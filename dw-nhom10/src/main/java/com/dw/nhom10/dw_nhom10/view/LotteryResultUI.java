package com.dw.nhom10.dw_nhom10.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.toedter.calendar.JDateChooser;

public class LotteryResultUI extends JFrame {
	// Khai báo các thành phần giao diện
	private DrawNumbersPanel drawNumbersPanel = new DrawNumbersPanel();
	private ResultPanel resultPanel = new ResultPanel();
	JDateChooser dateChooser;
	private String date;

	public LotteryResultUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đặt hành động khi đóng cửa sổ
		setSize(800, 330);// Đặt kích thước cửa sổ
		setLocationRelativeTo(null); // Đặt vị trí cửa sổ giữa màn hình

		// Tạo và cấu hình giao diện chia thành hai phần bằng JSplitPane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(0.2); // Đặt tỷ lệ kích thước của hai phần
		splitPane.setDividerLocation(0.2); // Đặt vị trí của đường chia
		splitPane.setLeftComponent(drawNumbersPanel); // Thiết lập panel bên trái
		splitPane.setRightComponent(resultPanel); // Thiết lập panel bên phải

		getContentPane().add(createDatePanel(), BorderLayout.NORTH);// Thêm panel chọn ngày vào phần đầu cửa sổ
		getContentPane().add(splitPane, BorderLayout.CENTER);// Thêm JSplitPane vào phần chính của cửa sổ

	}

	// Tạo panel chọn ngày
	private JPanel createDatePanel() {
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel dateLabel = new JLabel("Chọn ngày:");
		dateChooser = new JDateChooser();
		dateChooser.setDate(new Date());
		dateChooser.setDateFormatString("dd/MM/yyyy");
		datePanel.add(dateLabel);
		datePanel.add(dateChooser);
		return datePanel;
	}

	// Các phương thức getter và setter cho các thành phần giao diện và dữ liệu
	public JDateChooser getDateChooser() {
		return dateChooser;
	}

	public void addDateChangeListener(PropertyChangeListener listener) {
		dateChooser.addPropertyChangeListener(listener);
	}

	public DrawNumbersPanel getDrawNumbersPanel() {
		return drawNumbersPanel;
	}

	public void setDrawNumbersPanel(DrawNumbersPanel drawNumbersPanel) {
		this.drawNumbersPanel = drawNumbersPanel;
	}

	public ResultPanel getResultPanel() {
		return resultPanel;
	}

	public void setResultPanel(ResultPanel resultPanel) {
		this.resultPanel = resultPanel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDateChooser(JDateChooser dateChooser) {
		this.dateChooser = dateChooser;
	}

}
