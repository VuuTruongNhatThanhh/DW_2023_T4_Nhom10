package com.dw.nhom10.dw_nhom10.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dw.nhom10.dw_nhom10.DatabaseConfig;

public class Vietlot {
	private String date;
	private String drawVietlot;
	private Integer number1;
	private Integer number2;
	private Integer number3;
	private Integer number4;
	private Integer number5;
	private Integer number6;
	private Integer number7;
	private BigDecimal jackpot1;
	private BigDecimal jackpot2;
	private Integer amountJp1;
	private Integer amountJp2;
	private Integer amountFirst;
	private Integer amountSecond;
	private Integer amountThird;

	public Vietlot() {
	}

	public Vietlot(String date, String drawVietlot, Integer number1, Integer number2, Integer number3, Integer number4,
			Integer number5, Integer number6, Integer number7, BigDecimal jackpot1, BigDecimal jackpot2,
			Integer amountJp1, Integer amountJp2, Integer amountFirst, Integer amountSecond, Integer amountThird) {
		super();
		this.date = date;
		this.drawVietlot = drawVietlot;
		this.number1 = number1;
		this.number2 = number2;
		this.number3 = number3;
		this.number4 = number4;
		this.number5 = number5;
		this.number6 = number6;
		this.number7 = number7;
		this.jackpot1 = jackpot1;
		this.jackpot2 = jackpot2;
		this.amountJp1 = amountJp1;
		this.amountJp2 = amountJp2;
		this.amountFirst = amountFirst;
		this.amountSecond = amountSecond;
		this.amountThird = amountThird;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDrawVietlot() {
		return drawVietlot;
	}

	public void setDrawVietlot(String drawVietlot) {
		this.drawVietlot = drawVietlot;
	}

	public Integer getNumber1() {
		return number1;
	}

	public void setNumber1(Integer number1) {
		this.number1 = number1;
	}

	public Integer getNumber2() {
		return number2;
	}

	public void setNumber2(Integer number2) {
		this.number2 = number2;
	}

	public Integer getNumber3() {
		return number3;
	}

	public void setNumber3(Integer number3) {
		this.number3 = number3;
	}

	public Integer getNumber4() {
		return number4;
	}

	public void setNumber4(Integer number4) {
		this.number4 = number4;
	}

	public Integer getNumber5() {
		return number5;
	}

	public void setNumber5(Integer number5) {
		this.number5 = number5;
	}

	public Integer getNumber6() {
		return number6;
	}

	public void setNumber6(Integer number6) {
		this.number6 = number6;
	}

	public Integer getNumber7() {
		return number7;
	}

	public void setNumber7(Integer number7) {
		this.number7 = number7;
	}

	public BigDecimal getJackpot1() {
		return jackpot1;
	}

	public void setJackpot1(BigDecimal jackpot1) {
		this.jackpot1 = jackpot1;
	}

	public BigDecimal getJackpot2() {
		return jackpot2;
	}

	public void setJackpot2(BigDecimal jackpot2) {
		this.jackpot2 = jackpot2;
	}

	public Integer getAmountJp1() {
		return amountJp1;
	}

	public void setAmountJp1(Integer amountJp1) {
		this.amountJp1 = amountJp1;
	}

	public Integer getAmountJp2() {
		return amountJp2;
	}

	public void setAmountJp2(Integer amountJp2) {
		this.amountJp2 = amountJp2;
	}

	public Integer getAmountFirst() {
		return amountFirst;
	}

	public void setAmountFirst(Integer amountFirst) {
		this.amountFirst = amountFirst;
	}

	public Integer getAmountSecond() {
		return amountSecond;
	}

	public void setAmountSecond(Integer amountSecond) {
		this.amountSecond = amountSecond;
	}

	public Integer getAmountThird() {
		return amountThird;
	}

	public void setAmountThird(Integer amountThird) {
		this.amountThird = amountThird;
	}

	public Vietlot getDataVietlot(String date) throws SQLException {
		DatabaseConfig config = new DatabaseConfig("db4");
		String dbUrl_4 = config.getJdbcUrl();
		String username = config.getUsername();
		String password = config.getPassword();

		Connection targetConn = DriverManager.getConnection(dbUrl_4, username, password);
		String selectQuery = "SELECT * FROM dm_vietlot WHERE date = ?";

		try (PreparedStatement selectStatement = targetConn.prepareStatement(selectQuery)) {
			selectStatement.setString(1, date);

			try (ResultSet resultSet = selectStatement.executeQuery()) {
				if (resultSet.next()) {
					Vietlot vietlot = new Vietlot(resultSet.getString("date"), resultSet.getString("draw_vietlot"),
							resultSet.getInt("number1"), resultSet.getInt("number2"), resultSet.getInt("number3"),
							resultSet.getInt("number4"), resultSet.getInt("number5"), resultSet.getInt("number6"),
							resultSet.getInt("number7"), resultSet.getBigDecimal("jackpot1"),
							resultSet.getBigDecimal("jackpot2"), resultSet.getInt("amount_jp1"),
							resultSet.getInt("amount_jp2"), resultSet.getInt("amount_first"),
							resultSet.getInt("amount_second"), resultSet.getInt("amount_third"));
					return vietlot;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Vietlot [date=" + date + ", drawVietlot=" + drawVietlot + ", number1=" + number1 + ", number2="
				+ number2 + ", number3=" + number3 + ", number4=" + number4 + ", number5=" + number5 + ", number6="
				+ number6 + ", number7=" + number7 + ", jackpot1=" + jackpot1 + ", jackpot2=" + jackpot2
				+ ", amountJp1=" + amountJp1 + ", amountJp2=" + amountJp2 + ", amountFirst=" + amountFirst
				+ ", amountSecond=" + amountSecond + ", amountThird=" + amountThird + "]";
	}

}
