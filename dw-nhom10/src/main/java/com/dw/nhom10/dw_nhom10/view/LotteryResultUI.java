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
	private DrawNumbersPanel drawNumbersPanel = new DrawNumbersPanel();
	private ResultPanel resultPanel = new ResultPanel();
	JDateChooser dateChooser;
	private String date;

	public LotteryResultUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 330);
		setLocationRelativeTo(null);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(0.2);
		splitPane.setDividerLocation(0.2);
		splitPane.setLeftComponent(drawNumbersPanel);
		splitPane.setRightComponent(resultPanel);

		getContentPane().add(createDatePanel(), BorderLayout.NORTH);
		getContentPane().add(splitPane, BorderLayout.CENTER);
	}

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
