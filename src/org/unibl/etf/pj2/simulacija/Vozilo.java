package org.unibl.etf.pj2.simulacija;
import org.unibl.etf.pj2.terminali.*;

import java.util.Random;
import java.io.IOException;
import java.time.LocalTime; 
import java.util.List; 
import java.util.ArrayList; 


public class Vozilo extends Thread {
	private int maxBrojPutnika; 
	protected int brojPutnika; 
	protected List<Putnik> putnici = new ArrayList<Putnik>();
	
	public static Object lock = new Object(); 
	public static Object lock2 = new Object(); 

	// Konstruktor
	public Vozilo(int maxBrojPutnika) {
		this.maxBrojPutnika = maxBrojPutnika; 
		Random rand = new Random(); 
		brojPutnika = rand.nextInt(maxBrojPutnika) + 1; 
		
		// Ucitavanje putnika, prvo vozaca pa ostalih putnika (ako ih ima) 
		Putnik vozac = new Putnik(this); 
		vozac.setJeVozac(true);
		putnici.add(vozac); 
		
		for(int i = 1; i < brojPutnika; i++) {
			putnici.add(new Putnik(this));  
		}
	}
	
	public int getBrojPutnika() {
		return putnici.size(); 
	} 	
	
	public List<Putnik> getPutnici() {
		return putnici; 
	}
	
	public void setPutnici(List<Putnik> putnici) {
		this.putnici = putnici; 
	}
	
	public Putnik getPutnik(int indeks) {
		return putnici.get(indeks); 
	}
	
	public void setPutnik(int indeks, boolean validnostDokumenta) {
		putnici.get(indeks).setValidnostDokumenta(validnostDokumenta);
	}
	
	public void ukloniPutnika(Putnik putnik) {
		putnici.remove(putnik); 
	}

	// Za licna vozila i autobuse je isto kretanje, pa se moze implementirati i u klasi Vozilo
	@Override
	public void run() {
		synchronized (GranicniPrelaz.redVozila) {
			while(!GranicniPrelaz.redVozila.peek().equals(this)) {
				try {
					GranicniPrelaz.redVozila.wait(); 
				} catch(InterruptedException ie) {
					GranicniPrelaz.gpLogger.logger.warning(ie.getMessage()); 
				}
			} 
		} 
		
		synchronized (lock) {
			while((GranicniPrelaz.policijskiTerminal1.jeZauzet && GranicniPrelaz.policijskiTerminal2.jeZauzet)
				|| (GranicniPrelaz.policijskiTerminal1.daLiJeBlokiran() && GranicniPrelaz.policijskiTerminal2.daLiJeBlokiran())) {
				try {
					lock.wait(); 
				} catch(InterruptedException ie) {
					GranicniPrelaz.gpLogger.logger.warning(ie.getMessage());  
				}
			}
		}
		
		// Definisanje trenutnog terminala 
		Terminal glavniTerminal = null;
		Terminal sporedniTerminal = null; 
		int id = 0; 
		if(!GranicniPrelaz.policijskiTerminal1.jeZauzet) {
			glavniTerminal = GranicniPrelaz.policijskiTerminal1; 
			id = 1; 
			if(GranicniPrelaz.policijskiTerminal1.daLiJeBlokiran()) {
				sporedniTerminal = glavniTerminal; 
				glavniTerminal = GranicniPrelaz.policijskiTerminal2;
				id = 2; 
			}
		} else {
			if(!GranicniPrelaz.policijskiTerminal2.jeZauzet) {
				glavniTerminal = GranicniPrelaz.policijskiTerminal2; 
				id = 2; 
				if(GranicniPrelaz.policijskiTerminal2.daLiJeBlokiran()) {
					sporedniTerminal = glavniTerminal; 
					glavniTerminal = GranicniPrelaz.policijskiTerminal1; 
					id = 1; 
				}
			}
		}
		
		synchronized (lock) {
			while(glavniTerminal.jeZauzet && sporedniTerminal.daLiJeBlokiran()) {
				try {
					lock.wait(); 
				} catch(InterruptedException ie) {
					GranicniPrelaz.gpLogger.logger.warning(ie.getMessage()); 
				}
			}
		}
			
		// OBRADI VOZILO 
		if(glavniTerminal.daLiJeBlokiran()) {
			glavniTerminal = sporedniTerminal; 
			if(id == 1) {
				id = 2; 
			} else {
				id = 1; 
			}
		}
		LocalTime currentTime = LocalTime.now(); 
		System.out.println(this + " je zauzelo TERMINAL " + id + ", vrijeme: " + currentTime);
		glavniTerminal.zauzmiTerminal(this);
		// Update vrijeme izvršavanja: 
		MyFrame.updateExecutionTime(); 
		// Treba se znati koje vozilo je zauzelo odgovarajući terminal 
		if(glavniTerminal == GranicniPrelaz.policijskiTerminal1) {
			MyFrame.updatePoliceTerminal1(this); 
		} else {
			MyFrame.updatePoliceTerminal2(this);
		}
		synchronized (GranicniPrelaz.redVozila) {
			GranicniPrelaz.red.remove(GranicniPrelaz.redVozila.poll());
			MyFrame.update(); // Ažurira GUI (pomjera vozila u redu za 1 unaprijed)
			SecondFrame.update();  // Ažurira preostalih 45 vozila 
			GranicniPrelaz.redVozila.notifyAll();
		}
		if(glavniTerminal.provjeriVozilo(this)) {
			System.out.println("VOZILO: " + this.getClass() + " je imalo vozaca kome je neispravan dokument."); 
			glavniTerminal.oslobodiTerminal(); 
			MyFrame.updateExecutionTime();
			if(glavniTerminal == GranicniPrelaz.policijskiTerminal1) {
				MyFrame.updatePoliceTerminal1(null);
			} else {
				MyFrame.updatePoliceTerminal2(null);
			}
			synchronized (lock) {
				lock.notify();
			}
			return; 
		}			
			
		// PRISTUP CARINSKOM TERMINALU 
		synchronized (GranicniPrelaz.carinskiTerminal) {
			while(GranicniPrelaz.carinskiTerminal.jeZauzet || GranicniPrelaz.carinskiTerminal.daLiJeBlokiran()) {
				try {
					GranicniPrelaz.carinskiTerminal.wait(); 
				} catch(InterruptedException ie) {
					GranicniPrelaz.gpLogger.logger.warning(ie.getMessage()); 
				}
			}
		}
			
		synchronized (GranicniPrelaz.carinskiTerminal) {
			GranicniPrelaz.carinskiTerminal.zauzmiTerminal(this); 
			MyFrame.updateExecutionTime();
			// Update GUI 
			MyFrame.updateCustomsTerminal(this); 
			currentTime = LocalTime.now(); 
			System.out.println(this + " je zauzelo CARINSKI TERMINAL" + ", vrijeme: " + currentTime);
		}
		
		if(!glavniTerminal.daLiJeBlokiran()) {
			glavniTerminal.oslobodiTerminal();
			MyFrame.updateExecutionTime();
			if(glavniTerminal == GranicniPrelaz.policijskiTerminal1) {
				MyFrame.updatePoliceTerminal1(null);
			} else {
				MyFrame.updatePoliceTerminal2(null);
			}
			synchronized (lock) {
				lock.notify(); 
			}
		} else {
			System.out.println(glavniTerminal.daLiJeBlokiran()); 
			glavniTerminal.oslobodiTerminal(); 
			MyFrame.updateExecutionTime();
			if(glavniTerminal == GranicniPrelaz.policijskiTerminal1) {
				MyFrame.updatePoliceTerminal1(null);
			} else {
				MyFrame.updatePoliceTerminal2(null);
			}
		}
				
		synchronized (GranicniPrelaz.carinskiTerminal) {
			GranicniPrelaz.carinskiTerminal.ispitajVozilo(this); 
			if(!GranicniPrelaz.carinskiTerminal.daLiJeBlokiran()) {
				GranicniPrelaz.carinskiTerminal.oslobodiTerminal();
				MyFrame.updateExecutionTime();
				MyFrame.updateCustomsTerminal(null); 
				GranicniPrelaz.carinskiTerminal.notify(); 
				// Provjera za izradu interfejsa za incidente i zatvaramo fajl 
				if(GranicniPrelaz.krajSimulacije()) {
					MyFrame.enableReport();
					try {
						Terminal.out.close();
					} catch (IOException e) {
						GranicniPrelaz.gpLogger.logger.warning(e.getMessage());
					} 
				}
			} else {
				GranicniPrelaz.carinskiTerminal.oslobodiTerminal();
				MyFrame.updateExecutionTime();
				MyFrame.updateCustomsTerminal(null); 
			}
		}	
	}		
	
	@Override
	public String toString() {
		return "Vozilo: " + this.getClass() + ", Broj putnika: " + putnici.size(); 
	}
}
