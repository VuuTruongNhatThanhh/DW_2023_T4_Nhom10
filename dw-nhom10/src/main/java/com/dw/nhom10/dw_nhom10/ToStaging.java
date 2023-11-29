package com.dw.nhom10.dw_nhom10;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToStaging {

	public static void main(String[] args) {

		DatabaseConfig config = new DatabaseConfig("db1");
    	String dbUrl_2 = config.getJdbcUrl();
    	String username = config.getUsername();
    	String password = config.getPassword();
		
		String link_save_toFile = null;

		try {

			/**
			 * 2. Connect control DB
			 */
			Connection connection = DriverManager.getConnection(dbUrl_2, username, password);
			/**
			 * 3. Select link_save_excel from config
			 */
			String sqlQuery = "SELECT link_save_excel FROM config";
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				link_save_toFile = resultSet.getString("link_save_excel");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		
		
		DatabaseConfig config2 = new DatabaseConfig("db2");
    	String dbUrl = config2.getJdbcUrl();
    	String dbUser = config2.getUsername();
    	String dbPassword = config2.getPassword();

		/**
		 * 4. Connect staging DB
		 */

		try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {

			/**
			 * 5. Open file excel, get first sheet in file
			 */
			FileInputStream fileInputStream = new FileInputStream(link_save_toFile);
			Workbook workbook = new XSSFWorkbook(fileInputStream);

			Sheet sheet = workbook.getSheetAt(0);

			String insertSql = "INSERT INTO staging_vietlot (date, draw_vietlot,number1,number2,number3,number4,number5,number6,number7,jackpot1,jackpot2,amount_jp1,amount_jp2,amount_first,amount_second,amount_third) VALUES (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String selectSql = "SELECT date FROM staging_vietlot WHERE date = ?";

			try (PreparedStatement insertStatement = connection.prepareStatement(insertSql);
					PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
				/**
				 * 6. Check whether data in cell is String 
				 * 		-yes: Check if the date already
				 * exists in the table 
				 * 				 
				 * 						*yes: end
				 * 						*no: insert all data to table 
				 * 		-no: end
				 */
				for (Row row : sheet) {
					Cell dateCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					if (dateCell.getCellType() == CellType.STRING) {
						String dateValue = dateCell.getStringCellValue();

						selectStatement.setString(1, dateValue);
						ResultSet resultSet = selectStatement.executeQuery();

						if (!resultSet.next()) {

							for (int i = 0; i < 16; i++) {
								Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
								switch (cell.getCellType()) {
								case STRING:
									insertStatement.setString(i + 1, cell.getStringCellValue());
									break;

								}
							}
							insertStatement.addBatch();
						}
					}
				}

				insertStatement.executeBatch();
			}

			System.out.println("Data inserted successfully.");
		} catch (SQLException | java.io.IOException e) {
			e.printStackTrace();
		}
	}
}
