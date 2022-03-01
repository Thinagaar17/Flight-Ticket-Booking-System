package com.example.cbse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class Queue {

	public int head, tail;
	public final static int INITIAL_CAPACITY = 16;

	@Autowired
	public Ticket[] array;

	Queue() {
		this.head = 0;
		this.tail = 0;
		this.array = new Ticket[INITIAL_CAPACITY];
//		System.out.println(this.array.length);
	}

	public boolean isEmpty() {
		return tail == 0;
	}

	public boolean isFull() {
		return tail >= array.length;
	}

	public void ensureCapacity() {
		if (this.isFull()) {
			Ticket[] newArray = new Ticket[array.length * 2 + 1];
			System.arraycopy(array, 0, newArray, 0, tail);
			array = newArray;
		}
	}

	@Autowired
	public void enqueue(Ticket ticket) {
		ensureCapacity();
		array[tail] = ticket;
		tail++;
	}

	public Ticket dequeue() {
		if (this.isEmpty()) {
			return null;
		} else {
			Ticket temp = array[head];

			for (int i = 0; i < tail - 1; i++) {
				array[i] = array[i + 1];
				array[tail - 1] = null;
			}

			tail--;

			return temp;
		}
	}

	public Ticket dequeue(String string) {
		if (this.isEmpty()) {
			return null;
		} else {
			for (int i = 0; i < tail; i++) {
				if (array[i].getCustomerName() != null || array[i].getIdentity() != null
						|| array[i].getEmailAddress() != null) {
					if (array[i].getCustomerName().equalsIgnoreCase(string)
							|| array[i].getIdentity().equalsIgnoreCase(string)
							|| array[i].getEmailAddress().equalsIgnoreCase(string)) {
						Ticket temp = array[i];

						for (int j = i; j < tail - 1; j++) {
							array[i] = array[i + 1];
							array[tail - 1] = null;
						}

						return temp;
					}
				}
			}

			tail--;

			return null;
		}
	}

	public boolean search(String string) {
		if (this.isEmpty()) {
			return false;
		} else {
			for (Ticket x : array) {
				if (x != null) { // changes
					if (x.getCustomerName() != null || x.getIdentity() != null || x.getEmailAddress() != null) {
						if (x.getCustomerName().equalsIgnoreCase(string) || x.getIdentity().equalsIgnoreCase(string)
								|| x.getEmailAddress().equalsIgnoreCase(string)) {
							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public Ticket[] getArray() {
		return array;
	}

	@Autowired
	public void setArray(Ticket[] array) {
		this.array = array;
	}

	public int getTail() {
		return tail;
	}

	public void setTail(int tail) {
		this.tail = tail;
	}

}