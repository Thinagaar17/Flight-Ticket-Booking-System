package com.example.cbse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class Utils {

	public static String getSeatNo(int n) {
		int first = (n / 6) + 1;
		String firstChar = null;

		switch (first) {
		case 1:
			firstChar = "1";
			break;

		case 2:
			firstChar = "2";
			break;

		case 3:
			firstChar = "3";
			break;

		case 4:
			firstChar = "4";
			break;

		case 5:
			firstChar = "5";
			break;
		}

		int second = n % 6;
		String secondChar = null;

		switch (second) {
		case 0:
			secondChar = "A";
			break;

		case 1:
			secondChar = "B";
			break;

		case 2:
			secondChar = "C";
			break;

		case 3:
			secondChar = "D";
			break;

		case 4:
			secondChar = "E";
			break;

		case 5:
			secondChar = "F";
			break;
		}

		String seat = firstChar + secondChar;

		return seat;
	}

	public static int getSeatNo(String string) {
		char first = string.charAt(0);
		int firstNum = 0;

		switch (first) {
		case '1':
			firstNum = 0;
			break;

		case '2':
			firstNum = 6;
			break;

		case '3':
			firstNum = 12;
			break;

		case '4':
			firstNum = 18;
			break;

		case '5':
			firstNum = 24;
			break;

		}

		char second = string.charAt(1);
		int secondNum = 0;

		switch (second) {
		case 'A':
			secondNum = 0;
			break;

		case 'B':
			secondNum = 1;
			break;

		case 'C':
			secondNum = 2;
			break;

		case 'D':
			secondNum = 3;
			break;

		case 'E':
			secondNum = 4;
			break;

		case 'F':
			secondNum = 5;
			break;
		}

		int seat = firstNum + secondNum;

		return seat;
	}

}