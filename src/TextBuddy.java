import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class TextBuddy {
	
	private static final String FILE_LOCATION = "../";

	private static final String MESSAGE_ASK_INPUT = "Command: ";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. ";
	private static final String MESSAGE_FILE_READY = " is ready for use";	
	
	private static final String FORMAT_NEW_LINE = "\n";
	
	// Items in list
	private static int numItems = 1;

	// Possible commands
	private static final String[] COMMANDS = {"add", "delete", "display", "clear"};
	
	private static BufferedWriter output;
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		String fileName = args[0];
		
		System.out.println(MESSAGE_WELCOME + fileName + MESSAGE_FILE_READY);
		try {
			output = getWriter(fileName);
			do {
				System.out.println(MESSAGE_ASK_INPUT);
				String input = scanner.nextLine();
				executeCommand(getCommand(input), getParam(input), fileName);
			} while(scanner.hasNextLine());
			
			output.close();
			scanner.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

//	private static String getInput() {
//		try {
//			System.out.println(MESSAGE_ASK_INPUT);
//			String input = scanner.nextLine();
//			scanner.close();
//			
//			return input;			
//		} catch (IOException e) {
//			System.out.println(e);
//		}
//	}

	private static BufferedWriter getWriter(String fileName) throws IOException {
		File file = new File(FILE_LOCATION + fileName);
		return new BufferedWriter(new FileWriter(file));
	}
	
	private static String getCommand(String input) {
		return getFirstWord(input);
	}
	
	// Read first word as command keyword
	private static String getFirstWord(String input) {
		// split at white space
		String delimiter = "\\s";
		return input.trim().split(delimiter)[0];
	}
	
	// Perform action based on command given
	private static void executeCommand(String cmd, String param, String fileName) {
		if (cmd.equalsIgnoreCase(COMMANDS[0])) {        // add
			appendToFile(param, fileName);
			addSuccess(fileName, param);
		} else if (cmd.equalsIgnoreCase(COMMANDS[1])) { // delete
			System.out.println("delete");
		} else if (cmd.equalsIgnoreCase(COMMANDS[2])) { // display
			displayAllL(fileName);
		} else if (cmd.equalsIgnoreCase(COMMANDS[3])) { // clear
			System.out.println("clear");
		} else {
			
		}
	}

	private static void displayAllL(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILE_LOCATION + fileName));
			String currLine;
			System.out.println(reader.readLine());
			while ((currLine = reader.readLine()) != null) {
				System.out.println(currLine);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private static String getParam(String input) {
		int start = input.indexOf(" ");
		return input.substring(start + 1);
	}
	
	private static void appendToFile(String text, String fileName) {
		try {
			output.write(Integer.toString(numItems++) + ". " + text + FORMAT_NEW_LINE);
			output.flush();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	private static void addSuccess(String fileName, String text) {
		System.out.println("added to " + fileName + ": \"" + text + "\"");
	}
}
