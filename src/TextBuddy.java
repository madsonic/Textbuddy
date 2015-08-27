import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Able to perform add, delete, clear, display
 * 
 * All the above command keywords must be the first word of the input
 * Everything thereafter is considered arguments
 * 
 * Example use
 * add string
 * add sometext more text
 * Adds sometext more text to the list
 * 
 * display
 * Enumerates all added text. Numbering starts from 1
 * 
 * delete index
 * Removes item from output file and buffer
 * 
 * clear
 * Same as delete every index
 * 
 * @author Gerald
 *
 */
public class TextBuddy {
	
	private static final String FILE_LOCATION = "../";

	private static final String MESSAGE_ASK_INPUT = "Command: ";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. ";
	private static final String MESSAGE_FILE_READY = " is ready for use";	
	
	private static final String FORMAT_NEW_LINE = "\n";
	
	// Possible commands
	private static final String[] COMMANDS = {"add", "delete", "display", "clear"};
	
	private static BufferedWriter output;
	private static Scanner scanner = new Scanner(System.in);
	private static ArrayList<String> itemBuffer = new ArrayList<String>();
	
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

	// Perform action based on command given
	private static void executeCommand(String cmd, String param, String fileName) {
		if (cmd.equalsIgnoreCase(COMMANDS[0])) {        // add
			addToBuffer(param);
			appendToFile();
			addSuccess(fileName, param);
		} else if (cmd.equalsIgnoreCase(COMMANDS[1])) { // delete
			delete(param);
		} else if (cmd.equalsIgnoreCase(COMMANDS[2])) { // display
			displayAllL(fileName);
		} else if (cmd.equalsIgnoreCase(COMMANDS[3])) { // clear
			System.out.println("clear");
		} else {
			
		}
	}
	
	// List out all items in the buffer
	private static void displayAllL(String fileName) {
		for (int i = 0; i < itemBuffer.size(); i++) {
			int index = i + 1;
			System.out.println(index + ". " + itemBuffer.get(i));
		}
	}
	
	// Append param to item buffer
	private static void addToBuffer(String param) {
		itemBuffer.add(param);
	}
	
	/**
	 * Removes item from buffer. specified by 1 based index
	 * @param index
	 */
	private static void delete(String index) {
		int i = Integer.parseInt(index) - 1; // adjust index for 0 based buffer
		itemBuffer.remove(i); 
	}
	
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
	
	private static String getParam(String input) {
		int start = input.indexOf(" ");
		return input.substring(start + 1);
	}
	
	// Append last item in item buffer to file
	private static void appendToFile() {
		try {
			int last = itemBuffer.size() - 1;
			
			output.write(itemBuffer.get(last));
			output.flush();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	private static void addSuccess(String fileName, String text) {
		System.out.println("added to " + fileName + ": \"" + text + "\"");
	}
}
