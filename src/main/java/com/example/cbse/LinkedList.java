package com.example.cbse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class LinkedList {
	

	Flight head;
	

	LinkedList() {
		this.head = null;
	}

	public boolean isEmpty() {
		return head == null;
	}


	public void add(Flight newFlight) {
		if (this.isEmpty()) {
			newFlight.next = head;
			head = newFlight;
		} else {
			Flight previous = head;
			Flight current = head;

			while (current != null) {
				previous = current;
				current = current.next;
			}

			Flight temp = current;
			previous.next = newFlight;
			current = newFlight;
			current.next = temp;
		}
	}


	public Flight search(String string) {
		Flight current = head;

		while (current != null) {
			if (string.toUpperCase().compareTo(current.getFlightNo()) == 0
					|| string.toUpperCase().compareTo(current.getDateOfFlight()) == 0) {
				return current;
			} else {
				current = current.next;
			}
		}

		System.out.println("Flight not found.");
		return null;
	}

	public void display() {
		if (!this.isEmpty()) {
			Flight current = head;

			while (current != null) {
				current.display();
				current = current.next;
			}
		}
	}

}