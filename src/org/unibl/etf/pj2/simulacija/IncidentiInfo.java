package org.unibl.etf.pj2.simulacija;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.pj2.interfejsi.SadrziKofereInterface;
import org.unibl.etf.pj2.terminali.Putnik;
import org.unibl.etf.pj2.terminali.Terminal;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.io.FileReader; 
import java.io.BufferedReader;

public class IncidentiInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public IncidentiInfo() {
		setBounds(100, 100, 580, 400);
        setTitle("Incidenti");
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Create a JScrollPane and set the contentPane of the frame to it
        JScrollPane scrollPane = new JScrollPane(contentPane);
        setContentPane(scrollPane);

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(new JLabel("Informacije o incidentima:"));
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GranicniPrelaz.nevalidniDokumenti));   
            while(true) {
            	try {
            	Putnik putnik = (Putnik) ois.readObject(); 
	            	if(!putnik.getJeVozac()) {
	            		contentPane.add(new JLabel("Neispravan dokument: " + putnik.getIme() 
	                 						+ " iz vozila: " + putnik.getVozilo())); 
	            	} else {
	            		contentPane.add(new JLabel("Odstranjeno vozilo: " + putnik.getVozilo() 
                		 				+ " jer je vozac: " + putnik.getIme() + " imao neispravan dokument."));
	            	} 
            	} catch(EOFException | ClassNotFoundException ex) {
            		GranicniPrelaz.gpLogger.logger.warning(ex.getMessage());
            		break; 
            	} catch (IOException ex) {
            		GranicniPrelaz.gpLogger.logger.warning(ex.getMessage());
                }
            }
            ois.close(); 
        } catch (IOException ex) {
        	GranicniPrelaz.gpLogger.logger.warning(ex.getMessage());
        }
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(GranicniPrelaz.potrebnaDokumentacija)); 
			String line; 
			while((line = in.readLine()) != null) {
				contentPane.add(new JLabel(line)); 
			}
			in.close(); 
		} catch(IOException ex) {
			GranicniPrelaz.gpLogger.logger.warning(ex.getMessage());
		}
	}

}
