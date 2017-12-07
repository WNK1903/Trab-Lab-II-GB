package br.unisinos.lab2.TrabGB;

import java.io.Serializable;

public class DoublyLinkedList<E> implements List<E>, Serializable {
	
	private static final long serialVersionUID = 1L;
	protected DNode<E> head;
	protected DNode<E> tail;
	protected int numElements;

	public DoublyLinkedList() {
		head = tail = null;
		numElements = 0;
	}

	public int numElements() {
		return numElements;
	}

	public boolean isEmpty() {
		return numElements == 0;
	}

	public boolean isFull() {
		return false;
	}
	
	public void insertFirst(E element) {
		DNode<E> newNode = new DNode<E>(element);
		if (isEmpty())
			head = tail = newNode;
		else {
			newNode.setNext(head);
			head.setPrevious(newNode);
			head = newNode;
		}
		numElements++;
	}
		
	public void insertLast(E element) {
		DNode<E> newNode = new DNode<E>(element);
		if (isEmpty())
			head = tail = newNode;
		else {
			tail.setNext(newNode);
			newNode.setPrevious(tail);
			tail = newNode;
		}

		numElements++;
	}

	public void insert(E element, int pos) {
		if (pos < 0  ||  pos > numElements)
			throw new IndexOutOfBoundsException();
		
		if (pos == 0)
			insertFirst(element);
		else if (pos == numElements) 
			insertLast(element);
		else { 
			DNode<E> prev = head;
			for (int i = 0; i < pos-1; i++)
				prev = prev.getNext();
			
			DNode<E> newNode = new DNode<E>(element);
			newNode.setPrevious(prev);
			newNode.setNext(prev.getNext());
			prev.getNext().setPrevious(newNode);
			prev.setNext(newNode);
			numElements++;
		}
	}

	public E removeFirst() {
		if (isEmpty())
			throw new UnderflowException();

		E element = head.getElement();

		if (head == tail)
			head = tail = null;
		else {
			head = head.getNext();
			head.setPrevious(null);
		}

		numElements--;
		return element;
	}
	
	public E removeLast() {
		if (isEmpty())
			throw new UnderflowException();

		E element = tail.getElement();
		if (head == tail)
			head = tail = null;
		else {
			tail = tail.getPrevious();
			tail.setNext(null);
		}

		numElements--;
		return element;
	}

	public E remove(int pos) {
		if (pos < 0  ||  pos >= numElements)
			throw new IndexOutOfBoundsException();
		
		if (pos == 0)
			return removeFirst();
		else if (pos == numElements-1) 
			return removeLast();
		else { 
			DNode<E> prev = head;
			for (int i = 0; i < pos-1; i++)
				prev = prev.getNext();
			
			E element = prev.getNext().getElement();
			prev.setNext(prev.getNext().getNext());
			prev.getNext().setPrevious(prev);

			numElements--;
			return element;
		}
	}

	public E get(int pos) {
		if (pos < 0  ||  pos >= numElements)
			throw new IndexOutOfBoundsException();

		DNode<E> current = head;
		for (int i = 0; i < pos; i++)
			current = current.getNext();
		
		return current.getElement();
	}
	
	public int search(E element) {
		DNode<E> current = head;
		int i = 0;
		while (current != null) {
			if (element.equals(current.getElement()))
					return i;
			i++;
			current = current.getNext();
		}
		
		return -1;
	}

	public String toString() {
		String s = "";

		DNode<E> current = head;
		while (current != null) {
			s += current.getElement() + " ";
			current = current.getNext();
		}
		return s;
	}
}