package br.unisinos.lab2.TrabGB;
public interface Stack<E> {
  public boolean isEmpty();

  public boolean isFull();

  public int numElements();

  public void push(E element) throws OverflowException;

  public E pop() throws UnderflowException;

  public E top() throws UnderflowException;
}