package com.textbuddy;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class _Sort {
	Logic tb;
	
	@Before
	public void setUp() {
		tb = new Logic("test.txt");
	}
	
	@After
	public void cleanUp() {
		tb.executeCommand(new Parser("exit").getAction());
		File file = new File("test.txt");
		file.delete();
	}

	@Test
	public void sort_UnsortedItems_ShouldReturnSorted() {
		String[] inputs = {"add c", "add a", "add b"};
		String[] sorted = {"a", "b", "c"};
		
		for (String input: inputs) {
			tb.executeCommand(new Parser(input).getAction());			
		}
		tb.executeCommand(new Parser("sort").getAction());
		Object[] items = tb.getBuffer().toArray();
		assertArrayEquals(sorted, items);
	}
	
	@Test
	public void sort_IgnoreCase_ShouldReturnUnchanged() {
		String[] inputs = {"add a", "add A"};
		String[] sorted = {"a", "A"};
		
		for (String input: inputs) {
			tb.executeCommand(new Parser(input).getAction());			
		}
		tb.executeCommand(new Parser("sort").getAction());
		Object[] items = tb.getBuffer().toArray();
		assertArrayEquals(sorted, items);
	}
	
	@Test
	public void sort_NumIsSmaller_ShouldListNumFirst() {
		String[] inputs = {"add A", "add 123"};
		String[] sorted = {"123", "A"};
		
		for (String input: inputs) {
			tb.executeCommand(new Parser(input).getAction());			
		}
		tb.executeCommand(new Parser("sort").getAction());
		Object[] items = tb.getBuffer().toArray();
		assertArrayEquals(sorted, items);
	}

}
