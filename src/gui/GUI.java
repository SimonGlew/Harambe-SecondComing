package gui;

import java.awt.BorderLayout;

import core.*;
import iohandling.BoardCreator;
import renderer.Renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
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
	JPanel UIPanel;
	JPanel inventorySlots;
	public static final Color MAINCOLOR = new Color(5,26,37);
	public static final Color SECONDARYCOLOR = new Color(255,182,0);
	public static final Color MAINCOLOR2 = new Color(2, 13, 18);

	public GUI(){
		gameFrame = new JFrame("Harambe, Second Coming");
		gameFrame.setSize(1150, 860);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);

		prepareGUI();

		gameFrame.setVisible(true);
	}

	public void prepareGUI(){
		//Prepare different areas on gui
		JPanel windowPanel = new JPanel(new FlowLayout());
		windowPanel.setBackground(MAINCOLOR);

		//Create window that game image will be displayed on
		gameLabel = new JLabel();
		gameLabel.setPreferredSize(new Dimension(1000,800));
		tempView();

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
		UIPanel = new JPanel();
		UIPanel.setPreferredSize(new Dimension(130,800));
		UIPanel.setBackground(MAINCOLOR);
		UIPanel.setBorder(BorderFactory.createLineBorder(MAINCOLOR2, 3));

		//Setup UI pane
		setupUI();

		//Add components to window panel
		windowPanel.add(gameLabel, BorderLayout.CENTER);
		windowPanel.add(UIPanel, BorderLayout.EAST);

		gameFrame.add(windowPanel);
	}

	private void setupUI(){
		//Setup up name panel
		JPanel namePanel = new JPanel(new FlowLayout());
		namePanel.setPreferredSize(new Dimension(120, 160));
		namePanel.setBackground(MAINCOLOR);

		JLabel img = new JLabel();
		img.setIcon(nameImage);
		JLabel playerName = new JLabel();
		playerName.setText("Kyal");
		playerName.setForeground(Color.WHITE);
		playerName.setFont(new Font("title", Font.BOLD, 20));

		namePanel.add(img);
		namePanel.add(playerName);

		//Setup inventory panel
		JPanel inventoryPanel = new JPanel();
		inventoryPanel.setPreferredSize(new Dimension(128, 490));
		inventoryPanel.setBorder(BorderFactory.createLineBorder(MAINCOLOR2, 2));
		inventoryPanel.setBackground(MAINCOLOR);

		//Setup Title
		JLabel inventory = new JLabel();
		inventory.setText("Inventory");
		inventory.setForeground(Color.WHITE);
		inventory.setFont(new Font("title", Font.BOLD, 22));
		Font font = inventory.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		inventory.setFont(font.deriveFont(attributes));

		//Setup inventory slots
		inventorySlots = new JPanel(new FlowLayout());
		inventorySlots.setPreferredSize(new Dimension(120, 320));
		inventorySlots.setBackground(MAINCOLOR);
		setupInventorySlots();

		//Add banana count
		JLabel bananaLabel = new JLabel();
		bananaLabel.setText("Bananas");
		bananaLabel.setForeground(Color.WHITE);
		bananaLabel.setFont(font.deriveFont(attributes));

		JLabel bananaImg = new JLabel();
		bananaImg.setIcon(bananaImage);
		JLabel count = new JLabel();
		count.setFont(new Font("title", Font.BOLD, 22));
		count.setText("x 0");
		count.setForeground(Color.WHITE);

		inventoryPanel.add(inventory);
		inventoryPanel.add(inventorySlots);
		inventoryPanel.add(bananaLabel);
		inventoryPanel.add(bananaImg);
		inventoryPanel.add(count);

		//Setup view panel
		JLabel viewLabel = new JLabel();
		viewLabel.setText("View");
		viewLabel.setForeground(Color.WHITE);
		viewLabel.setFont(font.deriveFont(attributes));
		JLabel arrowImg = new JLabel();
		arrowImg.setIcon(arrowImage);

		JPanel viewPanel = new JPanel(new FlowLayout());
		viewPanel.setPreferredSize(new Dimension(120, 125));
		viewPanel.setBackground(MAINCOLOR);
		viewPanel.add(viewLabel);
		viewPanel.add(arrowImg);

		UIPanel.add(namePanel);
		UIPanel.add(inventoryPanel);
		UIPanel.add(viewPanel);
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

	private void setupInventorySlots(){
		int i = 0;
		while(i < 10){
			JLabel slot = new JLabel();
			slot.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 0), 4));
			slot.setPreferredSize(new Dimension(50, 50));
			inventorySlots.add(slot);
			JLabel slot2 = new JLabel();
			slot2.setPreferredSize(new Dimension(100,5));
			slot2.setBackground(MAINCOLOR);
			if(i%2 == 1) inventorySlots.add(slot2);
			i++;
		}
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if("quit".equals(action.getActionCommand())){
			System.exit(0);
		}
	}

	public void tempView(){
		Board b = BoardCreator.loadBoard("map.txt");
		Renderer rend = new Renderer();
		BufferedImage i = rend.paintLocation(b.getLocationById(0), 1000, 800);
		gameLabel.setIcon(new ImageIcon(i));
	}

	public static ImageIcon nameImage = Menu.makeImageIcon("gui/namebe.png");
	public static ImageIcon bananaImage = Menu.makeImageIcon("gui/banaga.png");
	public static ImageIcon arrowImage = Menu.makeImageIcon("gui/arrows.png");

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
