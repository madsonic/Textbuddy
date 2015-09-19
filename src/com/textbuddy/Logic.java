package com.textbuddy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
 * sort the items non permanent
 * 
 * search
 * looks for match in task description
 */
public class Logic {
    private final String COMMAND_ADD     = "add";
    private final String COMMAND_DELETE  = "delete";
    private final String COMMAND_DISPLAY = "display";
    private final String COMMAND_CLEAR   = "clear";
    private final String COMMAND_SORT    = "sort";
    private final String COMMAND_SEARCH  = "search";  
    private final String COMMAND_EXIT    = "exit";  
    
    private enum COMMANDS {
    	ADD, DELETE, DISPLAY, CLEAR, SORT, SEARCH, EXIT, INVALID
    }
    
    private Scanner scanner;
    private BufferedWriter output;
    private Storage fileStorage;
    private ArrayList<String> itemBuffer;
    
    public MessageCentre msgCentre;
    
    public Logic(String fileName) {
    	fileStorage = new Storage(fileName);
    	itemBuffer = new ArrayList<String>();
    	scanner = new Scanner(System.in);
    	msgCentre = new MessageCentre(fileName);
    	
    	try {
    		output = new BufferedWriter(new FileWriter(fileName, false));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void executeCommand(String[] action) {
    	COMMANDS cmd = matchCmd(action[0]);
    	String param = action[1];

    	switch(cmd) {
    	case ADD:
    		commandAdd(param);
    		break;
    	case DELETE:
    		commandDelete(param);
    		break;
    	case DISPLAY:
    		commandDisplay();
    		break;
    	case CLEAR:
    		commandClear();
    		break;
    	case SORT:
    		commandSort();
    		break;
    	case SEARCH:
    		commandSearch(param);
    		break;
    	case EXIT:
    		commandExit();
    		break;
    	default:
    		msgCentre.unknownCmd();
    	}
    }

    private COMMANDS matchCmd(String cmd) {
	    switch(cmd.toLowerCase()) {
	        case COMMAND_ADD:
	            return COMMANDS.ADD;
	        case COMMAND_DELETE:
	            return COMMANDS.DELETE;
	        case COMMAND_DISPLAY:
	            return COMMANDS.DISPLAY;
	        case COMMAND_CLEAR:
	            return COMMANDS.CLEAR;
	        case COMMAND_SORT:
	            return COMMANDS.SORT;
	        case COMMAND_SEARCH:
	            return COMMANDS.SEARCH;
	        default:
	        	System.out.println(cmd);
	        	return COMMANDS.INVALID;
	    } 
	}

	private void commandSearch(String searchTerm) {
    	boolean noResult = true;

    	for (int i = 0; i < itemBuffer.size(); i++) {
    		String item = itemBuffer.get(i);

    		if (item.contains(searchTerm)) {
    			msgCentre.list(i, item);
    			noResult = false;
    		}
    	}
    	if (noResult) {
    		msgCentre.emptyFind();
    	}
	}

	private void commandSort() {
		Collections.sort(itemBuffer, String.CASE_INSENSITIVE_ORDER);
	}

	private void commandDisplay() {
		if (itemBuffer.size() == 0) {
			msgCentre.emptyList();
			return;
		} else {
			for (int i = 0; i < itemBuffer.size(); i++) {
				String item = itemBuffer.get(i);
				msgCentre.list(i, item);
			}
		}
	}

	private void commandClear() {
		int size = itemBuffer.size();
		for (int i = 0; i < size; i++) {
			deleteFromBuffer(0);
		}
		msgCentre.clear();
	}

	private void commandDelete(String index) {
		String item = itemBuffer.get(Integer.parseInt(index) - 1);
		deleteFromBuffer(index);
		writeToFile();
		msgCentre.delete(item);
	}

	private void commandAdd(String param) {
		addToBuffer(param);
		writeToFile();
		msgCentre.add(param);
	}

	// exit cleanly
	private void commandExit() {
		scanner.close();
		fileStorage = null;
		itemBuffer = null;
		msgCentre = null;
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void addToBuffer(String item) {
		itemBuffer.add(item);
	}

	private void deleteFromBuffer(String index) {
		deleteFromBuffer(Integer.parseInt(index) - 1);
	}
	
	private void deleteFromBuffer(int index) {
		itemBuffer.remove(index);
	}

	// flush from buffer into file
	private void writeToFile2() {
		try {
			for (String line : itemBuffer) {
				output.write(line + "\n");
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Write everything from itemBuffer to file
	private void writeToFile() {
		try {
			output = getWriter(fileStorage.getFile());
			for (String line : itemBuffer) {
				output.write(line + "\n");
			}
			output.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	private BufferedWriter getWriter(File fileName) throws IOException {
		return new BufferedWriter(new FileWriter(fileName));
	}

	public boolean hasNextLine() {
		return this.scanner.hasNextLine();
	}

	public String getInput() {
		return this.scanner.nextLine();
	}
	
	// getter to use for unit test case
	public ArrayList<String> getBuffer() {
		return new ArrayList<String>(itemBuffer);
	}
}
