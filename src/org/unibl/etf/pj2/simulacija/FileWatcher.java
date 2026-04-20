package org.unibl.etf.pj2.simulacija;
import org.unibl.etf.pj2.terminali.*;

import java.util.List; 
import java.util.ArrayList; 
import java.nio.file.*; 
import java.io.IOException; 

public class FileWatcher implements Runnable {
	public static final String apsolutnaPutanja = "C:\\Users\\QWERTY\\eclipse-workspace\\JavaProjekat"; 
	public static final String kontrolniFajl = "konfiguracija_terminala.txt"; 
	private List<Terminal> terminali; 
	
	public FileWatcher(List<Terminal> terminali) {
		this.terminali = terminali; 
	}
	
	@Override
	public void run() {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService(); 
			Path dir = Paths.get(apsolutnaPutanja);  
			dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY); 

			while(true) {
				WatchKey key = null; 
				try {
					key = watcher.take(); 
				} catch(InterruptedException ie) {
					GranicniPrelaz.gpLogger.logger.warning(ie.getMessage());
				}
				
				for(WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind(); 
					WatchEvent<Path> ev = (WatchEvent<Path>) event; 
					Path fileName = ev.context(); 
					System.out.println(kind.name() + ": " + fileName); 
					if(fileName.toString().trim().equals(kontrolniFajl) && kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
						azurirajStatusTerminala();  
					}
				}
				
				boolean valid = key.reset(); 
				if(!valid) {
					break; 
				}
			}
		} catch(IOException ex) {
			GranicniPrelaz.gpLogger.logger.warning(ex.getMessage());
		}
	}
	
	public void azurirajStatusTerminala() {
		try {
			List<String> linije = Files.readAllLines(Paths.get(kontrolniFajl)); 
			for(int i = 0; i < linije.size(); i++) {
//				int status = Integer.parseInt(linije.get(i));  
				String linija = linije.get(i); 
				String[] split = linija.split(" "); 
				int status = Integer.parseInt(split[1]); 
				if(status == 0) {
					terminali.get(i).setBlokiranje(true);  
				} else {
					terminali.get(i).setBlokiranje(false); 
					if(!terminali.get(i).jeZauzet) {
						if(i > 2) {
							synchronized (terminali.get(i)) {
								terminali.get(i).notify(); 
							}
						} else {
							synchronized (Vozilo.lock) {
								Vozilo.lock.notify(); 
							}
						}
					}
				}
			}
		} catch(IOException ex) {
			GranicniPrelaz.gpLogger.logger.warning(ex.getMessage());
		}
	}
}