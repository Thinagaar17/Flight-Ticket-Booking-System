package com.example.cbse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketCounter {

//	public static LinkedList flightList;
//	public static Flight flight;
//	public static String name;

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(TicketCounter.class, args);

		Scanner scanner = new Scanner(System.in);
		int i = 0;

		LinkedList flightList = (LinkedList) context.getBean(LinkedList.class);

		try {
			File flight = new File("flight.txt");

			if (flight.exists()) {
				Scanner input01 = new Scanner(new FileInputStream(flight.getName()));

				while (input01.hasNextLine()) {
					Ticket ticket = (Ticket) context.getBean(Ticket.class);
					Queue queue = (Queue) context.getBean(Queue.class);
					Flight flight2 = (Flight) context.getBean(Flight.class);
					String[] flightDetail = input01.nextLine().split(",");
					flight2.setData(flightDetail[0], flightDetail[1], queue);
					flightList.add(flight2);
				}

				input01.close();
			}

			File ticket = new File("ticket.txt");
			Utils util = (Utils) context.getBean(Utils.class);

			if (ticket.exists()) {
				Scanner input02 = new Scanner(new FileInputStream(ticket.getName()));

				while (input02.hasNext()) {
					String[] ticketDetail = input02.nextLine().split(",");

					if (ticketDetail.length == 5) {
						Ticket t = (Ticket) context.getBean(Ticket.class);
						t.setTicket5(ticketDetail[1], ticketDetail[2], ticketDetail[3], ticketDetail[4]);
						flightList.search(ticketDetail[0]).add(t, Utils.getSeatNo(ticketDetail[4]));
					}

					if (ticketDetail.length == 4) {
						Ticket t = (Ticket) context.getBean(Ticket.class);
						t.setTicket4(ticketDetail[1], ticketDetail[2], ticketDetail[3]);
						flightList.search(ticketDetail[0]).add(t);
					}
				}

				input02.close();
			}

		} catch (FileNotFoundException e) {

		}

		do {
			flightList.display();
			System.out.print("Enter Flight No / Date Of Flight preferred: ");
			String choice = scanner.next();
			while (flightList.search(choice) == null) {
				System.out.println("Invalid flight.");
				System.out.print("Enter Flight No / Date Of Flight preferred: ");
				choice = scanner.next();
			}

			Flight flight = flightList.search(choice);

			Scanner scanner1 = new Scanner(System.in);

			System.out.println(
					"1 - Book ticket \n2 - Edit personal information \n3 - View ticket status \n4 - Cancel ticket");
			System.out.print(
					"Select a number from the list above for the operation you would like to continue with(-1 to quit) :");
			int option = scanner1.nextInt();
			scanner1.nextLine();

			switch (option) {
			case 1:
				System.out.println("Booking ticket for " + flight.getFlightNo() + " " + flight.getDateOfFlight());
				System.out.print("Enter passenger name: ");
				String name = scanner1.nextLine();

				System.out.print("Enter passenger passport no / identity card no:");
				String identity = scanner1.nextLine();

				System.out.print("Enter passenger email address: ");
				String email = scanner1.nextLine();

				Ticket t = (Ticket) context.getBean(Ticket.class);
				t.setTicket4(name, identity, email);
				flight.book(t);

				break;

			case 2:
				flight.editInformation(askInformation());
				break;

			case 3:
				flight.viewStatus(askInformation());
				break;

			case 4:
				flight.cancel(askInformation());
				break;

			default:
				System.out.println("Invalid number.");
				break;
			}

			System.out.print("Would you like to continue? (0 - No, 1 - Yes) : ");
			i = scanner.nextInt();

		} while (i == 1);

		try {
			File file01 = new File("flight.txt");
			PrintWriter output01 = new PrintWriter(new FileOutputStream(file01.getName()), true);

			if (!flightList.isEmpty()) {
				Flight current = flightList.head;

				while (current != null) {
					output01.write(current.getFlightNo() + "," + current.getDateOfFlight() + "\n");
					current = current.next;
				}

			}

			output01.close();

			File file02 = new File("ticket.txt");

			PrintWriter output02 = new PrintWriter(new FileOutputStream(file02.getName()), true);

			if (!flightList.isEmpty()) {
				Flight current = flightList.head;

				while (current != null) {

					if (!current.isEmpty()) {
						for (int j = 0; j < current.getConfirmedList().length; j++) {
							if (current.getConfirmedList()[j] != null) {
								Ticket t = current.getConfirmedList()[j];
								if (t.getCustomerName() != null || t.getIdentity() != null
										|| t.getEmailAddress() != null) {
									output02.write(current.getFlightNo() + "," + t.getCustomerName() + ","
											+ t.getIdentity() + "," + t.getEmailAddress() + "," + t.getSeatNo() + "\n");
								}

							}
						}
					}

					if (!current.getWaitingList().isEmpty()) {
						for (Ticket t : current.getWaitingList().getArray()) {
							if (t != null) { // changes
								if (t.getCustomerName() != null || t.getIdentity() != null
										|| t.getEmailAddress() != null) {
									output02.write(current.getFlightNo() + "," + t.getCustomerName() + ","
											+ t.getIdentity() + "," + t.getEmailAddress() + "\n");
								}
							}
						}
					}

					current = current.next;
				}
			}

			output02.close();

		} catch (FileNotFoundException e) {

		}
	}

	public static String askInformation() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter either passenger name or passport no / identity card no or email address: ");
		String string = scanner.nextLine();

		return string;
	}

}