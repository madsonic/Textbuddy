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
    private static final String MESSAGE_ASK_INPUT   = "Command: ";
    private static final String MESSAGE_WELCOME     = "Welcome to TextBuddy. %s is ready for use";   
    private static final String MESSAGE_ADD         = "added to %s: \"%s\"";  
    private static final String MESSAGE_CLEAR       = "all content deleted from %s";
    private static final String MESSAGE_DELETE      = "deleted from %s: \"%s\"";
    private static final String MESSAGE_EMPTY_LIST  = "List is empty";
    private static final String MESSAGE_EMPTY_FIND  = "No result";
    private static final String MESSAGE_UNKNOWN_CMD = "Unknown command";
    
    private static final String STATEMENT_ENUM = "%d. %s";
    
    private static final String COMMAND_ADD     = "add";
    private static final String COMMAND_DELETE  = "delete";
    private static final String COMMAND_DISPLAY = "display";
    private static final String COMMAND_CLEAR   = "clear";
    private static final String COMMAND_SORT    = "sort";
    private static final String COMMAND_SEARCH  = "search";    
    
    enum COMMANDS {
    	ADD, DELETE, DISPLAY, CLEAR, SORT, SEARCH, INVALID
    }
    
    private static BufferedWriter output;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<String> itemBuffer = new ArrayList<String>();
    private static String fileName = "./output.txt";
    
    public static void main(String[] args) {
        fileName = args[0];
        showMsg(String.format(MESSAGE_WELCOME, fileName));

        do {
            showMsg(MESSAGE_ASK_INPUT);
            String input = scanner.nextLine();
            executeCommand(input);
        } while(scanner.hasNextLine());
    }

    // Perform action based on command given
    public static void executeCommand(String userInput) {
    	COMMANDS cmd = getCommand(userInput);
    	String param = getArgument(userInput);
    	
    	switch(cmd) {
    		case ADD:
    			commandAdd(param);
    			return;
    		case DELETE:
    			commandDelete(param);
    			return;
    		case DISPLAY:
    			commandDisplay();
    			return;
    		case CLEAR:
    			commandClear();
    			return;
    		case SORT:
    			commandSort();
    			return;
    		case SEARCH:
    			commandSearch(param);
    			return;
    		case INVALID:
				showMsg(MESSAGE_UNKNOWN_CMD);
				return;
			default:
				// should not reach here as INVALID should be able to handle all other cases
				throw new Error("Command execution error");
    	}
    }
    
    /* ======================
     *    Command Parsing   
     * ======================
     */
    public static COMMANDS getCommand(String input) {
        String w = getFirstWord(input);
        
        if (w.equalsIgnoreCase(COMMAND_ADD)) {
        	return COMMANDS.ADD;
        } else if (w.equalsIgnoreCase(COMMAND_CLEAR)) {
        	return COMMANDS.CLEAR;
        } else if (w.equalsIgnoreCase(COMMAND_DELETE)) {
        	return COMMANDS.DELETE;
	    } else if (w.equalsIgnoreCase(COMMAND_DISPLAY)) {
	    	return COMMANDS.DISPLAY;
	    } else if (w.equalsIgnoreCase(COMMAND_SORT)) {
	    	return COMMANDS.SORT;
	    } else if (w.equalsIgnoreCase(COMMAND_SEARCH)) {
	    	return COMMANDS.SEARCH;
	    } else {
	    	return COMMANDS.INVALID;
	    }
    }
    
    // Everything after command keyword is argument
    public static String getArgument(String input) {
    	String[] arr = input.split(" ", 2);
    	if (arr.length == 1) {
    		return "";
    	} else {
    		return arr[1];        	
    	}
    }
    
    // Read first word as command keyword
    public static String getFirstWord(String input) {
        // split at white space
        String delimiter = "\\s";
        return input.trim().split(delimiter)[0];
    }
    
    /* =======================
     *    Command methods
     * =======================
     */
	
	public static void commandAdd(String param) {
		addToBuffer(param);
		addSuccess(param);
		writeToFile();
	}
	
	public static void commandDelete(String param) {
		int index = Integer.parseInt(param) - 1;
		String str = itemBuffer.get(index);
		deleteFromBuffer(index);
		deleteSuccess(str);
		writeToFile();
	}
	
	public static void commandClear() {
		clear();
		clearSuccess();
		writeToFile();
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
    
	public static void commandSort() {
		Collections.sort(itemBuffer);
		commandDisplay();
		writeToFile();
	}

	public static void commandSearch(String string) {
		boolean noResult = true;
		
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
