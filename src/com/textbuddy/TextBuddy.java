package com.textbuddy;
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
    private Storage fileStorage;
    
    public TextBuddy(String fileName) {
    	this.fileStorage = new Storage(fileName);
    }
    
    public static void main(String[] args) {
    	
    }

}
