import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;


public class GetFirstWord {

    @Test
    public void single() {
        String word = TextBuddy.getFirstWord("foo");
        
        assertEquals("foo", word);
    }
    
    @Test
    public void space() {
        String word = TextBuddy.getFirstWord("hello i am jim");
        
        assertEquals("hello", word);
    }

    @Test
    public void empty() {
        String word = TextBuddy.getFirstWord("");
        
        assertEquals("", word);
    }

    @Test
    public void leadingSpace() {
        String word = TextBuddy.getFirstWord("   foo");
        
        assertEquals("foo", word);
    }
    
    @Test
    public void leadingSpaceProper() {
        String word = TextBuddy.getFirstWord("   foo some words");
        
        assertEquals("foo", word);
    }
    
    @Test
    public void multiSpace() {
        String word = TextBuddy.getFirstWord("foo     words");
        
        assertEquals("foo", word);
    }
    
    @Test(expected=Error.class)
    public void wrongType() {
        String word = TextBuddy.getFirstWord(1);    
        fail();
    }
}
