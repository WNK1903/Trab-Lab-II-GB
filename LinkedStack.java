package br.unisinos.lab2.TrabGB;
public class LinkedStack<E> implements Stack<E> {
  protected Node<E> top;
  protected int numElements;

  public LinkedStack() {
    top = null;
    numElements = 0;
  }

  public boolean isEmpty() {
    return numElements == 0;
  }

  public boolean isFull() {
    // uma pilha com alocação dinâmica nunca estará cheia!
    return false;
  }
  
  public int numElements() {
    return numElements;
  }

  public void push(E element) {
    // cria um novo nodo e retorna o novo "top"
    Node<E> newNode = new Node<E>(element);
    newNode.setNext(top);
    top = newNode;
    numElements++;
  }

  public E pop() throws UnderflowException {
    if (isEmpty())
      throw new UnderflowException();
    // guarda uma referê�?ncia ao elemento atualmente no topo
    E element = top.getElement();
    // o novo topo passa a ser o elemento abaixo do atual
    top = top.getNext();
    // ajusta o total de elementos
    numElements--;

    return element;
  }
  
  public E top() throws UnderflowException {
    if (isEmpty())
      throw new UnderflowException();
    return top.getElement();
  }

  public String toString() {
    if (isEmpty())
      return "[Empty]";
    else {
      String s = "[";
      Node<E> cur = top;
      while (cur != null) {
        s += cur.getElement() + ",";
        cur = cur.getNext();
      }
      return s.substring(0, s.length() - 1) + "]";
    }
  }
}
  
  
  
  
