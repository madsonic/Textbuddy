package com.textbuddy;

public class Driver {
	public static void main(String[] args) {
		Logic logic = new Logic(args[0]);
		Parser p;
		
		logic.msgCentre.welcome();
		do {
			logic.msgCentre.ask();
			p = new Parser(logic.getInput());
			logic.executeCommand(p.getAction());
		} while(logic.hasNextLine());
	}
}
