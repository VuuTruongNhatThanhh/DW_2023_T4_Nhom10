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
		 * 1.Connect to database of datawarehouse
		 **/
		String jdbcUrl = "jdbc:mysql://localhost:3306/datawarehouse";
		String username = "root";
		String password = "";
		LocalDate currentDate = LocalDate.now();

		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
			/**
			 * 2.Check if that date is the first day of the month
			 * 
			 * yes: go to step 3
			 * no: Aggregation skipped
			 **/
			if (currentDate.getDayOfMonth() == 1) {
				/**
				 *3. Check if the date is January 1st
				 *
				 *				 
				 **/
				if (currentDate.getDayOfMonth() == 1 && currentDate.getMonthValue() == 1) {
				
					/**
					 *  If it's the first day of January,
					 *3.2 Get data from December of the previous yeart				 
					 **/
					currentDate = currentDate.minusYears(1);
				}
				/**
				 * If it's not the first day of January,
				 * 3.1. Get data from the previous month of the year				 
				 **/
				// Calculate the previous month and year
				LocalDate previousMonth = currentDate.minusMonths(1);

				// Format the date as needed for your database
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
				String previousMonthString = previousMonth.format(formatter);
				int currentYear = currentDate.getYear();
				/**			
				 *4.Check if data for the specified month and year already exists	
				 *
				 * if data already exists, message and Insertion skipped, 
				 * if data is not exist, go to step 5.
				 **/
				  if (dataExists(connection, previousMonthString, currentYear)) {
					  // If Data already exists for the month
		                System.out.println("Data already exists for the month: " + previousMonthString + "/" + currentYear + ". Insertion skipped.");
		            } else {
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
				 * 5.Select the number of each prize for the month from dw_vietlot				 
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
				 * 6.Select the 5 numbers that appear the most in the 7 winning numbers of the month from dw_viettlot
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
				 * 7. Insert the retrieved data into the aggregate_vietlot table of the database aggregate
				 **/
				
				String sqlQuery = "INSERT INTO `aggregate`.aggregate_vietlot (month, year, amount_jp1_month, amount_jp2_month, amount_first_month, amount_second_month, amount_third_month, number_appear_most1, number_appear_most2, number_appear_most3, number_appear_most4, number_appear_most5)\n"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				// Prepare the SQL statement
				try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
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
			}} else {
				System.out.println("It's not the first day of the month. Aggregation skipped.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
		private static boolean dataExists(Connection connection, String month, int year) throws SQLException {
	        String checkDataQuery = "SELECT COUNT(*) AS count FROM `aggregate`.aggregate_vietlot WHERE month = ? AND year = ?";
	        
	        try (PreparedStatement checkDataStatement = connection.prepareStatement(checkDataQuery)) {
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
