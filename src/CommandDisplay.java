import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;


public class CommandDisplay {
	
	@After
	public void tearDown() {
		TextBuddy.clear();
	}

	@Test
	public void naive() {
		String[] items = {"item1", "item2", "item3"};
		String[] wrong = {"item3", "item1", "item2"};
		for (String item : items) {
			TextBuddy.addToBuffer(item);
		}
		ArrayList<String> buff = TextBuddy.getBuffer();
		for (int i = 0; i < 3; i++) {
			if (!items[i].equals(buff.get(i))) {
				fail();
			}
			if (wrong[i].equals(buff.get(i))) {
				fail();
			}
		}
	}

}
