package com.github.mayconr.garra;

import org.jdesktop.beansbinding.Converter;

import com.fazecast.jSerialComm.SerialPort;

public class PortaAdapterConverter extends Converter<SerialPort, PortaAdapter>{

	@Override
	public PortaAdapter convertForward(SerialPort serial) {
		if (serial != null) {
			return new PortaAdapter(serial);
		}
		return null;
	}

	@Override
	public SerialPort convertReverse(PortaAdapter adapter) {
		if (adapter != null) {
			return adapter.getPort();
		}
		return null;
	}

}
