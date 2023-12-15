package com.dw.nhom10.dw_nhom10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ToAggregate {

	public static void main(String[] args) {
		/**
		 * 2.Connect to database of datawarehouse and aggregate
		 **/
		// connect to database of datawarehouse
		DatabaseConfig datawarehouse = new DatabaseConfig("db3");
    	String dbUrl_ = datawarehouse.getJdbcUrl();
    	String username = datawarehouse.getUsername();
    	String password = datawarehouse.getPassword();
    	
    	// connect to database of aggregate
    	DatabaseConfig aggregate = new DatabaseConfig("db5");
    	String dbUrl_2 = aggregate.getJdbcUrl();
    	String username1 = aggregate.getUsername();
    	String password1 = aggregate.getPassword();
		
    	
		
		
		
		LocalDate currentDate = LocalDate.now();

		try (Connection connection = DriverManager.getConnection(dbUrl_ , username, password);
				Connection connection1 = DriverManager.getConnection(dbUrl_2, username1, password1)) {
			/**
			 * 3.Check if that date is the first day of the month
			 * 
			 * yes: go to step 4
			 * no: Aggregation skipped
			 **/
			if (currentDate.getDayOfMonth() == 1) {
				/**
				 *4. Check if the date is January 1st
				 *yes: go to 4.2
				 *no: go to 4.1
				 *
				 **/
				if (currentDate.getDayOfMonth() == 1 && currentDate.getMonthValue() == 1) {

					/**
					 * If it's the first day of January, 
					 * 4.2 Get data from December of the previous year
					 **/
					
					currentDate = currentDate.minusYears(1);
				}
				/**
				 * If it's not the first day of January, 
				 * 4.1. Get data from the previous month of the year
				 **/
				// Calculate the previous month and year
				LocalDate previousMonth = currentDate.minusMonths(1);

				// Format the date as needed for your database
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
				String previousMonthString = previousMonth.format(formatter);
				int currentYear = currentDate.getYear();
				

				// Declare variables for each column
				String month = previousMonthString;
				String year = String.valueOf(currentYear);

				// Declare variables for amount columns
				double amount_jp1_month;
				double amount_jp2_month;
				double amount_first_month;
				double amount_second_month;
				double amount_third_month;

				// Declare variables for number_appear_most columns
				int number_appear_most1 = 0;
				int number_appear_most2 = 0;
				int number_appear_most3 = 0;
				int number_appear_most4 = 0;
				int number_appear_most5 = 0;
				/**
				 * 5. Select the number of each prize for the month from dw_vietlot table
				 **/
				// Calculate amounts for each column
				String amountQuery = "SELECT SUM(amount_jp1) as a1, SUM(amount_jp2) as a2, SUM(amount_first) as a3, SUM(amount_second) as a4, SUM(amount_third) as a5\n"
						+ "FROM dw_vietlot WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y')) = ? AND YEAR(STR_TO_DATE(date, '%d/%m/%Y')) = ?";

				try (PreparedStatement amountStatement = connection.prepareStatement(amountQuery)) {
					amountStatement.setInt(1, previousMonth.getMonthValue());
					amountStatement.setInt(2, currentYear);

					try (ResultSet amountResult = amountStatement.executeQuery()) {
						if (amountResult.next()) {
							amount_jp1_month = amountResult.getDouble("a1");
							amount_jp2_month = amountResult.getDouble("a2");
							amount_first_month = amountResult.getDouble("a3");
							amount_second_month = amountResult.getDouble("a4");
							amount_third_month = amountResult.getDouble("a5");
						} else {
							// Handle no results if needed
							throw new SQLException("No results for amount query");
						}
					}
				}
				/**
				 * 6 .Select the 5 numbers that appear the most in the 7 winning numbers of the month from dw_viettlot table
				 **/
				// Set values for number_appear_most columns
				String numberAppearQuery = "SELECT number, COUNT(*) AS frequency\n" + "FROM (\n"
						+ "   SELECT number1 AS number FROM dw_vietlot WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y')) = ?\n"
						+ "   UNION ALL\n"
						+ "   SELECT number2 AS number FROM dw_vietlot WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y')) = ?\n"
						+ "   UNION ALL\n"
						+ "   SELECT number3 AS number FROM dw_vietlot WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y')) = ?\n"
						+ "   UNION ALL\n"
						+ "   SELECT number4 AS number FROM dw_vietlot WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y')) = ?\n"
						+ "   UNION ALL\n"
						+ "   SELECT number5 AS number FROM dw_vietlot WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y')) = ?\n"
						+ "   UNION ALL\n"
						+ "   SELECT number6 AS number FROM dw_vietlot WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y')) = ?\n"
						+ "   UNION ALL\n"
						+ "   SELECT number7 AS number FROM dw_vietlot WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y')) = ?\n"

						+ ") AS numbers\n" + "GROUP BY number\n" + "ORDER BY frequency DESC\n" + "LIMIT 5";

				try (PreparedStatement numberAppearStatement = connection.prepareStatement(numberAppearQuery)) {
					numberAppearStatement.setInt(1, previousMonth.getMonthValue());
					numberAppearStatement.setInt(2, previousMonth.getMonthValue());
					numberAppearStatement.setInt(3, previousMonth.getMonthValue());
					numberAppearStatement.setInt(4, previousMonth.getMonthValue());
					numberAppearStatement.setInt(5, previousMonth.getMonthValue());
					numberAppearStatement.setInt(6, previousMonth.getMonthValue());
					numberAppearStatement.setInt(7, previousMonth.getMonthValue());

					try (ResultSet numberAppearResult = numberAppearStatement.executeQuery()) {
						int count = 1;
						while (numberAppearResult.next()) {
							switch (count) {
							case 1:
								number_appear_most1 = numberAppearResult.getInt("number");
								break;
							case 2:
								number_appear_most2 = numberAppearResult.getInt("number");
								break;
							case 3:
								number_appear_most3 = numberAppearResult.getInt("number");
								break;
							case 4:
								number_appear_most4 = numberAppearResult.getInt("number");
								break;
							case 5:
								number_appear_most5 = numberAppearResult.getInt("number");
								break;

							}
							count++;
						}
					}
				}
				/**
				 * 7 .Check if data for the specified month and year in the aggregate_vietlot table of the aggreagte database already exists
				 * 
				 * no: go to step 7.1
				 * yes : go to step 7.2
				 **/
				if (!dataExists(connection1, previousMonthString, currentYear)) {
					/**
					 * 7.1 . Insert the retrieved data into the aggregate_vietlot table in the aggregate database		 
					 **/
					String sqlQuery = "INSERT INTO aggregate_vietlot (month, year, amount_jp1_month, amount_jp2_month, amount_first_month, amount_second_month, amount_third_month, number_appear_most1, number_appear_most2, number_appear_most3, number_appear_most4, number_appear_most5)\n"
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

					// Prepare the SQL statement
					try (PreparedStatement preparedStatement = connection1.prepareStatement(sqlQuery)) {
						// Set values for each parameter
						preparedStatement.setString(1, month);
						preparedStatement.setString(2, year);
						preparedStatement.setDouble(3, amount_jp1_month);
						preparedStatement.setDouble(4, amount_jp2_month);
						preparedStatement.setDouble(5, amount_first_month);
						preparedStatement.setDouble(6, amount_second_month);
						preparedStatement.setDouble(7, amount_third_month);
						preparedStatement.setInt(8, number_appear_most1);
						preparedStatement.setInt(9, number_appear_most2);
						preparedStatement.setInt(10, number_appear_most3);
						preparedStatement.setInt(11, number_appear_most4);
						preparedStatement.setInt(12, number_appear_most5);

						// Execute the query
						preparedStatement.executeUpdate();
						System.out.println("Query executed successfully for the previous month: " + previousMonthString
								+ "/" + currentYear);
					}
				}
				/**
				 * 7.2 . Update the retrieved data to the row with the specified month and year in the aggregate_vietlot table of database aggregate

				 **/
				else {
					String updateQuery = "UPDATE aggregate_vietlot SET " + "amount_jp1_month = ?, "
							+ "amount_jp2_month = ?, " + "amount_first_month = ?, " + "amount_second_month = ?, "
							+ "amount_third_month = ?, " + "number_appear_most1 = ?, " + "number_appear_most2 = ?, "
							+ "number_appear_most3 = ?, " + "number_appear_most4 = ?, " + "number_appear_most5 = ? "
							+ "WHERE month = ? AND year = ?";

					try (PreparedStatement updateStatement = connection1.prepareStatement(updateQuery)) {
						// Set values for each parameter
						updateStatement.setDouble(1, amount_jp1_month);
						updateStatement.setDouble(2, amount_jp2_month);
						updateStatement.setDouble(3, amount_first_month);
						updateStatement.setDouble(4, amount_second_month);
						updateStatement.setDouble(5, amount_third_month);
						updateStatement.setInt(6, number_appear_most1);
						updateStatement.setInt(7, number_appear_most2);
						updateStatement.setInt(8, number_appear_most3);
						updateStatement.setInt(9, number_appear_most4);
						updateStatement.setInt(10, number_appear_most5);
						updateStatement.setString(11, month);
						updateStatement.setString(12, year);

						// Execute the update
						int rowsUpdated = updateStatement.executeUpdate();

						if (rowsUpdated > 0) {
							System.out.println("Update executed successfully for the previous month: "
									+ previousMonthString + "/" + currentYear);
						} else {
							System.out.println("No rows updated. Data for the specified month and year not found.");
						}
					}
				}
			} else {
				System.out.println("It's not the first day of the month. Aggregation skipped.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static boolean dataExists(Connection connection1, String month, int year) throws SQLException {
		String checkDataQuery = "SELECT COUNT(*) AS count FROM aggregate_vietlot WHERE month = ? AND year = ?";

		try (PreparedStatement checkDataStatement = connection1.prepareStatement(checkDataQuery)) {
			checkDataStatement.setString(1, month);
			checkDataStatement.setInt(2, year);

			try (ResultSet resultSet = checkDataStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt("count");
					return count > 0;
				}
			}
		}

		return false;
	}
}
