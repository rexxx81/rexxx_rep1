package de.broo.test.swing;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import java.awt.BorderLayout;

public class swingTest extends JFrame {
	private JTextField textField;

	public swingTest() {
		super();
		
		setTitle("EbayManager v.0.1");
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame frame = new JFrame();
	    final JPanel hauptPanel = init();
		getContentPane().add(hauptPanel);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu("Datei");
		mnDatei.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) {
			}
			public void menuDeselected(MenuEvent e) {
			}
			public void menuSelected(MenuEvent e) {
			}
		});
		
		menuBar.add(mnDatei);
		
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		
		mnDatei.add(mntmBeenden);
		
		JMenu mnNextPane = new JMenu("Next Pane");
		menuBar.add(mnNextPane);
		
		JMenuItem menuItem = new JMenuItem("1");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			hauptPanel.setVisible(false);
			JPanel hauptPanel = swingTest2.init();
			getContentPane().add(hauptPanel);
			}
		});
		mnNextPane.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("2");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hauptPanel.setVisible(false);
				JPanel hauptPanel = init();
				getContentPane().add(hauptPanel);
			}
		});
		mnNextPane.add(menuItem_1);
		
	}
	
	public static void main(String[] args) {
		swingTest hauptfenster = new swingTest();
		
		hauptfenster.setSize(500, 400);
		hauptfenster.setLocation(200,300);
		hauptfenster.setVisible(true);
		}
	
	private JPanel init() {
		
		System.out.println("init 1");
		JPanel panel = new JPanel ();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		JButton btnSuchen = new JButton("Suchen");
		btnSuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		// Klick auf den erstellen Button
			}
		});
		JButton button2 = new JButton("Abbrechen");
		
		JLabel lblArtikelNr = new JLabel("Artikel Nr.:");
		panel.add(lblArtikelNr);
		
		textField = new JTextField();
		
		
				
			
		
		panel.add(textField);
		textField.setColumns(20);
		panel.add(btnSuchen);
		panel.add(button2);
		
		panel.setBackground(Color.GRAY);
		return panel;
		
		
	}
	
}