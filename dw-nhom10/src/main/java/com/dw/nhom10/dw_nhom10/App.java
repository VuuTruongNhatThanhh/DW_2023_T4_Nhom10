package com.dw.nhom10.dw_nhom10;

import javax.swing.SwingUtilities;

import com.dw.nhom10.dw_nhom10.controller.VietlotController;

/**
 * Hello world!
 *
 */
public class App {
	public String Hello() {
		return "Hello world";
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			VietlotController controller = new VietlotController();
			controller.initController();
			controller.getLotteryResultUI().setVisible(true);
		});
	}
}
