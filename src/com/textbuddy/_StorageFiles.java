package com.textbuddy;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.*;


public class _StorageFiles {
	private File file;
	
	@Before
	public void makeFiles() {
		file = new File("testFile.txt");
		try {
			file.createNewFile();
			BufferedWriter bf = new BufferedWriter(new FileWriter(file));
			bf.write("some random text that should remain");
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void removeFiles() {
		file.delete();
	}
	
	@Test
	public void modifyFile() {
		Storage myFile = new Storage("testFile.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader("testFile.txt"));
			String text = br.readLine();
			assertEquals("some random text that should remain", text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
