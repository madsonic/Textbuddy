import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CommandSearch {
    String[] arr = {"some words", "characters !@#$", "some123numbers"};

    @Before
    public void setUp() {
        for (String item: arr) {
        	TextBuddy.addToBuffer(item);			
		}
    }
    
    @After
    public void tearDown() {
    	TextBuddy.clear();
    }

    @Test
    public void exactSearchOne() {
        String expect = "1. some words";
        assertEquals(expect, TextBuddy.commandSearch("some words"));
    }
    
    @Test
    public void exactSearchTwo() {
    	String expect = "2. characters !@#$";
    	assertEquals(expect, TextBuddy.commandSearch("characters !@#$"));
    }

    @Test
    public void nonExactSearch() {
    	String expect = "2. characters !@#$";
    	assertEquals(expect, TextBuddy.commandSearch("!@"));
    }

}