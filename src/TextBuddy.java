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
	private static final String MESSAGE_ADD = "added to ";	
	private static final String MESSAGE_CLEAR = "all content deleted from ";
	private static final String MESSAGE_DELETE = "deleted from ";
	private static final String MESSAGE_EMPTY_LIST = "List is empty";
	
	// Possible commands
	private static final String[] COMMANDS = {"add", "delete", "display", "clear"};
	
	private static BufferedWriter output;
	private static Scanner scanner = new Scanner(System.in);
	private static ArrayList<String> itemBuffer = new ArrayList<String>();
	
	public static void main(String[] args) {
		String fileName = args[0];
		
		System.out.println(MESSAGE_WELCOME + fileName + MESSAGE_FILE_READY);
		do {
			System.out.println(MESSAGE_ASK_INPUT);
			String input = scanner.nextLine();
			executeCommand(getCommand(input), getParam(input), fileName);
		} while(scanner.hasNextLine());
	}

	// Perform action based on command given
	private static void executeCommand(String cmd, String param, String fileName) {
		if (cmd.equalsIgnoreCase(COMMANDS[0])) {        // add
			
			addToBuffer(param);
			addSuccess(fileName, param);
			
		} else if (cmd.equalsIgnoreCase(COMMANDS[1])) { // delete
			
			int index = Integer.parseInt(param) - 1;
			String str = itemBuffer.get(index);
			deleteFromBuffer(index);
			deleteSuccess(fileName, str);
			
		} else if (cmd.equalsIgnoreCase(COMMANDS[2])) { // display
			
			displayAll(fileName);
			
		} else if (cmd.equalsIgnoreCase(COMMANDS[3])) { // clear
			
			clear();
			clearSuccess(fileName);
			
		}
		
		writeToFile(fileName);
	}
	
	// List all items in the buffer
	private static void displayAll(String fileName) {
		int len = itemBuffer.size();
		
		if (len == 0) {
			System.out.println(MESSAGE_EMPTY_LIST);
		} else {
			for (int i = 0; i < itemBuffer.size(); i++) {
				int index = i + 1;
				System.out.println(index + ". " + itemBuffer.get(i));
			}
		}
	}
	
	// Append item to item buffer
	private static void addToBuffer(String item) {
		itemBuffer.add(item);
	}
	
	/**
	 * Removes item from buffer
	 * @param index
	 */	
	private static void deleteFromBuffer(int index) {
		itemBuffer.remove(index);		
	}
	
	private static void clear() {
		int length = itemBuffer.size();
		for (int i = 0; i < length; i++) {
			deleteFromBuffer(0);
		}
	}
	
	// Write everything from itemBuffer to file
	private static void writeToFile(String path) {
		try {
			output = getWriter(path);
			for (String line : itemBuffer) {
				output.write(line + "\n");
			}
			output.close();
		} catch (IOException e) {
			System.out.println(e);
		}
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
	
	private static void addSuccess(String fileName, String text) {
		System.out.println(MESSAGE_ADD + fileName + ": \"" + text + "\"");
	}
	
	private static void deleteSuccess(String fileName, String text) {
		System.out.println(MESSAGE_DELETE + fileName + ": \"" + text + "\"");
	}
	
	private static void clearSuccess(String fileName) {
		System.out.println(MESSAGE_CLEAR + fileName);
	}
	
}
