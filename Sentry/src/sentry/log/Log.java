package sentry.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Log {

	public static final int LOG_DEBUG = 0;
	public static final int LOG_USER = 1;
	public static final int LOG_ERROR = 2;
	
	/*
	 * The file to log to.
	 */
	private File _file;
	
	/*
	 * The log entries.
	 */
	private ArrayList<String> _entries; 
	
	public Log(String filename){
		_file = new File(filename);
		_entries = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param text The text to log.
	 * @param level The loglevel of the text.
	 */
	public void log(String text, int level){
		_entries.add(text);
		switch(level){
		case LOG_DEBUG:
			System.out.println("[DEBUG] " + text); break;
		case LOG_ERROR:
			System.out.println("[ERROR] " + text); break;
		case LOG_USER:
		default:
			System.out.println("[LOG] " + text); break;
		}
	}
	
	/**
	 * Synchronizes the stored entries by writing them to the given file.
	 * @throws FileNotFoundException If the file does not exist.
	 */
	public void sync() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(_file));
		
		for(String line: _entries){
			bw.write(line);
		}
		bw.close();
	}
}
