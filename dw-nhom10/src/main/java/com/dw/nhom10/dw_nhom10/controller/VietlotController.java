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
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.dw.nhom10.dw_nhom10.model.Vietlot;
import com.dw.nhom10.dw_nhom10.model.VietlotAggregate;
import com.dw.nhom10.dw_nhom10.view.LotteryResultUI;

public class VietlotController {
	// 2.Initiate Vietlot and VietlotAggregate object
	private LotteryResultUI lotteryResultUI = new LotteryResultUI();
	Vietlot vietlot = new Vietlot();
	VietlotAggregate vietlotAggregate = new VietlotAggregate();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String currentDateStr = dateFormat.format(new Date());

	public VietlotController() throws ParseException {

		try {
			// 3.Call method getDataVietlot(date) and getDataVietlotAggregate(date) to get data from datamart and aggregate by date
			Vietlot newVietlot = vietlot.getDataVietlot(currentDateStr);
			Date date = dateFormat.parse(currentDateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			String month = String.valueOf(calendar.get(Calendar.MONTH));
			String year = String.valueOf(calendar.get(Calendar.YEAR));
			VietlotAggregate newVietlotAggregate = vietlotAggregate.getDataVietlotAggregate(month, year);

			// 4.Check If data exist in database by date now
			if (newVietlotAggregate == null || newVietlot == null)
				// 4.1. Default date is "21/11/2023" for Vietlot and "10/2023 for VietlotAggregate"
				newVietlotAggregate = vietlotAggregate.getDataVietlotAggregate("10", "2023");
			if (newVietlot == null)
				// 4.1. Default date is "21/11/2023" for Vietlot and "10/2023 for VietlotAggregate"
				newVietlot = vietlot.getDataVietlot("21/11/2023");

			// 5. Update object Vietlot and VietlotAggregate
			vietlot = newVietlot;
			vietlotAggregate = newVietlotAggregate;
			updateViews(vietlot, vietlotAggregate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initController() {
		// 7. Add event handle addDateChangeListener()
		getLotteryResultUI().addDateChangeListener(new PropertyChangeListener() {
			// 8. When user choose other date
			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				if ("date".equals(evt.getPropertyName())) {
					try {
						// 9. Call method handleDateChange()
						handleDateChange();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});
	}

	/* 
	 * 	 10. Transform the other date and call method getDataVietlot(date)
	 *  and getDataVietlotAggregate(date) to get data from datamart and aggregate by the other date
	 */
	 private void handleDateChange() throws SQLException, ParseException {
		String selectedDate = getSelectedDateAsString();
		Date date = dateFormat.parse(selectedDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String month = String.valueOf(calendar.get(Calendar.MONTH));
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		if (selectedDate != null) {
			Vietlot vietData = vietlot.getDataVietlot(selectedDate);
			VietlotAggregate vietDataAggregate = vietlotAggregate.getDataVietlotAggregate(month, year);
			// 11. Call method updateViews(Vietlot vietData, VietlotAggregate vietlotAggregate)
			updateViews(vietData, vietDataAggregate);
		}
	}

	// cập nhật view khi date thay đổi
	private void updateViews(Vietlot vietData, VietlotAggregate vietlotAggregate) {
		if (vietData != null && vietlotAggregate != null) {
			// 12. Update DrawNumbers, LotteryResult, ResultPannel page by new data
			updateDrawNumbers(vietData);
			updateLotteryResult(vietData);
			updateResultPannel(vietData, vietlotAggregate);
		} else {
			// Xử lý trường hợp không tìm thấy dữ liệu
		}
	}

	// Cập nhật trang DrawNumbersPanel
	private void updateDrawNumbers(Vietlot vietData) {
		if (vietData != null) {
			// Cập nhật dữ liệu giao diện cho phần hiển thị kết quả quay số
			lotteryResultUI.getDrawNumbersPanel().setDateLabel(new JLabel("Ngày: " + vietData.getDate()));
			lotteryResultUI.getDrawNumbersPanel().getDateLabel().setHorizontalAlignment(SwingConstants.CENTER);

			lotteryResultUI.getDrawNumbersPanel()
					.setDrawViewLabel(new JLabel("Kỳ quay trúng thưởng: " + vietData.getDrawVietlot()));
			lotteryResultUI.getDrawNumbersPanel().getDrawViewLabel().setHorizontalAlignment(SwingConstants.CENTER);

			lotteryResultUI.getDrawNumbersPanel().setLabelPanel(new JPanel(new GridLayout(2, 1)));
			lotteryResultUI.getDrawNumbersPanel().getLabelPanel()
					.add(lotteryResultUI.getDrawNumbersPanel().getDateLabel());
			lotteryResultUI.getDrawNumbersPanel().getLabelPanel()
					.add(lotteryResultUI.getDrawNumbersPanel().getDrawViewLabel());

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

	// Cập nhật trang LotteryResultUI
	private void updateLotteryResult(Vietlot vietData) {
		// Cập nhật tiêu đề cho cửa sổ
		lotteryResultUI.setTitle("Kết quả quay số mở thưởng Power " + vietData.getDate());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			// Đặt ngày được chọn trên dateChooser
			lotteryResultUI.getDateChooser().setDate(dateFormat.parse(vietData.getDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// Cập nhật trang ResultPanel
	private void updateResultPannel(Vietlot vietData, VietlotAggregate vietlotAggregate) {
		if (vietData != null) {
			// Cập nhật dữ liệu cho panel kết quả
			lotteryResultUI.getResultPanel().getSecondTablePanel()
					.setBorder(BorderFactory.createTitledBorder("Thống kê các giải đã xuất hiện nhiều nhất trong tháng "
							+ vietlotAggregate.getMonth() + " năm " + vietlotAggregate.getYear()));

			Object[][] dataTable = { { "JACKPOT 1", "6 số", vietData.getAmountJp1(), vietData.getJackpot1() },
					{ "JACKPOT 2", "5 số + power", vietData.getAmountJp2(), vietData.getJackpot2() },
					{ "Giải nhất", "5 số", vietData.getAmountFirst(), "40 Triệu" },
					{ "Giải nhì", "4 số", vietData.getAmountSecond(), "500.000đ" },
					{ "Giải ba", "3 số", vietData.getAmountThird(), "50.000đ" } };
			Object[][] newDataTable = { { "JACKPOT 1", "6 số", vietlotAggregate.getAmountJp1Month() },
					{ "JACKPOT 2", "5 số + power", vietlotAggregate.getAmountJp2Month() },
					{ "Giải nhất", "5 số", vietlotAggregate.getAmountFirstMonth() },
					{ "Giải nhì", "4 số", vietlotAggregate.getAmountSecondMonth() },
					{ "Giải ba", "3 số", vietlotAggregate.getAmountThirdMonth() } };
			lotteryResultUI.getResultPanel().updateTableData(dataTable, 1);
			lotteryResultUI.getResultPanel().updateTableData(newDataTable, 2);

		}
	}

	public static String adjustDate(String inputDate) {
		// Điều chỉnh ngày thành tháng trước
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
		// Lấy ngày được chọn trên dateChooser dưới dạng chuỗi
		Date selectedDate = getLotteryResultUI().getDateChooser().getDate();
		if (selectedDate != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.format(selectedDate);
		} else {
			return null;
		}
	}

	// Các phương thức getter và setter cho các thành phần giao diện và dữ liệu
	public LotteryResultUI getLotteryResultUI() {
		return lotteryResultUI;
	}
}
