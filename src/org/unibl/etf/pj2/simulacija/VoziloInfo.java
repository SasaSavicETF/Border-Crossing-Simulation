package org.unibl.etf.pj2.simulacija;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.pj2.terminali.Putnik;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

import java.util.List; 

public class VoziloInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VoziloInfo(Vozilo vozilo) {
		setBounds(100, 100, 500, 240);
		setTitle(vozilo.getClass().toString()); 
		setVisible(true); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane(contentPane); 
		setContentPane(scrollPane); 

		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.add(new JLabel("Informacije:")); 
		contentPane.add(new JLabel("Broj putnika: " + vozilo.getBrojPutnika())); 
		List<Putnik> listaPutnika = vozilo.getPutnici(); 
		for(int i = 0; i < listaPutnika.size(); i++) {
			contentPane.add(new JLabel(listaPutnika.get(i).toString())); 
		}
		
	}

}
