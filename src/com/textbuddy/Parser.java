package com.textbuddy;

/**
 * Parse user input into command and arguments
 * 
 * @return String[]
 * 
 * @author Gerald
 *
 */
public class Parser {
    private String cmd;
    private String arg;
    
    public Parser(String userInput) {
        String[] tokens = userInput.split("\\s", 2);
        if (tokens.length < 2) {
        	this.cmd = tokens[0];
        	this.arg = "";
        } else {
        	this.cmd = tokens[0];
        	this.arg = tokens[1]; 
        }
    }
    
    public String[] getAction() {
    	return new String[]{this.cmd, this.arg};
    }
}
