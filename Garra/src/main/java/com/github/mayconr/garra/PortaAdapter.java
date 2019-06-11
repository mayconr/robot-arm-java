package com.github.mayconr.garra;

import com.fazecast.jSerialComm.SerialPort;

public class PortaAdapter {
	private SerialPort port;

	public PortaAdapter(SerialPort port) {
		super();
		this.port = port;
	}

	public SerialPort getPort() {
		return port;
	}

	@Override
	public String toString() {
		return port.getDescriptivePortName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortaAdapter other = (PortaAdapter) obj;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		return true;
	}
	
}
