import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CommandSearch {
    private final String[] arr = {"some words", "characters !@#$", "some123numbers"};
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    @Before
    public void setUp() {
        for (String item: arr) {
        	TextBuddy.addToBuffer(item);			
		}
        System.setOut(new PrintStream(outContent));
    }
    
    @After
    public void tearDown() {
    	TextBuddy.clear();
    	System.setOut(null);
    }

    @Test
    public void exactSearchOne() {
        String expected = "1. some words\n";
        TextBuddy.commandSearch("some words");
        assertEquals(expected, outContent.toString());
    }
    
    @Test
    public void exactSearchTwo() {
    	String expected = "2. characters !@#$\n";
    	TextBuddy.commandSearch("characters !@#$");
    	assertEquals(expected, outContent.toString());
    }

    @Test
    public void nonExactSearch() {
    	String expected = "2. characters !@#$\n";
    	TextBuddy.commandSearch("!@");
    	assertEquals(expected, outContent.toString());
    }
    
    @Test
    public void multiMatch() {
    	String expected = "1. some words\n3. some123numbers\n";
    	TextBuddy.commandSearch("some");
    	assertEquals(expected, outContent.toString());
    }
    
    @Test
    public void noMatch() {
    	String expected = "No result\n";
    	TextBuddy.commandSearch("foo");
    	assertEquals(expected, outContent.toString());
    }

}