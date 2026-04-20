package org.unibl.etf.pj2.simulacija;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.pj2.interfejsi.SadrziKofereInterface;
import org.unibl.etf.pj2.interfejsi.SadrziTeretInterface;

public class SecondFrame extends JFrame {

	private JPanel contentPane;
	public static JButton[] otherVehicles; 

	/**
	 * Create the frame.
	 */
	public SecondFrame() {
		setBounds(100, 100, 670, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
 
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		otherVehicles = new JButton[45]; 
		
		int xPozicija = 181; 
		int yPozicija = 0; 
		int element = 0; 
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 15; j++) {
				final int currentElement = element; 
				otherVehicles[currentElement] = new JButton("New button"); 
				otherVehicles[currentElement].setBounds(xPozicija, yPozicija, 64, 36);
				otherVehicles[currentElement].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(GranicniPrelaz.red.contains(GranicniPrelaz.red.get(currentElement + 5))) {
							VoziloInfo info = new VoziloInfo(GranicniPrelaz.red.get(currentElement + 5)); 
						}
					}
				});
				getContentPane().add(otherVehicles[currentElement]); 
				element++; 
				yPozicija += 37; 
			}
			xPozicija += 120;
			yPozicija = 0; 
		}
	}

	public static void update() {
		if(GranicniPrelaz.red.size() <= 50 && GranicniPrelaz.red.size() >= 5) {
			for(int j = GranicniPrelaz.red.size() - 5; j < 45; j++) {
				otherVehicles[j].setBackground(Color.WHITE);
				otherVehicles[j].setForeground(Color.WHITE);
				otherVehicles[j].setText("N"); 
			}
		}
		for(int i = 0; i < GranicniPrelaz.red.size() - 5; i++) {
			if(GranicniPrelaz.red.get(i + 5) instanceof SadrziKofereInterface) {
				otherVehicles[i].setBackground(new Color(173, 216, 230)); 
				otherVehicles[i].setForeground(Color.WHITE);
				otherVehicles[i].setText("A");
			} else if(GranicniPrelaz.red.get(i + 5) instanceof SadrziTeretInterface) {
				otherVehicles[i].setBackground(Color.BLUE); 
				otherVehicles[i].setForeground(Color.WHITE);
				otherVehicles[i].setText("K"); 
			} else {
				otherVehicles[i].setBackground(Color.RED);
				otherVehicles[i].setForeground(Color.WHITE); 
				otherVehicles[i].setText("V");
			}
		}
	}
}
