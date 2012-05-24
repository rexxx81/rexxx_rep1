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

public class swingTest2 {
	public static JTextField textField;

	public swingTest2() {
		init();
	}
		public static JPanel init() {
		System.out.println("TestPane");
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
		
		JLabel lblArtikelNr = new JLabel("Artikel232323 Nr.:");
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
