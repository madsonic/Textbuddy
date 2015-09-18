package com.textbuddy;

import java.io.File;
import java.io.IOException;

public class Storage {
	private String m_fileLocation;
	private File m_file;
	
	public Storage(String fileLoc) {
		this.m_fileLocation = fileLoc;
		this.m_file = new File(fileLoc);
		try {
			m_file.createNewFile();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
