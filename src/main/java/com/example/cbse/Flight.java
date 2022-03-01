package com.example.cbse;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Flight {

	public String flightNo;
	public String dateOfFlight;

	@Autowired
	public Ticket[] confirmedList;

	@Autowired
	public Queue waitingList;

	private final static int CAPACITY = 5;
	Flight next;

	public Flight() {
//		this.flightNo = no;
//		this.dateOfFlight = date;
//		// this.waitingList = waitingList;
//
//		/**
//		 * Creating new array, so not violating bean principle
//		 */

	}

	public void setData(String no, String date, Queue queue) {
		this.flightNo = no;
		this.dateOfFlight = date;
		/**
		 * Creating new array, so not violating bean principle
		 */
		this.waitingList = queue;
		this.confirmedList = new Ticket[CAPACITY];

	}

	public boolean isFull() {
		return this.getAvailableSeat() == 0; // changes
	}

	public boolean isEmpty() {
		return this.getAvailableSeat() == CAPACITY; // changes
	}

	public void display() {
		String out = "Flight No: " + flightNo + "\n";
		out += "Date of Flight: " + dateOfFlight + "\n";
		out += "Available Seat: " + this.getAvailableSeat() + "\n";
		out += "-------------------------------";

		System.out.println(out);
	}

	public void book(Ticket t) {
		if (this.isFull()) {
			waitingList.enqueue(t);
			System.out.println("All tickets have been booked. Passenger detail have been added into waiting list. "
					+ "Ticket will be automatically book when there is empty slot.");
		} else {
			for (int i = 0; i < CAPACITY; i++) {
				if (confirmedList[i] == null) {
					t.setSeatNo(Utils.getSeatNo(i));
					confirmedList[i] = t;
					System.out.println("Ticket has been successfully booked. Ticket information is as below: ");
					confirmedList[i].display();
					break;
				}
			}
		}
	}

	public void add(Ticket t, int n) {
		confirmedList[n] = t;
	}

	@Autowired
	public void add(Ticket t) {
		this.waitingList.enqueue(t);
	}

	public void editInformation(String string) {

		Scanner scanner = new Scanner(System.in);
		if (checkConfirmedList(string) == true || waitingList.search(string) == true) {
			System.out.println("Which information you would like to edit?");
			System.out.println("1 - Name \n2 - Passport no / Identity card no \n3 - Email address \n4 - All");
			System.out.print("Please enter respective number for the information that you would like to edit "
					+ "(Only one number within 1 to 4 is allowed): ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.print("Update name = ");
				String newName = scanner.nextLine();
				for (int i = 0; i < CAPACITY; i++) {
					if (this.checkConfirmedList(string) == true) {
						if (confirmedList[i].getCustomerName().equalsIgnoreCase(string)
								|| confirmedList[i].getIdentity().equalsIgnoreCase(string)
								|| confirmedList[i].getEmailAddress().equalsIgnoreCase(string)) {
							confirmedList[i].setCustomerName(newName);
							System.out.println("Successfully update.");
							confirmedList[i].display();
						}
					}
				}

				for (Ticket x : waitingList.getArray()) {
					if (this.waitingList.search(string) == true) {
						if (x.getCustomerName() != null || x.getIdentity() != null || x.getEmailAddress() != null) {
							if (x.getCustomerName().equalsIgnoreCase(string) || x.getIdentity().equalsIgnoreCase(string)
									|| x.getEmailAddress().equalsIgnoreCase(string)) {
								x.setCustomerName(newName);
								System.out.println("Successfully update.");
								x.display();
							}
						}
					}
				}

				break;

			case 2:
				System.out.print("Update passport no / identity card no = ");
				String newIdentity = scanner.nextLine();
				for (int i = 0; i < CAPACITY; i++) {
					if (this.checkConfirmedList(string) == true) {
						if (confirmedList[i] != null) {
							if (confirmedList[i].getCustomerName().equalsIgnoreCase(string)
									|| confirmedList[i].getIdentity().equalsIgnoreCase(string)
									|| confirmedList[i].getEmailAddress().equalsIgnoreCase(string)) {
								confirmedList[i].setIdentity(newIdentity);
								System.out.println("Successfully update.");
								confirmedList[i].display();
							}
						}
					}
				}

				for (Ticket x : waitingList.getArray()) {
					if (this.waitingList.search(string) == true) {
						if (x != null) {
							if (x.getCustomerName() != null || x.getIdentity() != null || x.getEmailAddress() != null) {
								if (x.getCustomerName().equalsIgnoreCase(string)
										|| x.getIdentity().equalsIgnoreCase(string)
										|| x.getEmailAddress().equalsIgnoreCase(string)) {
									x.setIdentity(newIdentity);
									System.out.println("Successfully update.");
									x.display();
								}
							}
						}
					}
				}
				break;

			case 3:
				System.out.print("Update email address = ");
				String newEmail = scanner.nextLine();
				for (int i = 0; i < CAPACITY; i++) {
					if (this.checkConfirmedList(string) == true) {
						if (confirmedList[i] != null) {
							if (confirmedList[i].getCustomerName().equalsIgnoreCase(string)
									|| confirmedList[i].getIdentity().equalsIgnoreCase(string)
									|| confirmedList[i].getEmailAddress().equalsIgnoreCase(string)) {

								confirmedList[i].setEmailAddress(newEmail);
								System.out.println("Successfully update.");
								confirmedList[i].display();
							}
						}
					}
				}

				for (Ticket x : waitingList.getArray()) {
					if (this.waitingList.search(string) == true) {
						if (x != null) {
							if (x.getCustomerName() != null || x.getIdentity() != null || x.getEmailAddress() != null) {
								if (x.getCustomerName().equalsIgnoreCase(string)
										|| x.getIdentity().equalsIgnoreCase(string)
										|| x.getEmailAddress().equalsIgnoreCase(string)) {
									x.setEmailAddress(newEmail);
									System.out.println("Successfully update.");
									x.display();
								}
							}
						}
					}
				}
				break;

			case 4:
				System.out.print("Update all information (seperate respective information by comma in order of name"
						+ ",passport no / identity card no,email address) = ");
				String newInfo = scanner.nextLine();
				String[] newInformation = newInfo.split(",");

				if (newInformation.length == 3) {
					for (int i = 0; i < CAPACITY; i++) {
						if (this.checkConfirmedList(string) == true) {
							if (confirmedList[i] != null) {
								if (confirmedList[i].getCustomerName().equalsIgnoreCase(string)
										|| confirmedList[i].getIdentity().equalsIgnoreCase(string)
										|| confirmedList[i].getEmailAddress().equalsIgnoreCase(string)) {

									confirmedList[i].setCustomerName(newInformation[0]);
									confirmedList[i].setIdentity(newInformation[1]);
									confirmedList[i].setEmailAddress(newInformation[2]);
									System.out.println("Successfully update.");
									confirmedList[i].display();
								}
							}
						}
					}

					for (Ticket x : waitingList.getArray()) {
						if (this.waitingList.search(string) == true) {
							if (x != null) {
								if (x.getCustomerName() != null || x.getIdentity() != null
										|| x.getEmailAddress() != null) {
									if (x.getCustomerName().equalsIgnoreCase(string)
											|| x.getIdentity().equalsIgnoreCase(string)
											|| x.getEmailAddress().equalsIgnoreCase(string)) {
										x.setCustomerName(newInformation[0]);
										x.setIdentity(newInformation[1]);
										x.setEmailAddress(newInformation[2]);
										System.out.println("Successfully update.");
										x.display();
									}
								}
							}
						}
					}

				}
				break;

			default:
				System.out.println("Invalid number.");
				break;

			}

		} else {
			System.out.println(string + " information cannot be found.");
		}

	}

	public void viewStatus(String string) {
		if (checkConfirmedList(string) == true) {
			System.out.println(string + " information is found in confirmed list.");
		} else if (waitingList.search(string) == true) {
			System.out.println(string + " information is found in waiting list.");
		} else {
			System.out.println(string + " information not found.");
		}

	}

	public boolean checkConfirmedList(String string) {
		if (!this.isEmpty()) {
			for (int i = 0; i < CAPACITY; i++) {
				if (confirmedList[i] != null) {
					if (confirmedList[i].getCustomerName().equalsIgnoreCase(string)
							|| confirmedList[i].getIdentity().equalsIgnoreCase(string)
							|| confirmedList[i].getEmailAddress().equalsIgnoreCase(string)) {

						return true;
					}
				}
			}
		}
		return false;
	}

	public int getAvailableSeat() {
		int counter = 0;
		for (int i = 0; i < CAPACITY; i++) {
			if (confirmedList[i] == null) {
				counter++;
			}
		}

		return counter;
	}

	public void cancel(String string) {
		if (checkConfirmedList(string) == true) {
			for (int i = 0; i < CAPACITY; i++) {
				if (confirmedList[i] != null) {
					if (confirmedList[i].getCustomerName().equalsIgnoreCase(string)
							|| confirmedList[i].getIdentity().equalsIgnoreCase(string)
							|| confirmedList[i].getEmailAddress().equalsIgnoreCase(string)) {

						Ticket temp = confirmedList[i];
						String seatNo = confirmedList[i].getSeatNo(); // changes
						if (waitingList.isEmpty()) {
							confirmedList[i] = null;
						} else {
							confirmedList[i] = waitingList.dequeue();
							confirmedList[i].setSeatNo(seatNo); // changes
						}
						System.out
								.println("Passenger with following information has been removed from confirmed list: ");
						temp.display();
					}
				}
			}
		} else if (waitingList.search(string) == true) {
			Ticket temp = waitingList.dequeue(string);
			System.out.println("Passenger with following information has been removed from waiting list: ");
			temp.display();

		} else {
			System.out.println(string + " information cannot be found.");
		}
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDateOfFlight() {
		return dateOfFlight;
	}

	public void setDateOfFlight(String dateOfFlight) {
		this.dateOfFlight = dateOfFlight;
	}

	public Ticket[] getConfirmedList() {
		return confirmedList;
	}

	@Autowired
	public void setConfirmedList(Ticket[] confirmedList) {
		this.confirmedList = confirmedList;
	}

	public Queue getWaitingList() {
		return waitingList;
	}

	@Autowired
	public void setWaitingList(Queue waitingList) {
		this.waitingList = waitingList;
	}

}
