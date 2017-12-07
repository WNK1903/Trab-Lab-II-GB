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

	//Lista duplamente encadeada que armazena as c�lulas.
	protected static DoublyLinkedList<Cell> list = new DoublyLinkedList<>();

	public void menu() {
		Teclado t = new Teclado();
		int opcao = 0;

		do {
			System.out.println(
					"Digite a op��o: \n 1 - Incluir c�lula \n 2 - Avaliar c�lula \n 3 - Avaliar todas as c�lulas"
							+ " \n 4 - Conte�do de todas as c�lulas \n 5 - Salvar em arquivo \n 6 - Abrir arquivo \n 9 - Sair");
			do {
				opcao = t.leInt();
				if ((opcao < 1 || opcao > 6) && opcao != 9) {
					System.out.println("Op��o inv�lida. Digite novamente!");
				}
			} while ((opcao < 1 || opcao > 6) && opcao != 9);
			// Op��o 1 - incluir uma nova c�lula.
			if (opcao == 1) {
				String identificador = t.leString("Digite o identificador da c�lula:");
				DNode<Cell> current = list.head;
				boolean aux = true;
				// Verifica se j� existe uma c�lula com esse identificador.
				while (current != null) {
					if (current.getElement().getId().equalsIgnoreCase(identificador)) {
						aux = false;
					}
					current = current.getNext();
				}
				// Caso j� exista uma c�lula com esse identificador.
				if (!aux) {
					System.out.println("J� existe uma c�lula com esse identificador!");
				} else {
					String valor = t.leString("Digite o valor da c�lula: ");
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
			// Op��o 2 - Avaliar uma determinada c�lula.
			else if (opcao == 2) {
				String identificador = t.leString("Digite o identificador da c�lula para avaliar: ");
				DNode<Cell> current = list.head;
				boolean aux = false;
				// Verifica se existe uma c�lula com o identificador fornecido.
				while (current != null) {
					if (current.getElement().getId().equalsIgnoreCase(identificador)) {
						aux = true;
						break;
					}
					current = current.getNext();
				}
				// Caso n�o exista c�lula com esse identificador.
				if (!aux) {
					System.out.println("N�o existe c�lula com esse identificador!");

				} else {
					if (current.getElement() instanceof FormulaCell) {
						System.out.println("Valor da c�lula: " + ((FormulaCell) current.getElement()).eval());
					} else {
						System.out.println("Valor da c�lula: " + ((ValueCell) current.getElement()).eval());
					}
				}

			}
			// Op��o 3 - Avalia todas as c�lulas.
			else if (opcao == 3) {
				if (list.isEmpty()) {
					System.out.println("Planilha ainda n�o possui c�lulas.");
				}
				DNode<Cell> current = list.head;
				while (current != null) {
					if (current.getElement() instanceof FormulaCell) {
						System.out.println("C�lula: " + current.getElement().getId() + " Valor: "
								+ ((FormulaCell) current.getElement()).eval());
					} else {
						System.out.println("C�lula: " + current.getElement().getId() + " Valor: "
								+ ((ValueCell) current.getElement()).eval());
					}
					current = current.getNext();

				}

			}
			//Op��o 4 - Exibe o conte�do de todas as c�lulas.
			else if (opcao == 4) {
				if (list.isEmpty()) {
					System.out.println("Planilha ainda n�o possui c�lulas!");
				}
				DNode<Cell> current = list.head;
				while (current != null) {
					if (current.getElement() instanceof FormulaCell) {
						System.out.println("C�lula: " + current.getElement().getId() + " Conte�do: "
								+ ((FormulaCell) current.getElement()).getFormula());
					} else {
						System.out.println("C�lula: " + current.getElement().getId() + " Conte�do: "
								+ ((ValueCell) current.getElement()).getValue());
					}
					current = current.getNext();

				}

			}
			// Op��o 5 - Gravar todas as c�lulas em arquivo.
			else if (opcao == 5) {
				File arquivo = new File(t.leString("Digite a localiza��o do arquivo para gravar."));
				save(arquivo, list);

			} 
			//Op��o 6 - Insere as c�lulas na lista duplamente encadeada a partir de um arquivo. 
			else if (opcao == 6) {
				File arquivo = new File(t.leString("Digite a localiza��o do arquivo. "));
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

	// M�todo que salva em arquivo as celulas presentes na lista duplamente
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

	// M�todo que l� os objetos(do tipo Cell) a partir de um arquivo.
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
			System.out.println("Objeto n�o encontrado.");
		}
		return retorno;
	}

	public static void main(String[] args) {
		Planilha p = new Planilha();
		p.menu();
	}

}
