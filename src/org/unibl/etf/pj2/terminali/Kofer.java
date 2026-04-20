package org.unibl.etf.pj2.terminali;

import java.io.Serializable; 

public class Kofer implements Serializable {
	private boolean sadrziNedozvoljeneStvari = false; 
	
	public boolean getValidnost() {
		return sadrziNedozvoljeneStvari; 
	}
	
	public void setValidnost(boolean sadrziNedozvoljeneStvari) {
		this.sadrziNedozvoljeneStvari = sadrziNedozvoljeneStvari; 
	}
}
