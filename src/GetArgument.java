import static org.junit.Assert.*;

import org.junit.Test;


public class GetArgument {

    @Test
    public void hasParam() {
        String param = TextBuddy.getArgument("cmd param");
        assertEquals("param", param);
    }

    @Test
    public void noParam() {
        String param = TextBuddy.getArgument("cmdonly");
        assertEquals("", param);
    }
    
    @Test
    public void multiWordParam() {
    	String param = TextBuddy.getArgument("cmd long param bah bah");
    	assertEquals("long param bah bah", param);
    }
    
    @Test
    public void multiSpace() {
    	String param = TextBuddy.getArgument("cmd   long param bah bah");
    	assertEquals("  long param bah bah", param);
    }
}
