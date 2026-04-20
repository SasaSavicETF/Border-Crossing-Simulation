package org.unibl.etf.pj2.terminali;

import java.util.Random; 

public class Teret {
	private boolean potrebnaDokumentacija; 
	
	public Teret() {
		Random rand = new Random(); 
		potrebnaDokumentacija = rand.nextInt(100) <= 50; 
	}
	
	public boolean getPotrebnaDokumentacija() {
		return potrebnaDokumentacija; 
	}
}
