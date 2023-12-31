package com.dw.nhom10.dw_nhom10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDM {
	public static void main(String[] args) throws SQLException {
		// Declare variables source my sql
		DatabaseConfig datawarehouse = new DatabaseConfig("db3");
		String dbUrl_ = datawarehouse.getJdbcUrl();
		String username = datawarehouse.getUsername();
		String password = datawarehouse.getPassword();

		// connect to database of aggregate
		DatabaseConfig aggregate = new DatabaseConfig("db4");
		String dbUrl_2 = aggregate.getJdbcUrl();
		String username1 = aggregate.getUsername();
		String password1 = aggregate.getPassword();
		try (
				/**
				 * 2. Connect to DB warehouse and datamart
				 **/
				Connection sourceConn = DriverManager.getConnection(dbUrl_, username, password);
				Connection targetConn = DriverManager.getConnection(dbUrl_2, username1, password1);) {
			/**
			 * 3.Select all data from data warehouse
			 */
			String selectQuery = "SELECT * FROM dw_vietlot";
			try (PreparedStatement selectStatement = sourceConn.prepareStatement(selectQuery);
					ResultSet resultSet = selectStatement.executeQuery();) {

				String insertQuery = "INSERT INTO datamart.dm_vietlot(date, draw_vietlot, number1, number2, number3, number4, number5, number6, number7, jackpot1, jackpot2, amount_jp1, amount_jp2, amount_first, amount_second, amount_third) VALUES (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				try (PreparedStatement insertStatement = targetConn.prepareStatement(insertQuery)) {
					while (resultSet.next()) {
						/**
						 * 4. Check if datamart is exists data now
						 */
						String dateValue = resultSet.getString("date");
						System.out.println("dateValue: " + dateValue);

						if (!dateExistsInTarget(dateValue, targetConn)) {
							/**
							 * 5. Insert data from warehouse to datamart
							 */
							insertStatement.setString(1, dateValue);
							insertStatement.setString(2, resultSet.getString("draw_vietlot"));
							for (int i = 3; i <= 16; i++) {
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
