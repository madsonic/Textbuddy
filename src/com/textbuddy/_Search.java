package com.textbuddy;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class _Search {
	private Logic tb;
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUp() {
		tb = new Logic("test.txt");
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUp() {
		tb.executeCommand(new Parser("exit").getAction());
		File file = new File("test.txt");
		file.delete();
		System.setOut(null);
	}
	
	@Test
	public void search_NoMatch_ShouldReturnMessage() {
		tb.executeCommand(new Parser("search blah").getAction());
		assertEquals("No result\n", outContent.toString());
	}

	@Test
	public void search_ExactMatchFromOneItem_ShouldReturnMatch() {
		String input = "add something";
		tb.executeCommand(new Parser(input).getAction());
		
		outContent.reset();
		tb.executeCommand(new Parser("search something").getAction());
		assertEquals("1. something\n", outContent.toString());
	}
	
	@Test
	public void search_PartialMatchFromOneItem_ShouldReturnMatch() {
		String input = "add something";
		tb.executeCommand(new Parser(input).getAction());
		
		outContent.reset();
		tb.executeCommand(new Parser("search some").getAction());
		assertEquals("1. something\n", outContent.toString());
	}
	
	@Test
	public void search_PartialMatchesFromMultipleItems_ShouldReturnMultipleMatches() {
		String[] inputs = {"add something", "add someone", "add nothing"};
		for (String input : inputs) {
			tb.executeCommand(new Parser(input).getAction());
		}
		
		outContent.reset();
		tb.executeCommand(new Parser("search some").getAction());
		assertEquals("1. something\n2. someone\n", outContent.toString());
	}
}
