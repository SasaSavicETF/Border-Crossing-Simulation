package org.unibl.etf.pj2.simulacija;
import org.unibl.etf.pj2.interfejsi.SadrziKofereInterface;
import org.unibl.etf.pj2.interfejsi.SadrziTeretInterface;
import org.unibl.etf.pj2.terminali.*; 

import java.util.Random; 
import java.util.List;
import java.util.ArrayList;
import java.util.Queue; 
import java.util.LinkedList;  
import java.util.Collections;
import java.awt.Color;
import java.time.LocalTime;  
import java.time.format.DateTimeFormatter; 


public class GranicniPrelaz {
	public static List<Vozilo> red; 
	public static Queue<Vozilo> redVozila;
	public static Terminal policijskiTerminal1; 
	public static Terminal policijskiTerminal2; 
	public static Terminal policijskiZaKamione; 
	public static CarinskiTerminal carinskiTerminal; 
	public static CarinskiTerminal carinskiZaKamione; 
	
	public static Object lock = new Object(); 
	
	public static long startTime; 
	
	public static final String nevalidniDokumenti;
	public static final String potrebnaDokumentacija;
	
	private static final String LOG_FAJL = "GranicniPrelazLogger.log"; 
	public static MyLogger gpLogger = new MyLogger(LOG_FAJL); 
	
	
	static {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss"); 
		LocalTime currentTime = LocalTime.now(); 
		nevalidniDokumenti = "file_" + currentTime.format(formatter) + ".ser"; 
		potrebnaDokumentacija = "file_" + currentTime.plusSeconds(1).format(formatter) + ".txt"; 
	}
	
	public GranicniPrelaz() {
		startTime = System.currentTimeMillis(); 
		policijskiTerminal1 = new Terminal(); 
		policijskiTerminal2 = new Terminal(); 
		policijskiZaKamione = new Terminal();
		carinskiTerminal = new CarinskiTerminal(); 
		carinskiZaKamione = new CarinskiTerminal();  
	}
	
	// Racunanje stvarne mase posebnim kamionima
	public void izracunajStvarnuMasuKamiona(Kamion[] kamioni) {
		List<Integer> posebniKamioni = new ArrayList<Integer>(); 
		int brojPosebnihKamiona = (int) Math.round(0.2 * kamioni.length); 
		Random rand = new Random();
		
		for(int i = 0; i < brojPosebnihKamiona; i++) {
			int posebanKamion = rand.nextInt(kamioni.length); 
			if(!posebniKamioni.contains(posebanKamion)) {
				posebniKamioni.add(posebanKamion); 
				double doTridesetOdSto = (double) rand.nextInt(30); 
				kamioni[posebanKamion].setStvarnaMasa(kamioni[posebanKamion].getDeklarisanaMasa() + doTridesetOdSto/100 * kamioni[posebanKamion].getDeklarisanaMasa()); 
			} else {
				--i;
				continue; 
			}
		}
		
		// Setovanje stvarne mase ostalim kamionima 
		for(int j = 0; j < kamioni.length; j++) {
			if(!posebniKamioni.contains(j)) {
				kamioni[j].setStvarnaMasa(kamioni[j].getDeklarisanaMasa()); 
			}
		}
	}
	
	

	public void uveziKofere(Autobus autobus) {
		Random rand = new Random(); 
		List<Putnik> putnici = autobus.getPutnici();
		
		// Prebrojavanje putnika sa koferom 
		int brojPutnikaSaKoferom = 0; 
		for(int i = 0; i < putnici.size(); i++) {
			if(putnici.get(i).imaKofer()) {
				brojPutnikaSaKoferom++; 
			}
		}
		
		// 10% kofera sadrzi nedozvoljene stvari
		int brojNedozvoljenihKofera = (int) Math.round(0.1 * brojPutnikaSaKoferom); 
		for(int i = 0; i < brojNedozvoljenihKofera; i++) {
			int indeks = rand.nextInt(putnici.size());
				while (!putnici.get(indeks).imaKofer()) {
					indeks = rand.nextInt(putnici.size());
				}
			putnici.get(indeks).postaviValidnostKofera(false);
		}
	}
	

	public void evidentirajDokumente(Vozilo[] vozila) {
		Random rand = new Random(); 
		int ukupanBrojDokumenata = 0; 
		
		for(int i = 0; i < vozila.length; i++) {
			ukupanBrojDokumenata += vozila[i].getBrojPutnika(); 
		}
		
		// 3% dokumenata je neispavno
		int brojac = 0; 
		int brojNevalidnihDokumenata = (int) Math.round(0.03 * ukupanBrojDokumenata); 
		for(int i = 0; i < brojNevalidnihDokumenata; i++) {  
			int randomDokument = rand.nextInt(ukupanBrojDokumenata); 
			
			for(int j = 0; j < vozila.length; j++) { 
				brojac += vozila[j].getBrojPutnika(); 
											
				if(brojac >= randomDokument) {  
					int indeks = (vozila[j].getBrojPutnika() - 1) - (brojac - randomDokument); 
					if(indeks == vozila[j].getBrojPutnika()) {
						if(vozila[j].getPutnik(((vozila[j].getBrojPutnika() - 1) - (brojac - randomDokument)) - 1).getValidnostDokumenta()) {
							vozila[j].setPutnik(((vozila[j].getBrojPutnika() - 1) - (brojac - randomDokument)) - 1, false);
							brojac = 0; 
							break; 
						} else {
							brojac = 0; 
							break; 
						}
					} else {
						if(vozila[j].getPutnik(((vozila[j].getBrojPutnika() - 1) - (brojac - randomDokument))).getValidnostDokumenta()) {
							vozila[j].setPutnik(((vozila[j].getBrojPutnika() - 1) - (brojac - randomDokument)), false);
							brojac = 0;
							break; 
						} else {
							brojac = 0; 
							break; 
						}
					}
				}
			}
		}
	}
	
	public static void playPause(boolean status) {
		policijskiTerminal1.setBlokiranje(status);
		policijskiTerminal2.setBlokiranje(status);
		policijskiZaKamione.setBlokiranje(status);
		carinskiTerminal.setBlokiranje(status);
		carinskiZaKamione.setBlokiranje(status);
	}
	
	public static boolean krajSimulacije() {
		if(!policijskiTerminal1.jeZauzet && !policijskiTerminal2.jeZauzet && !policijskiZaKamione.jeZauzet
			&& !carinskiTerminal.jeZauzet && !carinskiZaKamione.jeZauzet && !policijskiTerminal1.daLiJeBlokiran() 
			&& !policijskiTerminal2.daLiJeBlokiran() && !policijskiZaKamione.daLiJeBlokiran() && !carinskiTerminal.daLiJeBlokiran()
			&& !carinskiZaKamione.daLiJeBlokiran()) {
				return true; 
		} else {
			return false; 
		}
	}
 	
	
	// MAIN 
	public static void main(String[] args) {
		// INICIJLAZICIJA 
		GranicniPrelaz granica = new GranicniPrelaz();
		List<Terminal> terminali = new ArrayList<Terminal>(); 
		terminali.add(policijskiTerminal1);
		terminali.add(policijskiTerminal2); 
		terminali.add(policijskiZaKamione);
		terminali.add(carinskiTerminal);
		terminali.add(carinskiZaKamione); 
		
		FileWatcher fileWatcher = new FileWatcher(terminali); 
		Thread thread = new Thread(fileWatcher); 
		thread.setDaemon(true); 
		thread.start(); 

		red = new ArrayList<Vozilo>(); 
		
		LicnoVozilo[] licnaVozila = new LicnoVozilo[35]; 
		Kamion[] kamioni = new Kamion[10]; 
		Autobus[] autobusi = new Autobus[5]; 
		// LICNA VOZILA:
		for(int i = 0; i < licnaVozila.length; i++) {
			licnaVozila[i] = new LicnoVozilo();
			red.add(licnaVozila[i]); 
		}
		granica.evidentirajDokumente(licnaVozila); 
		
		// KAMIONI:
		for(int i = 0; i < kamioni.length; i++) {
			kamioni[i] = new Kamion(); 
			red.add(kamioni[i]); 
		}
		granica.evidentirajDokumente(kamioni); 
		granica.izracunajStvarnuMasuKamiona(kamioni); 
		
		// AUTOBUSI: 
		for(int i = 0; i < autobusi.length; i++) {
			autobusi[i] = new Autobus(); 
			granica.uveziKofere(autobusi[i]); 
			red.add(autobusi[i]); 
		}
		granica.evidentirajDokumente(autobusi); 
		
		// Generisanje random poretka od 50 vozila 
		Collections.shuffle(red);
		redVozila = new LinkedList<Vozilo>(red); 
		
		MyFrame frame = new MyFrame(); 
		frame.setVisible(true);
		for(int i = 0; i < MyFrame.vehicles.length; i++) {
			if(red.get(i) instanceof SadrziKofereInterface) {
				MyFrame.vehicles[i].setBackground(new Color(173, 216, 230)); 
				MyFrame.vehicles[i].setForeground(Color.WHITE);
				MyFrame.vehicles[i].setText("A");
			} else if(red.get(i) instanceof SadrziTeretInterface) {
				MyFrame.vehicles[i].setBackground(Color.BLUE); 
				MyFrame.vehicles[i].setForeground(Color.WHITE);
				MyFrame.vehicles[i].setText("K");
			} else {
				MyFrame.vehicles[i].setBackground(Color.RED); 
				MyFrame.vehicles[i].setForeground(Color.WHITE); 
				MyFrame.vehicles[i].setText("V");
			}
		} 
		
		policijskiTerminal1.setBlokiranje(true);
		policijskiTerminal2.setBlokiranje(true);
		policijskiZaKamione.setBlokiranje(true);
		carinskiTerminal.setBlokiranje(true);
		carinskiZaKamione.setBlokiranje(true);
		 
		// Pokretanje vozila
		for(int i = 0; i < red.size(); i++) {
			System.out.println("Vozilo: " + red.get(i).getClass() + ", pozicija: " + red.indexOf(red.get(i)));
		}
		for(int i = 0; i < red.size(); i++) {
			red.get(i).start();
		}
		System.out.println("Broj vozila u redu: " + red.size()); 
		
		for(int i = 0; i < red.size(); i++) {
			try {
				red.get(i).join();
			} catch(InterruptedException ie) {
				gpLogger.logger.warning(ie.getMessage()); 
			}
		} 
		
	}
}
