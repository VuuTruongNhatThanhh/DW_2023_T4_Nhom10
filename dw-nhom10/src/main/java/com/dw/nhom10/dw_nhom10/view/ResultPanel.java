package com.dw.nhom10.dw_nhom10.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ResultPanel extends JPanel {
	private JTable table = new JTable();
	private JTable newTable = new JTable();
	private String[] columnsTable1 = { "Giải thưởng", "Trùng", "Số lượng giải", "Giá trị giải" };
	String[] columnsTable2 = { "Giải thưởng", "Trùng", "Số lượng xuất hiện" };
	JPanel secondTablePanel = new JPanel(new BorderLayout());

	public ResultPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		DefaultTableModel tableModel = new DefaultTableModel(new Object[][] {}, columnsTable1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(tableModel);
		applyNumberRenderer(table);

		JPanel firstTablePanel = new JPanel(new BorderLayout());
		firstTablePanel.setBorder(BorderFactory.createTitledBorder("Kết quả hôm nay"));
		JScrollPane firstScrollPane = new JScrollPane(table);
		firstTablePanel.add(firstScrollPane);
		add(firstTablePanel);

		DefaultTableModel newTableModel = new DefaultTableModel(new Object[][] {}, columnsTable2) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		newTable.setModel(newTableModel);
//		applyNumberRenderer(newTable);

		secondTablePanel.setBorder(
				BorderFactory.createTitledBorder("Thống kê các giải đã xuất hiện nhiều nhất trong tháng 11"));
		JScrollPane secondScrollPane = new JScrollPane(newTable);
		secondTablePanel.add(secondScrollPane);
		add(secondTablePanel);
	}

	private void applyNumberRenderer(JTable table) {
		DefaultTableCellRenderer numberRenderer = new DefaultTableCellRenderer() {
			NumberFormat integerFormat = NumberFormat.getNumberInstance();
			NumberFormat customFormat = NumberFormat.getNumberInstance();

			@Override
			protected void setValue(Object value) {
				setHorizontalAlignment(JLabel.RIGHT);
				if (value instanceof Number) {
					int modelColumn = table.convertColumnIndexToModel(column);
					if (modelColumn == 2) {
						setText(integerFormat.format((Number) value));
					} else if (modelColumn == 3) {
						if (row < 2) {
							customFormat.setMaximumFractionDigits(0);
							setText(customFormat.format((Number) value));
						} else {
							setText(value.toString());
						}
					}
				} else {
					super.setValue(value);
				}
			}

			private int row;
			private int column;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				this.row = row;
				this.column = column;
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};

		table.getColumnModel().getColumn(2).setCellRenderer(numberRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(numberRenderer);
	}

	public void updateTableData(Object[][] data, int tableIndex) {
		DefaultTableModel model;
		if (tableIndex == 1) {
			model = (DefaultTableModel) table.getModel();

		} else {
			model = (DefaultTableModel) newTable.getModel();
		}
		model.setRowCount(0);
		for (Object[] row : data) {
			System.out.println("RUN");
			model.addRow(row);
		}
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JTable getNewTable() {
		return newTable;
	}

	public void setNewTable(JTable newTable) {
		this.newTable = newTable;
	}

	public String[] getColumnsTable1() {
		return columnsTable1;
	}

	public void setColumnsTable1(String[] columnsTable1) {
		this.columnsTable1 = columnsTable1;
	}

	public String[] getColumnsTable2() {
		return columnsTable2;
	}

	public void setColumnsTable2(String[] columnsTable2) {
		this.columnsTable2 = columnsTable2;
	}

	public JPanel getSecondTablePanel() {
		return secondTablePanel;
	}

	public void setSecondTablePanel(JPanel secondTablePanel) {
		this.secondTablePanel = secondTablePanel;
	}

}
