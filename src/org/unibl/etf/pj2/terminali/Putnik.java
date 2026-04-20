package org.unibl.etf.pj2.terminali;

import java.util.Random;

import org.unibl.etf.pj2.interfejsi.SadrziKofereInterface;
import org.unibl.etf.pj2.interfejsi.SadrziTeretInterface;
import org.unibl.etf.pj2.simulacija.Vozilo;

import java.io.Serializable;  

public class Putnik implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String ime; 
	private boolean jeVozac = false; 
	private transient Kofer kofer;
	private boolean dokumentValidan = true; 
	private String vozilo;  
	
	// Metode: 
	public Putnik(Vozilo vozilo) { 
		if(vozilo instanceof SadrziKofereInterface) {
			this.vozilo = "Autobus"; 
		} else if(vozilo instanceof SadrziTeretInterface) {
			this.vozilo = "Kamion"; 
		} else {
			this.vozilo = "LicnoVozilo"; 
		}
		Random rand = new Random(); 
		if(rand.nextInt(100) <= 70) {
			kofer = new Kofer(); 
		} else {
			kofer = null; 
		}
		
		StringBuilder stringBuilder = new StringBuilder(); 
		stringBuilder.append("putnik"); 
		for(int i = 0; i < 4; i++) {
			stringBuilder.append(rand.nextInt(10)); 
		}
		ime = stringBuilder.toString(); 
	}
	
	public String getVozilo() {
		return vozilo; 
	}
	
	public boolean imaKofer() {
		if(kofer != null) {
			return true; 
		} else {
			return false; 
		}
	}
	
	public boolean getValidnostDokumenta() {
		return dokumentValidan; 
	}
	
	public void setValidnostDokumenta(boolean dokumentValidan) {
		this.dokumentValidan = dokumentValidan; 
	}
	
	public Kofer getKofer() {
		return kofer; 
	}
	
	public void postaviValidnostKofera(boolean validnost) {
		kofer.setValidnost(validnost); 
	}
	
	public void setJeVozac(boolean jeVozac) {
		this.jeVozac = jeVozac; 
	}
	
	public boolean getJeVozac() {
		return jeVozac; 
	}
	
	public String getIme() {
		return ime; 
	}
	
	@Override
	public String toString() {
		return ime + ", Vozac: " + jeVozac + ", Ima kofer: " + imaKofer() + ", Dokument validan: " + dokumentValidan; 
	}
}
