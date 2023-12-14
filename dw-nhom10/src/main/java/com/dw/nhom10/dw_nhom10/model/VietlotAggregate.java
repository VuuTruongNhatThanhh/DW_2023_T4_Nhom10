package com.dw.nhom10.dw_nhom10.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dw.nhom10.dw_nhom10.DatabaseConfig;

public class VietlotAggregate {
	private Integer id;
	private Integer amountJp1Month;
	private Integer amountJp2Month;
	private Integer amountFirstMonth;
	private Integer amountSecondMonth;
	private Integer amountThirdMonth;
	private Integer numberAppearMost1;
	private Integer numberAppearMost2;
	private Integer numberAppearMost3;
	private Integer numberAppearMost4;
	private Integer numberAppearMost5;
	private String month;
	private String year;

	public VietlotAggregate() {
		// TODO Auto-generated constructor stub
	}

	public VietlotAggregate(Integer id, Integer amountJp1Month, Integer amountJp2Month, Integer amountFirstMonth,
			Integer amountSecondMonth, Integer amountThirdMonth, Integer numberAppearMost1, Integer numberAppearMost2,
			Integer numberAppearMost3, Integer numberAppearMost4, Integer numberAppearMost5, String month,
			String year) {
		super();
		this.id = id;
		this.amountJp1Month = amountJp1Month;
		this.amountJp2Month = amountJp2Month;
		this.amountFirstMonth = amountFirstMonth;
		this.amountSecondMonth = amountSecondMonth;
		this.amountThirdMonth = amountThirdMonth;
		this.numberAppearMost1 = numberAppearMost1;
		this.numberAppearMost2 = numberAppearMost2;
		this.numberAppearMost3 = numberAppearMost3;
		this.numberAppearMost4 = numberAppearMost4;
		this.numberAppearMost5 = numberAppearMost5;
		this.month = month;
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAmountJp1Month() {
		return amountJp1Month;
	}

	public void setAmountJp1Month(Integer amountJp1Month) {
		this.amountJp1Month = amountJp1Month;
	}

	public Integer getAmountJp2Month() {
		return amountJp2Month;
	}

	public void setAmountJp2Month(Integer amountJp2Month) {
		this.amountJp2Month = amountJp2Month;
	}

	public Integer getAmountFirstMonth() {
		return amountFirstMonth;
	}

	public void setAmountFirstMonth(Integer amountFirstMonth) {
		this.amountFirstMonth = amountFirstMonth;
	}

	public Integer getAmountSecondMonth() {
		return amountSecondMonth;
	}

	public void setAmountSecondMonth(Integer amountSecondMonth) {
		this.amountSecondMonth = amountSecondMonth;
	}

	public Integer getAmountThirdMonth() {
		return amountThirdMonth;
	}

	public void setAmountThirdMonth(Integer amountThirdMonth) {
		this.amountThirdMonth = amountThirdMonth;
	}

	public Integer getNumberAppearMost1() {
		return numberAppearMost1;
	}

	public void setNumberAppearMost1(Integer numberAppearMost1) {
		this.numberAppearMost1 = numberAppearMost1;
	}

	public Integer getNumberAppearMost2() {
		return numberAppearMost2;
	}

	public void setNumberAppearMost2(Integer numberAppearMost2) {
		this.numberAppearMost2 = numberAppearMost2;
	}

	public Integer getNumberAppearMost3() {
		return numberAppearMost3;
	}

	public void setNumberAppearMost3(Integer numberAppearMost3) {
		this.numberAppearMost3 = numberAppearMost3;
	}

	public Integer getNumberAppearMost4() {
		return numberAppearMost4;
	}

	public void setNumberAppearMost4(Integer numberAppearMost4) {
		this.numberAppearMost4 = numberAppearMost4;
	}

	public Integer getNumberAppearMost5() {
		return numberAppearMost5;
	}

	public void setNumberAppearMost5(Integer numberAppearMost5) {
		this.numberAppearMost5 = numberAppearMost5;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public VietlotAggregate getDataVietlotAggregate(String month, String year) throws SQLException {
		DatabaseConfig config = new DatabaseConfig("db5");
		String dbUrl_5 = config.getJdbcUrl();
		String username = config.getUsername();
		String password = config.getPassword();

		Connection targetConn = DriverManager.getConnection(dbUrl_5, username, password);
		String selectQuery = "SELECT * FROM aggregate_vietlot WHERE month = ? AND year = ?";

		try (PreparedStatement selectStatement = targetConn.prepareStatement(selectQuery)) {
			selectStatement.setString(1, month);
			selectStatement.setString(2, year);

			try (ResultSet resultSet = selectStatement.executeQuery()) {
				if (resultSet.next()) {
					// Tạo một đối tượng VietlotAggregate và thiết lập các giá trị từ resultSet
					VietlotAggregate vietlot = new VietlotAggregate();
					vietlot.setMonth(resultSet.getString("month"));
					vietlot.setYear(resultSet.getString("year"));
					vietlot.setAmountJp1Month(resultSet.getInt("amount_jp1_month"));
					vietlot.setAmountJp2Month(resultSet.getInt("amount_jp2_month"));
					vietlot.setAmountFirstMonth(resultSet.getInt("amount_first_month"));
					vietlot.setAmountSecondMonth(resultSet.getInt("amount_second_month"));
					vietlot.setAmountThirdMonth(resultSet.getInt("amount_third_month"));
					vietlot.setNumberAppearMost1(resultSet.getInt("number_appear_most1"));
					vietlot.setNumberAppearMost2(resultSet.getInt("number_appear_most2"));
					vietlot.setNumberAppearMost3(resultSet.getInt("number_appear_most3"));
					vietlot.setNumberAppearMost4(resultSet.getInt("number_appear_most4"));
					vietlot.setNumberAppearMost5(resultSet.getInt("number_appear_most5"));

					return vietlot;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (targetConn != null) {
				targetConn.close();
			}
		}
		return null;
	}

	public VietlotAggregate getVietlotAggregate() {

		return null;
	}
}
