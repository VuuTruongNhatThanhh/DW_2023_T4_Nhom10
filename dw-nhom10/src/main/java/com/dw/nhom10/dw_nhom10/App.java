package com.dw.nhom10.dw_nhom10;

import java.text.ParseException;

import javax.swing.SwingUtilities;

import com.dw.nhom10.dw_nhom10.controller.VietlotController;

public class App {
	public String Hello() {
		return "Hello world";
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			// 1. Initiate VietlotController

			VietlotController controller;
			try {
				controller = new VietlotController();
				// 6. Call method initController()
				controller.initController();
				controller.getLotteryResultUI().setVisible(true);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}
}
