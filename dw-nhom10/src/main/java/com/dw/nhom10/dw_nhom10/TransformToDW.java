package com.dw.nhom10.dw_nhom10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransformToDW {

	public static void main(String[] args) {
//        
		DatabaseConfig config1 = new DatabaseConfig("db2");
		String dbStagingUrl = config1.getJdbcUrl();
		String dbStagingUser = config1.getUsername();
		String dbStagingPassword = config1.getPassword();

//       

		DatabaseConfig config2 = new DatabaseConfig("db3");
		String dbDataWarehouseUrl = config2.getJdbcUrl();
		String dbDataWarehouseUser = config2.getUsername();
		String dbDataWarehousePassword = config2.getPassword();

		/**
		 * 2. Connect staging DB and datawarehouse DB
		 */

		try (Connection stagingConnection = DriverManager.getConnection(dbStagingUrl, dbStagingUser, dbStagingPassword);
				Connection dataWarehouseConnection = DriverManager.getConnection(dbDataWarehouseUrl,
						dbDataWarehouseUser, dbDataWarehousePassword)) {

			/**
			 * 3. Truncate data in datawarehouse.dw_vietlot
			 */
			String truncateSql = "TRUNCATE TABLE dw_vietlot";
			try (PreparedStatement truncateStatement = dataWarehouseConnection.prepareStatement(truncateSql)) {
				truncateStatement.execute();
			}

			/**
			 * 4. Transform and insert data from staging.staging_vietlot to
			 * datawarehouse.dw_vietlot
			 */
			String transformSql = "INSERT INTO datawarehouse.dw_vietlot (date, draw_vietlot, number1, number2, number3, number4, number5, number6, number7, jackpot1, jackpot2, amount_jp1, amount_jp2, amount_first, amount_second, amount_third) "
					+ "SELECT date, draw_vietlot," + "CAST(REPLACE(number1, ',', '') AS SIGNED), "
					+ "CAST(REPLACE(number2, ',', '') AS SIGNED), " + "CAST(REPLACE(number3, ',', '') AS SIGNED), "
					+ "CAST(REPLACE(number4, ',', '') AS SIGNED), " + "CAST(REPLACE(number5, ',', '') AS SIGNED), "
					+ "CAST(REPLACE(number6, ',', '') AS SIGNED), " + "CAST(REPLACE(number7, ',', '') AS SIGNED), "
					+ "CAST(REPLACE(jackpot1, ',', '') AS DECIMAL(20,2)), "
					+ "CAST(REPLACE(jackpot2, ',', '') AS DECIMAL(20,2)), "
					+ "CAST(REPLACE(amount_jp1, ',', '') AS SIGNED), "
					+ "CAST(REPLACE(amount_jp2, ',', '') AS SIGNED), "
					+ "CAST(REPLACE(amount_first, ',', '') AS SIGNED), "
					+ "CAST(REPLACE(amount_second, ',', '') AS SIGNED), "
					+ "CAST(REPLACE(amount_third, ',', '') AS SIGNED) " + "FROM staging_vietlot";

			try (PreparedStatement transformStatement = stagingConnection.prepareStatement(transformSql)) {
				transformStatement.execute();
			}

			System.out.println("Data transformation and insertion completed successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
