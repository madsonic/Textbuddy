import java.io.File;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.Scanner;

public class TextBuddy {
	
	private static final String FILE_FORMAT = ".txt";
	private static final String FILE_LOCATION = "../";

	private static final String MESSAGE_ASK_INPUT = "Command:";

	// Possible commands
	enum COMMANDS {
		ADD, DELETE, DISPLAY, CLEAR;
	}
	
	public static void main(String[] fileName) {
		try {
			BufferedWriter out = getWriter(fileName);
			String input = getInput();
			String cmd = getCommand(input);
			
			System.out.println(cmd);
			out.write(input);
			
			out.close();
		} catch (IOException e) {
			// TODO: handle exception. print exception for now
			System.out.println(e);
		}
	}

	private static String getInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(MESSAGE_ASK_INPUT);
		String input = scanner.nextLine();
		scanner.close();
		
		return input;
	}

	private static BufferedWriter getWriter(String[] fileName) throws IOException {
		File file = new File(FILE_LOCATION + fileName[0] + FILE_FORMAT);
		return new BufferedWriter(new FileWriter(file));
	}
	
	private static String getCommand(String input) {
		return getFirstWord(input);
	}
	
	// Read first word as command keyword
	private static String getFirstWord(String input) {
		// split at white space
		return input.trim().split("\\s")[0];
	}
	
	
}
