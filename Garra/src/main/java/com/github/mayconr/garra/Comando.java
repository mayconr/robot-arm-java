package com.github.mayconr.garra;

public class Comando {
	private String nome;
	private String valor;

	public Comando() {
		super();
	}

	public Comando(String nome, String valor) {
		super();
		this.nome = nome;
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
