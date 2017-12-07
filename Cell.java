package br.unisinos.lab2.TrabGB;

import java.io.Serializable;

public abstract class Cell implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	public Cell(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public abstract double eval();

}
