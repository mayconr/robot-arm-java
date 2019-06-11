package com.github.mayconr.garra;

import javax.swing.SwingUtilities;

public class Application {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->new GuarraUI().setVisible(true));
		/*SerialPort port = SerialPort.getCommPort("COM5");
		
		if (port.openPort()) {
			String command = "C:120;A:150;B:150;D:70;E:30";
			port.writeBytes(command.getBytes(), command.length());
			port.closePort();
			System.out.println("foi");
		}
		System.out.println(port.isOpen());*/
	}
}
