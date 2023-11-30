package com.dw.nhom10.dw_nhom10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDM {
	public static void main(String[] args) throws SQLException {
		String sourceDbUrl = "jdbc:mysql://localhost:3306/datawarehouse";
		String sourceDbUser = "root";
		String sourceDbPassword = "";

		String targetDbUrl = "jdbc:mysql://localhost:3306/datamart";
		String targetDbUser = "root";
		String targetDbPassword = "";
		try (
				/**
				 * 1. Connect to DB warehouse and datamart
				 **/
				Connection sourceConn = DriverManager.getConnection(sourceDbUrl, sourceDbUser, sourceDbPassword);
				Connection targetConn = DriverManager.getConnection(targetDbUrl, targetDbUser, targetDbPassword);) {
			/**
			 * 2. query data warehouse
			 */
			String selectQuery = "SELECT * FROM dw_vietlot";
			try (PreparedStatement selectStatement = sourceConn.prepareStatement(selectQuery);
					ResultSet resultSet = selectStatement.executeQuery();) {

				String insertQuery = "INSERT INTO datamart.dm_vietlot(date, draw_vietlot, number1, number2, number3, number4, number5, number6, number7, jackpot1, jackpot2, amount_jp1, amount_jp2, amount_first, amount_second, amount_third) VALUES (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				try (PreparedStatement insertStatement = targetConn.prepareStatement(insertQuery)) {
					while (resultSet.next()) {
						/**
						 * 3. Check if the current data from data mart already exists
						 */
						String dateValue = resultSet.getString("date");
						if (!dateExistsInTarget(dateValue, targetConn)) {
							/**
							 * 4. If the data doesn't exist, insert it
							 */
							insertStatement.setString(1, dateValue);
							for (int i = 2; i <= 16; i++) {
								insertStatement.setLong(i, resultSet.getLong(i));
							}
							insertStatement.executeUpdate();
							System.out.println("Data Not Exists.");
						}
						System.out.println("Data Exists.");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Data transfer complete.");
		}
	}

	/**
	 * Helper method to check if a specific date already exists in the target table
	 */
	private static boolean dateExistsInTarget(String dateValue, Connection targetConn) throws SQLException {
		String checkQuery = "SELECT COUNT(*) FROM datamart.dm_vietlot WHERE date = ?";
		try (PreparedStatement checkStatement = targetConn.prepareStatement(checkQuery);) {
			checkStatement.setString(1, dateValue);
			try (ResultSet resultSet = checkStatement.executeQuery()) {
				resultSet.next();
				return resultSet.getInt(1) > 0;
			}
		}
	}
}
