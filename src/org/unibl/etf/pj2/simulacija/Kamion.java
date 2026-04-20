package org.unibl.etf.pj2.simulacija;
import org.unibl.etf.pj2.terminali.*;
import org.unibl.etf.pj2.interfejsi.*;

import java.util.Random; 
import java.time.LocalTime; 
import java.io.FileWriter;
import java.awt.Color;
import java.io.BufferedWriter; 
import java.io.PrintWriter; 
import java.io.IOException; 

public class Kamion extends Vozilo implements SadrziTeretInterface {
	private transient Teret teret; 
	private double deklarisanaMasa; 
	private double stvarnaMasa;  
	
	public Kamion() {
		super(3); 
		teret = new Teret(); 
		Random rand = new Random(); 
		deklarisanaMasa = (double)(rand.nextInt(3) + 1); 
	}
	
	public Teret getTeret() {
		return teret; 
	}
	
	public double getDeklarisanaMasa() {
		return deklarisanaMasa; 
	}
	
	public void setStvarnaMasa(double stvarnaMasa) {
		this.stvarnaMasa = stvarnaMasa; 
	}
	
	public double getStvarnaMasa() {
		return stvarnaMasa; 
	}
	
	// Za kamion je jedino drugacija pa se mora redefinisati
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
			while(GranicniPrelaz.policijskiZaKamione.jeZauzet || GranicniPrelaz.policijskiZaKamione.daLiJeBlokiran()) {
				try {
					lock.wait(); 
				} catch(InterruptedException ie) {
					GranicniPrelaz.gpLogger.logger.warning(ie.getMessage());
				}
			}
		}
		
		if(!GranicniPrelaz.policijskiZaKamione.jeZauzet) {
			System.out.println(this + " je zauzeo TERMINAL ZA KAMIONE"); 
			GranicniPrelaz.policijskiZaKamione.zauzmiTerminal(this); 
			MyFrame.updateExecutionTime();
			// Update GUI
			MyFrame.updatePoliceTerminalForTrucks(this);
			synchronized (GranicniPrelaz.redVozila) {
				GranicniPrelaz.red.remove(GranicniPrelaz.redVozila.poll());
				MyFrame.update(); // Ažurira red vozila (pomjera vozila za 1 unaprijed)
				SecondFrame.update(); // Ažurira ostalih 45 vozila 
				GranicniPrelaz.redVozila.notifyAll(); 
			}
			if(GranicniPrelaz.policijskiZaKamione.provjeriVozilo(this)) {
				System.out.println("VOZILO: " + this.getClass() + " je imalo vozaca kome je neispravan dokument."); 
				GranicniPrelaz.policijskiZaKamione.oslobodiTerminal(); 
				MyFrame.updateExecutionTime();
				MyFrame.updatePoliceTerminalForTrucks(null);
				synchronized (lock) {
					lock.notify();
				}
				return; 
			}
			
			
			
			// PRISTUP CARINSKOM TERMINALU 
			synchronized (GranicniPrelaz.carinskiZaKamione) {
				while(GranicniPrelaz.carinskiZaKamione.jeZauzet || GranicniPrelaz.carinskiZaKamione.daLiJeBlokiran()) {
					try {
						GranicniPrelaz.carinskiZaKamione.wait(); 
					} catch(InterruptedException ie) {
						GranicniPrelaz.gpLogger.logger.warning(ie.getMessage()); 
					}
				}
				LocalTime currentTime = LocalTime.now(); 
				System.out.println(this + " je zauzelo CARINSKI TERMINAL ZA KAMIONE" + ", vrijeme: " + currentTime);
				GranicniPrelaz.carinskiZaKamione.zauzmiTerminal(this);
				MyFrame.updateExecutionTime();
				// Update GUI
				MyFrame.updateCustomsTerminalForTrucks(this);
				if(!GranicniPrelaz.policijskiZaKamione.daLiJeBlokiran()) {
					GranicniPrelaz.policijskiZaKamione.oslobodiTerminal();
					MyFrame.updateExecutionTime();
					MyFrame.updatePoliceTerminalForTrucks(null);
					synchronized (lock) {
						lock.notify(); 
					}
				} else {
					GranicniPrelaz.policijskiZaKamione.oslobodiTerminal();
					MyFrame.updateExecutionTime();
					MyFrame.updatePoliceTerminalForTrucks(null);
				}
				GranicniPrelaz.carinskiZaKamione.ispitajVozilo(this); 
				if(this.getStvarnaMasa() > this.getDeklarisanaMasa()) {
					try {
						PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(GranicniPrelaz.potrebnaDokumentacija, true))); 
						out.println(this.getClass() + ", Broj putnika: " + this.getBrojPutnika() + " nema ispravnu deklarisanu masu."); 
						out.close(); 
					} catch(IOException ex) {
						GranicniPrelaz.gpLogger.logger.warning(ex.getMessage());
					}
					GranicniPrelaz.carinskiZaKamione.oslobodiTerminal(); 
					MyFrame.updateExecutionTime();
					MyFrame.updateCustomsTerminalForTrucks(null);
					GranicniPrelaz.carinskiZaKamione.notify(); 
					return; 
				}
				
				if(!GranicniPrelaz.carinskiZaKamione.daLiJeBlokiran()) {
					GranicniPrelaz.carinskiZaKamione.oslobodiTerminal();
					MyFrame.updateExecutionTime();
					MyFrame.updateCustomsTerminalForTrucks(null);
					GranicniPrelaz.carinskiZaKamione.notify(); 
					// Provjera za izradu interfejsa za incidente
					if(GranicniPrelaz.krajSimulacije()) {
						MyFrame.enableReport();
					}
				} else {
					GranicniPrelaz.carinskiZaKamione.oslobodiTerminal();
					MyFrame.updateExecutionTime();
					MyFrame.updateCustomsTerminalForTrucks(null);
				}
			}
		}
	}
}
