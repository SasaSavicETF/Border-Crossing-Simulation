package org.unibl.etf.pj2.simulacija;

import java.io.File; 
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	public Logger logger; 
	public FileHandler fileHandler; 
	
	public MyLogger(String nazivFajla) {
		File file = new File(nazivFajla); 
		try {
			fileHandler = new FileHandler(nazivFajla, true); 
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		logger = Logger.getLogger(nazivFajla); 
		logger.addHandler(fileHandler);
		SimpleFormatter simpleFormatter = new SimpleFormatter(); 
		fileHandler.setFormatter(simpleFormatter); 
		logger.setLevel(Level.WARNING);
	}
}
