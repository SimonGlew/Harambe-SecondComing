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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

public class GUI implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
	ClientController controller;
	JFrame gameFrame;
	JLabel gameLabel;
	JPanel UIPanel;
	JPanel inventorySlots;
	public static final Color MAINCOLOR = new Color(5,26,37);
	public static final Color SECONDARYCOLOR = new Color(255,182,0);
	public static final Color MAINCOLOR2 = new Color(2, 13, 18);

	public GUI(ClientController c){
		this.controller = c;
		gameFrame = new JFrame("Harambe, Second Coming");
		gameFrame.setSize(1150, 860);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.addMouseListener(this);
		gameFrame.addMouseMotionListener(this);

		prepareGUI();

		gameFrame.setVisible(true);
		
	}
	
	public void hideGUI(){
		gameFrame.dispose();
	}

	public void prepareGUI(){
		//Prepare different areas on gui
		JPanel windowPanel = new JPanel(new FlowLayout());
		windowPanel.setBackground(MAINCOLOR);

		//Create window that game image will be displayed on
		gameLabel = new JLabel();
		gameLabel.setPreferredSize(new Dimension(1000,800));

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
		img.setIcon(jackImage);
		JLabel playerName = new JLabel();
		playerName.setText(controller.getName());
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
		count.setText("x " + controller.getBananaCount());
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

	private void checkClicked(int x, int y) {
		if(y >= 780 && y <= 830){
			if(x >= 1020 && x <= 1070) controller.rotateLeft(); //rotate left
			else if(x >= 1075 && x <= 1125) controller.rotateRight(); //rotate right
		}
		if(y > gameFrame.getHeight() - gameLabel.getHeight()){
			if(x > 0 && x < 1000){
				controller.moveTo(x, y - (gameFrame.getHeight() - gameLabel.getHeight()));
			}
		}
	}
	
	private void checkMoved(int x, int y) {
		controller.selectTile(x, y - (gameFrame.getHeight() - gameLabel.getHeight()));
	}

	
	public void showBoard(BufferedImage i){
		gameLabel.setIcon(new ImageIcon(i));
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if("quit".equals(action.getActionCommand())){
			System.exit(0);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		checkClicked(arg0.getX(), arg0.getY());
	}

	public static ImageIcon nameImage = Menu.makeImageIcon("gui/namebe.png");
	public static ImageIcon bananaImage = Menu.makeImageIcon("gui/banaga.png");
	public static ImageIcon arrowImage = Menu.makeImageIcon("gui/arrows.png");
	public static ImageIcon jackImage = Menu.makeImageIcon("gui/jack.png");

	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		checkMoved(e.getX(), e.getY());
	}

}
