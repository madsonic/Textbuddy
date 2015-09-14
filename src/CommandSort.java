import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CommandSort {
	private final String[] sorted = {"item0", "item1", "item2"};

	@After
	public void tearDown() {
		TextBuddy.clear();
	}

	@Test
	public void sorted() {
		String[] input = sorted;
		for (String item : input) {
			TextBuddy.executeCommand("add " + item);
		}
		TextBuddy.commandSort();
		ArrayList<String> buff = TextBuddy.getBuffer();
		for (int i = 0; i < sorted.length; i++) {
			if (!buff.get(i).equals(sorted[i])) {
				fail();
			}
		}
	}

	@Test
	public void revSorted() {
		String[] input = {"item2", "item1", "item0"};
		for (String item : input) {
			TextBuddy.executeCommand("add " + item);
		}
		TextBuddy.commandSort();
		ArrayList<String> buff = TextBuddy.getBuffer();
		for (int i = 0; i < sorted.length; i++) {
			if (!buff.get(i).equals(sorted[i])) {
				fail();
			}
		}
	}
	
	

}
