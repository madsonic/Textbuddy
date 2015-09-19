package com.textbuddy;

import static org.junit.Assert.*;

import org.junit.Test;

public class _Parser {

	@Test
	public void giveAction() {
		Parser p = new Parser("keyword then parameter");
		assertEquals("keyword", p.getAction()[0]);
		assertEquals("then parameter", p.getAction()[1]);
	}
	
	@Test
	public void shouldIncludeWhiteSpace() {
		Parser p = new Parser("keyword      parameter");
		assertEquals("keyword", p.getAction()[0]);
		assertEquals("     parameter", p.getAction()[1]);
	}
}
