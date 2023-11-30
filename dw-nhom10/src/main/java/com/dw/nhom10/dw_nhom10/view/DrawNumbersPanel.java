package com.dw.nhom10.dw_nhom10.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DrawNumbersPanel extends JPanel {
	private JLabel dateLabel;
	private JLabel drawViewLabel;
	private JLabel additionalLabel;
	private Integer[] numbersToDraw;
	JPanel labelPanel;

	public DrawNumbersPanel() {
		setLayout(new BorderLayout());

		labelPanel = new JPanel(new GridLayout(2, 1));

		dateLabel = new JLabel("Ngày: <date>");
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		drawViewLabel = new JLabel("Kỳ quay trúng thưởng: <viewlot_draw>");
		drawViewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		labelPanel.add(dateLabel);
		labelPanel.add(drawViewLabel);
		additionalLabel = new JLabel(
				"<html><div style='text-align: center; font-style:italic;'>*Các con số dự thưởng phải trùng với số kết quả nhưng không cần theo đúng thứ tự.</div></html>");
		additionalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelPanel, BorderLayout.NORTH);
		add(additionalLabel, BorderLayout.SOUTH);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int topMargin = 25;

		int startY = 40 + topMargin;

		int circleDiameter = 50;
		int squareSize = 50;
		int panelWidth = getWidth();
		int spaceBetweenShapes = (panelWidth - (3 * circleDiameter + squareSize)) / 5;

		Stroke borderStroke = new BasicStroke(2.0f);
		Font numberFont = new Font("Arial", Font.BOLD, 20);
		g2d.setFont(numberFont);

		FontMetrics metrics = g2d.getFontMetrics(numberFont);
		final int[] count = { 0 };
		Consumer<Shape> drawCenteredText = (shape) -> {
			if (count[0] < numbersToDraw.length) {
				String numberStr = numbersToDraw[count[0]].toString();
				Rectangle bounds = shape.getBounds();
				int x = bounds.x + (bounds.width - metrics.stringWidth(numberStr)) / 2;
				int y = bounds.y + ((bounds.height - metrics.getHeight()) / 2) + metrics.getAscent();
				g2d.drawString(numberStr, x, y);
				count[0]++;
			}
		};

		g2d.setColor(Color.YELLOW);

		for (int i = 0; i < 3; i++) {
			int x = (int) (1.5 * spaceBetweenShapes) + circleDiameter / 2 + (spaceBetweenShapes + circleDiameter) * i;
			g2d.fillOval(x, startY, circleDiameter, circleDiameter);
		}

		for (int i = 0; i < 3; i++) {
			int x = spaceBetweenShapes + (spaceBetweenShapes + circleDiameter) * i;
			g2d.fillOval(x, startY + circleDiameter + topMargin, circleDiameter, circleDiameter);
		}

		int squareX = spaceBetweenShapes + (spaceBetweenShapes + circleDiameter) * 3;
		g2d.fillRect(squareX, startY + circleDiameter + topMargin, squareSize, squareSize);

		g2d.setColor(Color.RED);
		g2d.setStroke(borderStroke);

		for (int i = 0; i < 3; i++) {
			int x = (int) (1.5 * spaceBetweenShapes) + circleDiameter / 2 + (spaceBetweenShapes + circleDiameter) * i;
			g2d.drawOval(x, startY, circleDiameter, circleDiameter);
			drawCenteredText.accept(new Ellipse2D.Float(x, startY, circleDiameter, circleDiameter));
		}

		for (int i = 0; i < 3; i++) {
			int x = spaceBetweenShapes + (spaceBetweenShapes + circleDiameter) * i;
			g2d.drawOval(x, startY + circleDiameter + topMargin, circleDiameter, circleDiameter);
			drawCenteredText.accept(
					new Ellipse2D.Float(x, startY + circleDiameter + topMargin, circleDiameter, circleDiameter));
		}

		g2d.drawRect(squareX, startY + circleDiameter + topMargin, squareSize, squareSize);

		drawCenteredText.accept(new Rectangle(squareX, startY + circleDiameter + topMargin, squareSize, squareSize));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 200);
	}

	public void setDate(String date) {
		dateLabel.setText("Ngày " + date);
	}

	public void setDrawView(String drawView) {
		drawViewLabel.setText("Kỳ quay trúng thưởng: " + drawView);
	}

	public void setNumbersToDraw(Integer[] numbers) {
		this.numbersToDraw = numbers;
	}

	public Integer[] getNumbersToDraw() {
		return numbersToDraw;
	}

	public JLabel getDateLabel() {
		return dateLabel;
	}

	public void setDateLabel(JLabel dateLabel) {
		this.dateLabel = dateLabel;
	}

	public JLabel getDrawViewLabel() {
		return drawViewLabel;
	}

	public void setDrawViewLabel(JLabel drawViewLabel) {
		this.drawViewLabel = drawViewLabel;
	}

	public JLabel getAdditionalLabel() {
		return additionalLabel;
	}

	public void setAdditionalLabel(JLabel additionalLabel) {
		this.additionalLabel = additionalLabel;
	}

	public JPanel getLabelPanel() {
		return labelPanel;
	}

	public void setLabelPanel(JPanel labelPanel) {
		this.labelPanel = labelPanel;
	}

}