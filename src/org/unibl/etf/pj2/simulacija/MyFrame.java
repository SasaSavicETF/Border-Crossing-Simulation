package org.unibl.etf.pj2.simulacija;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.pj2.interfejsi.SadrziKofereInterface;
import org.unibl.etf.pj2.interfejsi.SadrziTeretInterface;
import org.unibl.etf.pj2.terminali.Terminal;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class MyFrame extends JFrame {

	private JPanel contentPane;
	public static JButton[] vehicles;
	private static JButton policijski1; 
	private static JButton policijski2; 
	private static JButton policijskiKamioni; 
	private static JButton carinski; 
	private static JButton carinskiKamioni; 
	public static JLabel time;
	
	private boolean running = false; 
	private static JButton btnNewButton_1;
	

	/**
	 * Create the frame.
	 */
	public MyFrame() {
		setSize(670, 480); 
		setResizable(false);
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		policijski1 = new JButton("P1");
		policijski1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.policijskiTerminal1.getTrenutnoVozilo() != null) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.policijskiTerminal1.getTrenutnoVozilo()); 
				}
			}
		});
		policijski1.setBounds(181, 149, 64, 36);
		policijski1.setBackground(Color.GRAY);
		policijski1.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(policijski1);
		policijski1.repaint();
		
		policijski2 = new JButton("P2");
		policijski2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.policijskiTerminal2.getTrenutnoVozilo() != null) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.policijskiTerminal2.getTrenutnoVozilo()); 
				}
			}
		});
		policijski2.setBounds(301, 149, 64, 36);
		policijski2.setBackground(Color.GRAY);
		policijski2.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(policijski2);
		policijski2.repaint();
		
		policijskiKamioni = new JButton("PK");
		policijskiKamioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.policijskiZaKamione.getTrenutnoVozilo() != null) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.policijskiZaKamione.getTrenutnoVozilo()); 
				}
			}
		});
		policijskiKamioni.setBounds(421, 149, 64, 36);
		policijskiKamioni.setBackground(Color.GRAY);
		policijskiKamioni.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(policijskiKamioni);
		policijskiKamioni.repaint();
		
		carinski = new JButton("C1");
		carinski.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.carinskiTerminal.getTrenutnoVozilo() != null) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.carinskiTerminal.getTrenutnoVozilo()); 
				}
			}
		});
		carinski.setBounds(181, 67, 64, 36);
		carinski.setFont(new Font("Tahoma", Font.BOLD, 11));
		carinski.setBackground(Color.GRAY);
		getContentPane().add(carinski);
		carinski.repaint();
		
		carinskiKamioni = new JButton("CK");
		carinskiKamioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.carinskiZaKamione.getTrenutnoVozilo() != null) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.carinskiZaKamione.getTrenutnoVozilo()); 
				}
			}
		});
		carinskiKamioni.setFont(new Font("Tahoma", Font.BOLD, 11));
		carinskiKamioni.setBackground(Color.GRAY);
		carinskiKamioni.setBounds(421, 67, 64, 36);
		getContentPane().add(carinskiKamioni);
		carinskiKamioni.repaint();
		
		vehicles = new JButton[5]; 
		
		vehicles[0] = new JButton("New button");
		vehicles[0].setBounds(301, 221, 64, 36);
		vehicles[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.red.contains(GranicniPrelaz.red.get(0))) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.red.get(0)); 
				}
			}
		});
		getContentPane().add(vehicles[0]);
		
		vehicles[1] = new JButton("New button");
		vehicles[1].setBounds(301, 258, 64, 36);
		vehicles[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.red.contains(GranicniPrelaz.red.get(1))) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.red.get(1)); 
				}
			}
		});
		getContentPane().add(vehicles[1]);
		
		vehicles[2] = new JButton("New button");
		vehicles[2].setBounds(301, 295, 64, 36);
		vehicles[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.red.contains(GranicniPrelaz.red.get(2))) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.red.get(2)); 
				}
			}
		});
		getContentPane().add(vehicles[2]);
		
		vehicles[3] = new JButton("New button");
		vehicles[3].setBounds(301, 332, 64, 36);
		vehicles[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.red.contains(GranicniPrelaz.red.get(3))) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.red.get(3)); 
				}
			}
		});
		getContentPane().add(vehicles[3]);
		
		vehicles[4] = new JButton("New button");
		vehicles[4].setBounds(301, 369, 64, 36);
		vehicles[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GranicniPrelaz.red.contains(GranicniPrelaz.red.get(4))) {
					VoziloInfo info = new VoziloInfo(GranicniPrelaz.red.get(4)); 
				}
			}
		});
		getContentPane().add(vehicles[4]);
		
		SecondFrame frame2 = new SecondFrame(); 
		for(int i = 0; i < GranicniPrelaz.red.size() - 5; i++) {
			if(GranicniPrelaz.red.get(i + 5) instanceof SadrziKofereInterface) {
				SecondFrame.otherVehicles[i].setBackground(new Color(173, 216, 230)); 
				SecondFrame.otherVehicles[i].setForeground(Color.WHITE);
				SecondFrame.otherVehicles[i].setText("A");
			} else if(GranicniPrelaz.red.get(i + 5) instanceof SadrziTeretInterface) {
				SecondFrame.otherVehicles[i].setBackground(Color.BLUE); 
				SecondFrame.otherVehicles[i].setForeground(Color.WHITE);
				SecondFrame.otherVehicles[i].setText("K"); 
			} else {
				SecondFrame.otherVehicles[i].setBackground(Color.RED);
				SecondFrame.otherVehicles[i].setForeground(Color.WHITE); 
				SecondFrame.otherVehicles[i].setText("V");
			}
		}
		 
		
		JButton playPauseButton = new JButton("PLAY");
		playPauseButton.setBounds(1, 1, 80, 40);
		playPauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Dugme stisnuto."); 
				if(!running) {
					GranicniPrelaz.playPause(false); 
					pokreniVozila(); 
					running = true; 
					playPauseButton.setText("PAUSE"); 
				} else {
					GranicniPrelaz.playPause(true); 
					running = false; 
					playPauseButton.setText("PLAY"); 
				}
			}
		});
		playPauseButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(playPauseButton);
		playPauseButton.repaint();
		
		JButton btnNewButton = new JButton("OSTATAK REDA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(1, 394, 130, 36);
		getContentPane().add(btnNewButton);
		btnNewButton.repaint();  
		
		btnNewButton_1 = new JButton("INCIDENTI");
		btnNewButton_1.setVisible(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 new IncidentiInfo();  
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(554, 394, 100, 36);
		getContentPane().add(btnNewButton_1);
		btnNewButton_1.repaint(); 
		
		time = new JLabel("VRIJEME:");
		time.setFont(new Font("Tahoma", Font.BOLD, 11));
		time.setBounds(479, 14, 165, 27);
		getContentPane().add(time);
		time.repaint();
	}
	
	public static void updateExecutionTime() {
		time.setText("VRIJEME: " + (System.currentTimeMillis() - GranicniPrelaz.startTime));
	}
	
	public static void enableReport() {
		btnNewButton_1.setVisible(true); 
	}
	
	public static void update() {
		if(GranicniPrelaz.red.size() < 5) {
			for(int j = GranicniPrelaz.red.size(); j < 5; j++) {
				vehicles[j].setBackground(Color.WHITE);
				vehicles[j].setForeground(Color.WHITE);
				vehicles[j].setText("N");
			}
			for(int i = 0; i < GranicniPrelaz.red.size(); i++) {
				if(GranicniPrelaz.red.get(i) instanceof SadrziKofereInterface) {
					vehicles[i].setBackground(new Color(173, 216, 230)); 
					vehicles[i].setForeground(Color.WHITE);
					vehicles[i].setText("A");
				} else if(GranicniPrelaz.red.get(i) instanceof SadrziTeretInterface) {
					vehicles[i].setBackground(Color.BLUE); 
					vehicles[i].setForeground(Color.WHITE);
					vehicles[i].setText("K"); 
				} else {
					vehicles[i].setBackground(Color.RED);
					vehicles[i].setForeground(Color.WHITE); 
					vehicles[i].setText("V");
				}
			}
		} else {
			for(int i = 0; i < vehicles.length; i++) {
				if(GranicniPrelaz.red.get(i) instanceof SadrziKofereInterface) {
					vehicles[i].setBackground(new Color(173, 216, 230)); 
					vehicles[i].setForeground(Color.WHITE);
					vehicles[i].setText("A");
				} else if(GranicniPrelaz.red.get(i) instanceof SadrziTeretInterface) {
					vehicles[i].setBackground(Color.BLUE); 
					vehicles[i].setForeground(Color.WHITE);
					vehicles[i].setText("K");
				} else {
					vehicles[i].setBackground(Color.RED);
					vehicles[i].setForeground(Color.WHITE); 
					vehicles[i].setText("V");
				}
			}
		}
	}
	
	public static void updatePoliceTerminal1(Vozilo vozilo) {
		if(vozilo instanceof SadrziKofereInterface) {
			policijski1.setBackground(new Color(173, 216, 230)); 
			policijski1.setForeground(Color.WHITE);
			policijski1.setText("A");
		} else if(vozilo instanceof SadrziTeretInterface) {
			policijski1.setBackground(Color.BLUE); 
			policijski1.setForeground(Color.WHITE);
			policijski1.setText("K"); 
		} else {
			policijski1.setBackground(Color.RED);
			policijski1.setForeground(Color.WHITE); 
			policijski1.setText("V");
		}
		if(vozilo == null) {
			policijski1.setBackground(Color.GRAY); 
			policijski1.setForeground(Color.BLACK);
			policijski1.setText("P1"); 
		}
	}
	
	public static void updatePoliceTerminal2(Vozilo vozilo) {
		if(vozilo instanceof SadrziKofereInterface) {
			policijski2.setBackground(new Color(173, 216, 230)); 
			policijski2.setForeground(Color.WHITE);
			policijski2.setText("A");
		} else if(vozilo instanceof SadrziTeretInterface) {
			policijski2.setBackground(Color.BLUE); 
			policijski2.setForeground(Color.WHITE);
			policijski2.setText("K"); 
		} else {
			policijski2.setBackground(Color.RED);
			policijski2.setForeground(Color.WHITE); 
			policijski2.setText("V");
		}
		if(vozilo == null) {
			policijski2.setBackground(Color.GRAY); 
			policijski2.setForeground(Color.BLACK);
			policijski2.setText("P2"); 
		}
	}
	
	public static void updatePoliceTerminalForTrucks(Vozilo vozilo) {
		if(vozilo instanceof SadrziKofereInterface) {
			policijskiKamioni.setBackground(new Color(173, 216, 230)); 
			policijskiKamioni.setForeground(Color.WHITE);
			policijskiKamioni.setText("A");
		} else if(vozilo instanceof SadrziTeretInterface) {
			policijskiKamioni.setBackground(Color.BLUE); 
			policijskiKamioni.setForeground(Color.WHITE);
			policijskiKamioni.setText("K"); 
		} else {
			policijskiKamioni.setBackground(Color.RED);
			policijskiKamioni.setForeground(Color.WHITE); 
			policijskiKamioni.setText("V");
		}
		if(vozilo == null) {
			policijskiKamioni.setBackground(Color.GRAY); 
			policijskiKamioni.setForeground(Color.BLACK);
			policijskiKamioni.setText("PK"); 
		}
		
	}
	
	public static void updateCustomsTerminal(Vozilo vozilo) {
		if(vozilo instanceof SadrziKofereInterface) {
			carinski.setBackground(new Color(173, 216, 230)); 
			carinski.setForeground(Color.WHITE);
			carinski.setText("A");
		} else if(vozilo instanceof SadrziTeretInterface) {
			carinski.setBackground(Color.BLUE); 
			carinski.setForeground(Color.WHITE);
			carinski.setText("K"); 
		} else {
			carinski.setBackground(Color.RED);
			carinski.setForeground(Color.WHITE); 
			carinski.setText("V");
		}
		if(vozilo == null) {
			carinski.setBackground(Color.GRAY); 
			carinski.setForeground(Color.BLACK);
			carinski.setText("C1"); 
		}
	}
	
	public static void updateCustomsTerminalForTrucks(Vozilo vozilo) {
		if(vozilo instanceof SadrziKofereInterface) {
			carinskiKamioni.setBackground(new Color(173, 216, 230)); 
			carinskiKamioni.setForeground(Color.WHITE);
			carinskiKamioni.setText("A");
		} else if(vozilo instanceof SadrziTeretInterface) {
			carinskiKamioni.setBackground(Color.BLUE); 
			carinskiKamioni.setForeground(Color.WHITE);
			carinskiKamioni.setText("K"); 
		} else {
			carinskiKamioni.setBackground(Color.RED);
			carinskiKamioni.setForeground(Color.WHITE); 
			carinskiKamioni.setText("V");
		}
		if(vozilo == null) {
			carinskiKamioni.setBackground(Color.GRAY); 
			carinskiKamioni.setForeground(Color.BLACK);
			carinskiKamioni.setText("CK"); 
		}
	}
	
	public void pokreniVozila() {
		synchronized (GranicniPrelaz.carinskiTerminal) {
			GranicniPrelaz.carinskiTerminal.notify(); 
		}
		synchronized (GranicniPrelaz.carinskiZaKamione) {
			GranicniPrelaz.carinskiZaKamione.notify(); 
		}
		synchronized (Vozilo.lock) {
			Vozilo.lock.notify(); 
		}
	}
}
