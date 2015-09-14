import static org.junit.Assert.*;

import org.junit.Test;


public class GetParam {

    @Test
    public void hasParam() {
        String param = TextBuddy.getParam("cmd param");
        assertEquals("param", param);
    }

    @Test
    public void noParam() {
        String param = TextBuddy.getParam("cmdonly");
        assertEquals("", param);
    }
    
    @Test
    public void multiWordParam() {
    	String param = TextBuddy.getParam("cmd long param bah bah");
    	assertEquals("long param bah bah", param);
    }
    
    @Test
    public void multiSpace() {
    	String param = TextBuddy.getParam("cmd   long param bah bah");
    	assertEquals("  long param bah bah", param);
    }
}
