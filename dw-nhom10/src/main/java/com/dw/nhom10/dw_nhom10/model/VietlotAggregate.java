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

	public VietlotAggregate() {
		// TODO Auto-generated constructor stub
	}

	public VietlotAggregate(Integer id, Integer amountJp1Month, Integer amountJp2Month, Integer amountFirstMonth,
			Integer amountSecondMonth, Integer amountThirdMonth, Integer numberAppearMost1, Integer numberAppearMost2,
			Integer numberAppearMost3, Integer numberAppearMost4, Integer numberAppearMost5) {
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

	public VietlotAggregate getDataVietlotAggregate(String date) throws SQLException {
		DatabaseConfig config = new DatabaseConfig("db5");
		String dbUrl_5 = config.getJdbcUrl();
		String username = config.getUsername();
		String password = config.getPassword();

		Connection targetConn = DriverManager.getConnection(dbUrl_5, username, password);
		String selectQuery = "SELECT * FROM dm_vietlot_aggregate WHERE date = ?"; // Assuming the table name is
																					// dm_vietlot_aggregate

		try (PreparedStatement selectStatement = targetConn.prepareStatement(selectQuery)) {
			selectStatement.setString(1, date);

			try (ResultSet resultSet = selectStatement.executeQuery()) {
				if (resultSet.next()) {
					VietlotAggregate vietlotAggregate = new VietlotAggregate(resultSet.getInt("id"),
							resultSet.getInt("amount_jp1_month"), resultSet.getInt("amount_jp2_month"),
							resultSet.getInt("amount_first_month"), resultSet.getInt("amount_second_month"),
							resultSet.getInt("amount_third_month"), resultSet.getInt("number_appear_most1"),
							resultSet.getInt("number_appear_most2"), resultSet.getInt("number_appear_most3"),
							resultSet.getInt("number_appear_most4"), resultSet.getInt("number_appear_most5"));
					return vietlotAggregate;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

}
