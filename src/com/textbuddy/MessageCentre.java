package com.textbuddy;

public class MessageCentre {
	private final String MESSAGE_ASK_INPUT   = "Command: ";
    private final String MESSAGE_WELCOME     = "Welcome to TextBuddy. %s is ready for use";   
    private final String MESSAGE_ADD         = "added to %s: \"%s\"";  
    private final String MESSAGE_CLEAR       = "all content deleted from %s";
    private final String MESSAGE_DELETE      = "deleted from %s: \"%s\"";
    private final String MESSAGE_EMPTY_LIST  = "List is empty";
    private final String MESSAGE_EMPTY_FIND  = "No result";
    private final String MESSAGE_UNKNOWN_CMD = "Unknown command";
    private final String STATEMENT_ENUM      = "%s. %s";
    
    private String fileName;
    
    public MessageCentre(String fileName) {
    	this.fileName = fileName;
    }
    
    public void list(int index, String s) {
    	String i = Integer.toString(index);
    	String[] arr = {i, s};
    	show(STATEMENT_ENUM, arr);
    }
    
    public void ask() {
    	show(MESSAGE_ASK_INPUT); 
    }
    
    public void welcome() {
    	show(MESSAGE_WELCOME, fileName); 
    }
    
    public void add(String s) {
    	String[] arr = {fileName, s};
    	show(MESSAGE_ADD, arr);
    }
    
    public void clear() {
    	show(MESSAGE_CLEAR, fileName);
    }
    
    public void delete(String s) {
    	String[] arr = {fileName, s};
    	show(MESSAGE_DELETE, arr);
    }
    
    public void emptyList() {
    	show(MESSAGE_EMPTY_LIST);
    }
    
    public void emptyFind() {
    	show(MESSAGE_EMPTY_FIND);
    }
    
    public void unknownCmd() {
    	show(MESSAGE_UNKNOWN_CMD);
    }
    
    private void show(String message) {
		show(message, new String[]{});
	}
    
    private void show(String message, String param) {
    	show(message, new String[]{param});
    }
    
    private void show(String message, String[] param) {
    	if (param == null || param.length == 0) {
    		System.out.println(message);
    	} else {
    		System.out.println(String.format(message, (Object[])param));
    	}
    }
}
