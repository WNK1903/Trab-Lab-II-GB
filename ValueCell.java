package br.unisinos.lab2.TrabGB;

import java.io.Serializable;

public class ValueCell extends Cell implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double value;
	
	public ValueCell(String id, double value){
		super(id);
		this.value = value;
	}
	
	public double getValue(){
		return value;
	}
	
	public void setValue(double value){
		this.value = value;
	}
	
	public double eval(){
		return value;
		
	}
	
	public String toString(){
		return "identificador: " + super.getId() + " Conteúdo: " + value;
	}

}
