import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.Scanner;

public class TextBuddy {
	
	private static final String FILE_FORMAT = ".txt";
	private static final String FILE_LOCATION = "../";

	private static final String MESSAGE_ASK_INPUT = "Command:";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command";
	
	private static final String FORMAT_NEW_LINE = "\n";

	// Possible commands
	private static final String[] COMMANDS = {"add", "delete", "display", "clear"};
	
	private static BufferedWriter out;
	
	public static void main(String[] fileName) {
		try {
			out = getWriter(fileName);
			String input = getInput();
			String cmd = getCommand(input);
			executeCommand(cmd, getParam(input));
			
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
		String delimiter = "\\s";
		return input.trim().split(delimiter)[0];
	}
	
	// Perform action based on command given
	private static Boolean executeCommand(String cmd, String text) {
		Boolean flag = false;
		String msg = "some success message";
		
		if (cmd.equalsIgnoreCase(COMMANDS[0])) {        // add
			appendToFile(text);
			flag = true;
		} else if (cmd.equalsIgnoreCase(COMMANDS[1])) { // delete
			System.out.println("delete");
		} else if (cmd.equalsIgnoreCase(COMMANDS[2])) { // display
			System.out.println("display");
		} else if (cmd.equalsIgnoreCase(COMMANDS[3])) { // clear
			System.out.println("clear");
		} else {
			
		}
		
		System.out.println(msg);
		System.out.println();
		return flag;
	}

	private static String getParam(String input) {
		int start = input.indexOf(" ");
		return input.substring(start + 1);
	}
	
	private static void appendToFile(String input) {
		try {
			out.write(input + FORMAT_NEW_LINE);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
 	
	
}
