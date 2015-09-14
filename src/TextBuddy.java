import java.awt.print.Printable;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
 * Able to perform add, delete, clear, display
 * 
 * All the above command keywords must be the first word of the input
 * Everything thereafter is considered arguments
 * 
 * Example use
 * add string
 * add sometext more text
 * Adds 'sometext more text' to the list
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
 * sort
 * sort the items permanently
 */
public class TextBuddy {
    private static final String MESSAGE_ASK_INPUT = "Command: ";
    private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use";   
    private static final String MESSAGE_ADD = "added to %s: \"%s\"";  
    private static final String MESSAGE_CLEAR = "all content deleted from %s";
    private static final String MESSAGE_DELETE = "deleted from %s: \"%s\"";
    private static final String MESSAGE_EMPTY_LIST = "List is empty";
    private static final String MESSAGE_EMPTY_FIND = "No result";
    
    private static final String STATEMENT_ENUM = "%d. %s";
    
    
    private static final String[] COMMANDS = {"add", "delete", "display", "clear", "sort"};
    
    private static BufferedWriter output;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<String> itemBuffer = new ArrayList<String>();
    private static String fileName;
    
    public static void main(String[] args) {
        fileName = args[0];
        showMsg(String.format(MESSAGE_WELCOME, fileName));

        do {
            showMsg(MESSAGE_ASK_INPUT);
            String input = scanner.nextLine();
            executeCommand(getCommand(input), getParam(input));
        } while(scanner.hasNextLine());
    }

    // Perform action based on command given
    public static void executeCommand(String cmd, String param) {
        if (cmd.equalsIgnoreCase(COMMANDS[0])) {        // add
            commandAdd(param);
        } else if (cmd.equalsIgnoreCase(COMMANDS[1])) { // delete
            commandDelete(param);
        } else if (cmd.equalsIgnoreCase(COMMANDS[2])) { // display
            commandDisplay();
        } else if (cmd.equalsIgnoreCase(COMMANDS[3])) { // clear
            commandClear();
        } else if (cmd.equalsIgnoreCase(COMMANDS[4])) { // sort
        	commandSort();
        }
        writeToFile();
    }
    
    /* =======================
     *    Command methods
     * =======================
     */
	
	public static void commandAdd(String param) {
		addToBuffer(param);
		addSuccess(param);
	}
	
	public static void commandDelete(String param) {
		int index = Integer.parseInt(param) - 1;
		String str = itemBuffer.get(index);
		deleteFromBuffer(index);
		deleteSuccess(str);
	}
	
	public static void commandClear() {
		clear();
		clearSuccess();
	}
    
    // List all items in the buffer
    public static void commandDisplay() {
        int len = itemBuffer.size();
        
        if (len == 0) {
            showMsg(MESSAGE_EMPTY_LIST);
        } else {
            for (int i = 0; i < len; i++) {
                int index = i + 1;
                showMsg(String.format(STATEMENT_ENUM, index, itemBuffer.get(i)));
            }
        }
    }
    
    /* =======================
	 *    Command methods
	 * =======================
	 */
	
	public static void commandSort() {
		Collections.sort(itemBuffer);
//		commandDisplay();
	}

	public static void commandSearch(String string) {
		boolean noResult = true;
//		ArrayList<String> searchHits;
		
		for (int i = 0; i < itemBuffer.size(); i++) {
			String item = itemBuffer.get(i);
			
			if (item.contains(string)) {
				showMsg(String.format(STATEMENT_ENUM, i + 1, item)); 
				noResult = false;
			}
		}
		if (noResult) {
			showMsg(MESSAGE_EMPTY_FIND);;
		}
	}

	// Append item to item buffer
    public static void addToBuffer(String item) {
        itemBuffer.add(item);
    }
    
    /**
     * Removes item from buffer
     * @param index
     */ 
    public static void deleteFromBuffer(int index) {
        itemBuffer.remove(index);       
    }
    
    public static void clear() {
        int length = itemBuffer.size();
        for (int i = 0; i < length; i++) {
            deleteFromBuffer(0);
        }
    }
    
    /* ===================
     *   helper methods
     * ===================
     */
    
    // Write everything from itemBuffer to file
    public static void writeToFile() {
        try {
            output = getWriter();
            for (String line : itemBuffer) {
                output.write(line + "\n");
            }
            output.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static BufferedWriter getWriter() throws IOException {
        File file = new File(fileName);
        return new BufferedWriter(new FileWriter(file));
    }
    
    /* ======================
     *    Command Parsing   
     * ======================
     */
    public static String getCommand(String input) {
        return getFirstWord(input);
    }
    
    // Read first word as command keyword
    public static String getFirstWord(String input) {
        // split at white space
        String delimiter = "\\s";
        return input.trim().split(delimiter)[0];
    }
    
    // Everything after command keyword is argument
    public static String getParam(String input) {
        String[] arr = input.split(" ", 2);
        if (arr.length == 1) {
        	return "";
        } else {
        	return arr[1];        	
        }
    }
    
    /* ======================
     *   Success Messages  
     * ======================
     */
    public static void showMsg(String message) {
    	System.out.println(message);
    }
    
    public static void addSuccess(String text) {
        showMsg(String.format(MESSAGE_ADD, fileName, text));
    }
    
    public static void deleteSuccess(String text) {
        showMsg(String.format(MESSAGE_DELETE, fileName, text));
    }
    
    public static void clearSuccess() {
        showMsg(String.format(MESSAGE_CLEAR, fileName));
    }
    
    /* =====================================
     *  Methods to expose data for testing
     * =====================================
     */
    
    public static ArrayList<String> getBuffer() {
    	return new ArrayList<String>(itemBuffer);
    }
}
