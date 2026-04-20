package org.unibl.etf.pj2.terminali;
import org.unibl.etf.pj2.simulacija.*;
import org.unibl.etf.pj2.interfejsi.*;

import java.util.List;
import java.util.ArrayList; 
import java.io.FileWriter;
import java.io.BufferedWriter; 
import java.io.PrintWriter; 
import java.io.IOException; 

public class CarinskiTerminal extends Terminal {
	public void ispitajVozilo(Vozilo vozilo) {
		try {
			if(vozilo instanceof SadrziKofereInterface) {
				Thread.sleep(vozilo.getBrojPutnika() * 100);
				List<Putnik> putnici = vozilo.getPutnici(); 
				for(int i = 0; i < putnici.size(); i++) {
					if(putnici.get(i).imaKofer()) {
						if(!putnici.get(i).getKofer().getValidnost()) {
							try {
								PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(GranicniPrelaz.potrebnaDokumentacija, true))); 
								out.println(putnici.get(i).getIme() + " posjeduje kofer koji sadrzi nedozvoljene stvari. Broj putnika: " + vozilo.getBrojPutnika()); 
								out.close(); 
							} catch(IOException ex) {
								GranicniPrelaz.gpLogger.logger.warning(ex.getMessage());
							}
							vozilo.ukloniPutnika(putnici.get(i)); 
						}
					}
				}
			} else if(vozilo instanceof SadrziTeretInterface) {
				if(((Kamion)vozilo).getTeret().getPotrebnaDokumentacija()) { 
					try {
						PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(GranicniPrelaz.potrebnaDokumentacija, true))); 
						out.println("Generisana carinska dokumentacija za " + vozilo.getClass() + ", Broj putnika: " + vozilo.getBrojPutnika()); 
						out.close(); 
					} catch(IOException ex) {
						GranicniPrelaz.gpLogger.logger.warning(ex.getMessage()); 
					}
				}
				Thread.sleep(500); 
		    } else {
				Thread.sleep(2000); 
			}
		} catch(InterruptedException ie) {
			GranicniPrelaz.gpLogger.logger.warning(ie.getMessage());
		}
		
		
	}
}
