package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI implements KeyListener, ActionListener {
	JFrame gameFrame;
	JLabel gameLabel;
	
	public GUI(){
		gameFrame = new JFrame("Harambe, Second Coming");
		gameFrame.setSize(1010, 1000);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		
		prepareGUI();
		
		gameFrame.setVisible(true);
	}
	
	public void prepareGUI(){
		//Prepare different areas on gui
		JPanel windowPanel = new JPanel(new FlowLayout());
		
		//Create window that game image will be displayed on
		gameLabel = new JLabel();
		gameLabel.setIcon(Menu.m1);
		gameLabel.setPreferredSize(new Dimension(1000,800));
		gameLabel.setBackground(new Color(0));
		
		//Create items window
		JPanel uiPanel = new JPanel();
		uiPanel.setPreferredSize(new Dimension(1000,150));
		uiPanel.setBackground(new Color(0));
		
		//Add components to window panel
		windowPanel.add(gameLabel);
		windowPanel.add(uiPanel);
		
		gameFrame.add(windowPanel);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
}
