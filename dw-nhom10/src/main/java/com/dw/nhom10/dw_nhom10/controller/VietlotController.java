package com.dw.nhom10.dw_nhom10.controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.dw.nhom10.dw_nhom10.model.Vietlot;
import com.dw.nhom10.dw_nhom10.view.LotteryResultUI;

public class VietlotController {
	private LotteryResultUI lotteryResultUI = new LotteryResultUI();
	Vietlot vietlot = new Vietlot();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String currentDateStr = dateFormat.format(new Date());

	public VietlotController() {

		try {
			Vietlot newVietlot = vietlot.getDataVietlot(currentDateStr);
			if (newVietlot == null)
				newVietlot = vietlot.getDataVietlot("21/11/2023");
			vietlot = newVietlot;
			updateViews(vietlot);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initController() {
		getLotteryResultUI().addDateChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					try {
						handleDateChange();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}

		});
	}

	private void handleDateChange() throws SQLException {
		String selectedDate = getSelectedDateAsString();
		if (selectedDate != null) {
			Vietlot vietData = vietlot.getDataVietlot(selectedDate);
			updateViews(vietData);
		}
	}

	private void updateViews(Vietlot vietData) {
		if (vietData != null) {
			updateDrawNumbers(vietData);
			updateLotteryResult(vietData);
			updateResultPannel(vietData);
		} else {
			// Handle page 404
		}
	}

	private void updateDrawNumbers(Vietlot vietData) {
		if (vietData != null) {
			lotteryResultUI.getDrawNumbersPanel().setDateLabel(new JLabel("Ngày: " + currentDateStr));
			lotteryResultUI.getDrawNumbersPanel().getDateLabel().setHorizontalAlignment(SwingConstants.CENTER);

			lotteryResultUI.getDrawNumbersPanel()
					.setDrawViewLabel(new JLabel("Kỳ quay trúng thưởng: " + vietData.getDrawVietlot()));
			lotteryResultUI.getDrawNumbersPanel().getDrawViewLabel().setHorizontalAlignment(SwingConstants.CENTER);

			lotteryResultUI.getDrawNumbersPanel().setLabelPanel(new JPanel(new GridLayout(2, 1)));
			lotteryResultUI.getDrawNumbersPanel().getLabelPanel()
					.add(lotteryResultUI.getDrawNumbersPanel().getDateLabel());
			lotteryResultUI.getDrawNumbersPanel().getLabelPanel()
					.add(lotteryResultUI.getDrawNumbersPanel().getDrawViewLabel());

			lotteryResultUI.getDrawNumbersPanel().setAdditionalLabel(new JLabel(
					"<html><div style='text-align: center; font-style:italic;'>*Các con số dự thưởng phải trùng với số kết quả nhưng không cần theo đúng thứ tự.</div></html>"));
			lotteryResultUI.getDrawNumbersPanel().getAdditionalLabel().setHorizontalAlignment(SwingConstants.CENTER);

			lotteryResultUI.getDrawNumbersPanel().add(lotteryResultUI.getDrawNumbersPanel().getLabelPanel(),
					BorderLayout.NORTH);
			lotteryResultUI.getDrawNumbersPanel().add(lotteryResultUI.getDrawNumbersPanel().getAdditionalLabel(),
					BorderLayout.SOUTH);

			Integer[] numbers = new Integer[] { vietData.getNumber1(), vietData.getNumber2(), vietData.getNumber3(),
					vietData.getNumber4(), vietData.getNumber5(), vietData.getNumber6(), vietData.getNumber7(), };
			lotteryResultUI.getDrawNumbersPanel().setNumbersToDraw(numbers);
			lotteryResultUI.getDrawNumbersPanel().repaint();

		}
	}

	private void updateLotteryResult(Vietlot vietData) {
		lotteryResultUI.setTitle("Kết quả quay số mở thưởng Power " + vietData.getDate());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			lotteryResultUI.getDateChooser().setDate(dateFormat.parse(vietData.getDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void updateResultPannel(Vietlot vietData) {
		if (vietData != null) {
			lotteryResultUI.getResultPanel().getSecondTablePanel().setBorder(BorderFactory.createTitledBorder(
					"Thống kê các giải đã xuất hiện nhiều nhất trong tháng " + adjustDate(vietData.getDate())));

			Object[][] dataTable = { { "JACKPOT 1", "6 số", vietData.getAmountJp1(), vietData.getJackpot1() },
					{ "JACKPOT 2", "5 số + power", vietData.getAmountJp2(), vietData.getJackpot2() },
					{ "Giải nhất", "5 số", vietData.getAmountFirst(), "40 Triệu" },
					{ "Giải nhì", "4 số", vietData.getAmountSecond(), "500.000đ" },
					{ "Giải ba", "3 số", vietData.getAmountThird(), "50.000đ" } };
			Object[][] newDataTable = { { "JACKPOT 1", "6 số", vietData.getAmountJp1() },
					{ "JACKPOT 2", "5 số + power", vietData.getAmountJp2() },
					{ "Giải nhất", "5 số", vietData.getAmountFirst() },
					{ "Giải nhì", "4 số", vietData.getAmountSecond() },
					{ "Giải ba", "3 số", vietData.getAmountThird() } };
			lotteryResultUI.getResultPanel().updateTableData(dataTable, 1);
			lotteryResultUI.getResultPanel().updateTableData(newDataTable, 2);

		}
	}

	public static String adjustDate(String inputDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(inputDate, formatter);

		if (date.getMonthValue() == 1) {
			date = date.minusYears(1).withMonth(12);
		} else {
			date = date.minusMonths(1);
		}

		return date.format(formatter);
	}

	public String getSelectedDateAsString() {
		Date selectedDate = getLotteryResultUI().getDateChooser().getDate();
		if (selectedDate != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.format(selectedDate);
		} else {
			return null;
		}
	}

	public LotteryResultUI getLotteryResultUI() {
		return lotteryResultUI;
	}

}
