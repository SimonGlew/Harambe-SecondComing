package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI implements KeyListener, ActionListener {
	JFrame gameFrame;
	JLabel gameLabel;

	public GUI(){
		System.out.println("APPLE");
		gameFrame = new JFrame("Harambe, Second Coming");
		gameFrame.setSize(1150, 860);
		gameFrame.setBackground(new Color(0,0,0));
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);

		prepareGUI();

		gameFrame.setVisible(true);
	}

	public void prepareGUI(){
		System.out.println("APPLE");
		//Prepare different areas on gui
		JPanel windowPanel = new JPanel(new FlowLayout());
		windowPanel.setBackground(new Color(5,26,37));

		//Create window that game image will be displayed on
		gameLabel = new JLabel();
		gameLabel.setIcon(Menu.m1);
		gameLabel.setPreferredSize(new Dimension(1000,800));
		gameLabel.setBorder(BorderFactory.createLineBorder(new Color(50)));

		//Create menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu gameBar = new JMenu("Game");
		JMenu helpBar = new JMenu("Help");

		//Sets each jmenu components
		createMenuBar(gameBar, helpBar);

		//Add the jmenus to menu bar
		menuBar.add(gameBar);
		menuBar.add(helpBar);
		gameFrame.setJMenuBar(menuBar);

		//Create items window
		JPanel uiPanel = new JPanel();
		uiPanel.setPreferredSize(new Dimension(130,800));
		uiPanel.setBackground(new Color(5,26,37));
		uiPanel.setBorder(BorderFactory.createLineBorder(new Color(50)));

		//Add components to window panel
		windowPanel.add(gameLabel, BorderLayout.CENTER);
		windowPanel.add(uiPanel, BorderLayout.EAST);

		gameFrame.add(windowPanel);
	}

	private void createMenuBar(JMenu gameBar, JMenu helpBar) {
		//Game Bar setup
		JMenuItem quit = new JMenuItem("Quit");
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		gameBar.add(quit);

		//Help Bar setup
		JMenuItem rules = new JMenuItem("Help");
		rules.setActionCommand("help");
		rules.addActionListener(this);
		helpBar.add(rules);

		//Shortcuts setup
		JMenuItem shortcuts = new JMenuItem("Shortcuts");
		shortcuts.setActionCommand("shortcuts");
		shortcuts.addActionListener(this);
		helpBar.add(shortcuts);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if("quit".equals(action.getActionCommand())){
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	public static void main(String[] args) {
		new GUI();
	}

}
