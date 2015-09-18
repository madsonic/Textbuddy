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
 * sort the items permanently
 */
public class TextBuddy {
    private static final String STATEMENT_ENUM = "%d. %s";
    
    private final String COMMAND_ADD     = "add";
    private final String COMMAND_DELETE  = "delete";
    private final String COMMAND_DISPLAY = "display";
    private final String COMMAND_CLEAR   = "clear";
    private final String COMMAND_SORT    = "sort";
    private final String COMMAND_SEARCH  = "search";    
    
    private enum COMMANDS {
    	ADD, DELETE, DISPLAY, CLEAR, SORT, SEARCH, INVALID
    }
    
    private Scanner scanner;
    private BufferedWriter output;
    private Storage fileStorage;
    private ArrayList<String> itemBuffer;
    private MessageCentre msgCentre;
    
    public TextBuddy(String fileName) {
    	fileStorage = new Storage(fileName);
    	itemBuffer = new ArrayList<String>();
    	scanner = new Scanner(System.in);
    	
    	readToBuffer(fileStorage.getFile());
    	
    	msgCentre = new MessageCentre(fileName);
    	msgCentre.welcome();
    }
    
    
    private void readToBuffer(File file) {
    	String line;
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null) {
				itemBuffer.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
            	return COMMANDS.INVALID;
        } 
    }
    
    private String getInput() {
    	return this.scanner.nextLine();
    }
    
    private boolean hasNextLine() {
    	return this.scanner.hasNextLine();
    }
    
    private void executeCommand(String[] action) {
		COMMANDS cmd = matchCmd(action[0]);
		String param = action[1];
		
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
	        default:
	            // TODO: display error message
		}
	}

	public static void main(String[] args) {
    	TextBuddy tb = new TextBuddy(args[0]);

        do {
        	tb.msgCentre.ask();
            Parser p = new Parser(tb.getInput());
            tb.executeCommand(p.getAction());
        } while(tb.hasNextLine());
    }

}
