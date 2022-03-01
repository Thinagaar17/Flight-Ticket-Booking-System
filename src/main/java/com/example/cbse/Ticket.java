package com.example.cbse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class Ticket {

	public String customerName = null;
	public String identity = null;
	public String emailAddress = null;
	public String seatNo;

	
	Ticket() {
	}

	public void setTicket4(String name, String id, String email) {
		this.customerName = name;
		this.identity = id;
		this.emailAddress = email;
		this.seatNo = null;
	}

	public void setTicket5(String name, String id, String email, String seat) {
		this.customerName = name;
		this.identity = id;
		this.emailAddress = email;
		this.seatNo = seat;
	}
	
	public void displayTesting() {
		System.out.println("Ticket working ...");
	}

	public boolean isNull() {
		return (this.customerName == null && this.identity == null && this.emailAddress == null);
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String name) {
		this.customerName = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public void display() {
		String out = "Name: " + this.getCustomerName() + "\n";
		out += "Passport number / Identity card number: " + this.getIdentity() + "\n";
		out += "Email address: " + this.getEmailAddress() + "\n";
		out += "Seat: " + this.getSeatNo();
		System.out.println(out);
	}
}