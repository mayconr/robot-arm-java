package com.github.mayconr.garra;

import java.util.ArrayList;
import java.util.List;

import org.jdesktop.observablecollections.ObservableCollections;

import com.fazecast.jSerialComm.SerialPort;

public class GuarraModel extends AbstractBean {
	private List<PortaAdapter> portas;
	private SerialPort porta;
	private boolean conectado;
	
	private List<Comando> comandos;
	private Comando comando;
	
	public GuarraModel() {
		super();
		this.comandos = ObservableCollections.observableList(new ArrayList<>());
	}

	public List<PortaAdapter> getPortas() {
		return portas;
	}

	public void setPortas(List<PortaAdapter> portas) {
		firePropertyChange("portas", this.portas, this.portas = portas);
	}

	public SerialPort getPorta() {
		return porta;
	}

	public void setPorta(SerialPort porta) {
		firePropertyChange("porta", this.porta, this.porta = porta);
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		firePropertyChange("conectado", this.conectado, this.conectado = conectado);
	}

	public List<Comando> getComandos() {
		return comandos;
	}

	public void setComandos(List<Comando> comandos) {
		this.comandos.clear();
		this.comandos.addAll(comandos);
	}

	public void addComando(Comando comando) {
		this.comandos.add(comando);
	}

	public Comando getComando() {
		return comando;
	}

	public void setComando(Comando comando) {
		firePropertyChange("comando", this.comando, this.comando = comando);
	}
	
}
