package org.unibl.etf.pj2.terminali;
import org.unibl.etf.pj2.simulacija.*;
import org.unibl.etf.pj2.interfejsi.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream; 
import java.io.IOException;  
import java.util.List; 

public class Terminal { 
	public volatile boolean jeZauzet;
	public static ObjectOutputStream out; 
	public Vozilo trenutnoVozilo; 
	
	private boolean jeBlokiran; 
	
	
	public Terminal() {
		this.jeZauzet = false; 
		try {
			out = new ObjectOutputStream(new FileOutputStream(GranicniPrelaz.nevalidniDokumenti));
		} catch (FileNotFoundException e) {
			GranicniPrelaz.gpLogger.logger.warning(e.getMessage());
		} catch (IOException e) {
			GranicniPrelaz.gpLogger.logger.warning(e.getMessage());
		} 
	}
	
	public void zauzmiTerminal(Vozilo trenutnoVozilo) {
		jeZauzet = true;
		this.trenutnoVozilo = trenutnoVozilo; 
	}
	
	public boolean provjeriVozilo(Vozilo vozilo) {
		boolean imaVozaca = false; 
		
		try {
			if(vozilo instanceof SadrziKofereInterface) {
				vozilo.sleep(vozilo.getBrojPutnika() * 100); 
			} else {
				vozilo.sleep(vozilo.getBrojPutnika() * 500); 
			}				
		} catch(InterruptedException ie) {
			GranicniPrelaz.gpLogger.logger.warning(ie.getMessage());
		}
		
		List<Putnik> putnici = vozilo.getPutnici(); 
		for(int i = 0; i < putnici.size(); i++) {
			if(!putnici.get(i).getValidnostDokumenta()) { 
				try {
					out.writeObject(putnici.get(i)); 
					System.out.println("Upisan putnik za " + vozilo.getClass() + ", Broj putnika: " + vozilo.getBrojPutnika());
					if(putnici.get(i).getJeVozac()) {
						imaVozaca = true; 
					} else {
						vozilo.ukloniPutnika(putnici.get(i));
					}
				} catch(IOException ex) {
					GranicniPrelaz.gpLogger.logger.warning(ex.getMessage());
				} 
			}
		}
		return imaVozaca; 
	}
	
	public void oslobodiTerminal() {
		trenutnoVozilo = null; 
		jeZauzet = false; 
	}
	
	public void setBlokiranje(boolean jeBlokiran) {
		this.jeBlokiran = jeBlokiran; 
	}
	
	public boolean daLiJeBlokiran() {
		return jeBlokiran; 
	}
	
	public Vozilo getTrenutnoVozilo() {
		return trenutnoVozilo; 
	}
}
