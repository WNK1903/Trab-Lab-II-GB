package br.unisinos.lab2.TrabGB;

import java.io.Serializable;

import br.unisinos.lab2.aula10.LinkedStack;
import br.unisinos.lab2.aula10.Stack;

public class FormulaCell  extends Cell implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String formula;
	

	public FormulaCell(String id, String formula) {
		super(id);
		this.formula = formula;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public String toString(){
		return "Identificador: " + super.getId() + " Conteúdo: " + formula;
	}

	//Método que avalia a fórmula.
	public double eval() {
		String[] colls = formula.split(" ");
		Stack<Double> stack = new LinkedStack<>();
		for (int i = 0; i < colls.length; i++) {
			//Verifica se é um valor númerico.
			if ("0123456789".contains(colls[i].substring(0, 1))) {
				stack.push(Double.parseDouble(colls[i]));
			}
			//Verifica se é uma referência a uma célula da planilha.
			else if ("abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ".contains(colls[i].substring(0, 1))) {
				DNode<Cell> current = Planilha.list.head;
				while (!current.getElement().getId().equalsIgnoreCase(colls[i])) {
					current = current.getNext();
				}
				stack.push(current.getElement().eval());

			} else {
				double aux = stack.pop();
				double aux1 = stack.pop();
				if (colls[i].equalsIgnoreCase("+")) {
					stack.push(aux + aux1);
				}
				if (colls[i].equalsIgnoreCase("-")) {
					stack.push(aux1 - aux);
				}
				if (colls[i].equalsIgnoreCase("*")) {
					stack.push(aux1 * aux);
				}
				if (colls[i].equalsIgnoreCase("/")) {
					stack.push(aux1 / aux);
				}
				if (colls[i].equalsIgnoreCase("^")) {
					stack.push(Math.pow(aux1, aux));
				}

			}

		}
		return stack.pop();
	}

}
