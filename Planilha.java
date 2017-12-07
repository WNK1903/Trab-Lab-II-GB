package br.unisinos.lab2.TrabGB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import br.com.unisinos.lab1.TrabGB.Dependente;
import br.com.unisinos.lab1.TrabGB.PoupancaSaude;

public class Planilha {

	//Lista duplamente encadeada que armazena as células.
	protected static DoublyLinkedList<Cell> list = new DoublyLinkedList<>();

	public void menu() {
		Teclado t = new Teclado();
		int opcao = 0;

		do {
			System.out.println(
					"Digite a opção: \n 1 - Incluir célula \n 2 - Avaliar célula \n 3 - Avaliar todas as células"
							+ " \n 4 - Conteúdo de todas as células \n 5 - Salvar em arquivo \n 6 - Abrir arquivo \n 9 - Sair");
			do {
				opcao = t.leInt();
				if ((opcao < 1 || opcao > 6) && opcao != 9) {
					System.out.println("Opção inválida. Digite novamente!");
				}
			} while ((opcao < 1 || opcao > 6) && opcao != 9);
			// Opção 1 - incluir uma nova célula.
			if (opcao == 1) {
				String identificador = t.leString("Digite o identificador da célula:");
				DNode<Cell> current = list.head;
				boolean aux = true;
				// Verifica se já existe uma célula com esse identificador.
				while (current != null) {
					if (current.getElement().getId().equalsIgnoreCase(identificador)) {
						aux = false;
					}
					current = current.getNext();
				}
				// Caso já exista uma célula com esse identificador.
				if (!aux) {
					System.out.println("Já existe uma célula com esse identificador!");
				} else {
					String valor = t.leString("Digite o valor da célula: ");
					if (valor.charAt(0) == '=') {
						String newValor = valor.replaceAll("=", "");
						FormulaCell cell = new FormulaCell(identificador, newValor);
						list.insertLast(cell);
					} else {
						ValueCell cell = new ValueCell(identificador, Double.parseDouble(valor));
						list.insertLast(cell);
					}
				}

			}
			// Opção 2 - Avaliar uma determinada célula.
			else if (opcao == 2) {
				String identificador = t.leString("Digite o identificador da célula para avaliar: ");
				DNode<Cell> current = list.head;
				boolean aux = false;
				// Verifica se existe uma célula com o identificador fornecido.
				while (current != null) {
					if (current.getElement().getId().equalsIgnoreCase(identificador)) {
						aux = true;
						break;
					}
					current = current.getNext();
				}
				// Caso não exista célula com esse identificador.
				if (!aux) {
					System.out.println("Não existe célula com esse identificador!");

				} else {
					if (current.getElement() instanceof FormulaCell) {
						System.out.println("Valor da célula: " + ((FormulaCell) current.getElement()).eval());
					} else {
						System.out.println("Valor da célula: " + ((ValueCell) current.getElement()).eval());
					}
				}

			}
			// Opção 3 - Avalia todas as células.
			else if (opcao == 3) {
				if (list.isEmpty()) {
					System.out.println("Planilha ainda não possui células.");
				}
				DNode<Cell> current = list.head;
				while (current != null) {
					if (current.getElement() instanceof FormulaCell) {
						System.out.println("Célula: " + current.getElement().getId() + " Valor: "
								+ ((FormulaCell) current.getElement()).eval());
					} else {
						System.out.println("Célula: " + current.getElement().getId() + " Valor: "
								+ ((ValueCell) current.getElement()).eval());
					}
					current = current.getNext();

				}

			}
			//Opção 4 - Exibe o conteúdo de todas as células.
			else if (opcao == 4) {
				if (list.isEmpty()) {
					System.out.println("Planilha ainda não possui células!");
				}
				DNode<Cell> current = list.head;
				while (current != null) {
					if (current.getElement() instanceof FormulaCell) {
						System.out.println("Célula: " + current.getElement().getId() + " Conteúdo: "
								+ ((FormulaCell) current.getElement()).getFormula());
					} else {
						System.out.println("Célula: " + current.getElement().getId() + " Conteúdo: "
								+ ((ValueCell) current.getElement()).getValue());
					}
					current = current.getNext();

				}

			}
			// Opção 5 - Gravar todas as células em arquivo.
			else if (opcao == 5) {
				File arquivo = new File(t.leString("Digite a localização do arquivo para gravar."));
				save(arquivo, list);

			} 
			//Opção 6 - Insere as células na lista duplamente encadeada a partir de um arquivo. 
			else if (opcao == 6) {
				File arquivo = new File(t.leString("Digite a localização do arquivo. "));
				Cell[] celulas = read(arquivo);
				for (int i = 0; i < celulas.length; i++) {
					list.insertLast(celulas[i]);
				}

			}
			if (opcao != 9) {
				t.leString("Tecle ENTER para voltar ao menu.");
			}
		} while (opcao != 9);

	}

	// Método que salva em arquivo as celulas presentes na lista duplamente
	// encadeada.
	private static void save(File f, DoublyLinkedList<Cell> lista) {
		Cell[] copy = new Cell[lista.numElements];
		for (int i = 0; i < lista.numElements; i++) {
			copy[i] = lista.get(i);
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(copy);
			os.close();
		} catch (IOException e) {
			System.out.println("Erro ao gravar objeto.");
		}
	}

	// Método que lê os objetos(do tipo Cell) a partir de um arquivo.
	private static Cell[] read(File f) {
		Cell[] retorno = null;
		try {
			FileInputStream fos = new FileInputStream(f);
			ObjectInputStream os = new ObjectInputStream(fos);
			retorno = (Cell[]) os.readObject();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ce) {
			System.out.println("Objeto não encontrado.");
		}
		return retorno;
	}

	public static void main(String[] args) {
		Planilha p = new Planilha();
		p.menu();
	}

}
