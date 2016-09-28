package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clientserver.Client;

/**
 * Class for creating the menu to the game which allows the user to select connection port through
 * the use of swing.
 * @author Kyal
 *
 */
public class Menu implements MouseListener, ActionListener{
	private JFrame menuFrame;
	int imageCount = 0;
	JLabel menuLabel;
	Timer imageTimer;
	boolean change;
	JTextField portNum;
	JTextField address;
	JTextField playerName;
	JDialog portDialog;

	/**
	 * Setup JFrame
	 */
	public Menu(){
		menuFrame = new JFrame("Harambe, Second Coming");
		menuFrame.setSize(1300, 930);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupGraphics();
	}

	/**
	 * Creates panels and labels for images to be drawn
	 */
	private void setupGraphics(){
		//Setup panel
		JPanel menuPanel = new JPanel();
		menuLabel = new JLabel();
		menuPanel.add(menuLabel);

		menuFrame.add(menuPanel);
		menuFrame.addMouseListener(this);
		menuFrame.setResizable(false);
		menuFrame.setVisible(true);

		//Setup loop timers
		change = true;
		imageTimer = new Timer(100, this);
		imageTimer.start();
	}

	/**
	 * Loops images to produce small animation while waiting for user to click
	 */
	private void loopImage(){
		//Set current loop image and draw
		menuLabel.setIcon(images[imageCount]);
		menuFrame.revalidate();

		//Increment count depending on direction
		if(change) imageCount++;
		else imageCount--;
		//Change direction at end of array
		if(imageCount == 4){ change = false; imageCount = 3; }
		else if(imageCount == -1){ change = true; imageCount = 0; }
	}

	/**
	 * Create JDialog asking for port number and connect button
	 */
	private void selectPort(){
		//Create JDialog and setup options
		portDialog = new JDialog();
		portDialog.setTitle("Connection Details");
		portDialog.setSize(450,130);
		portDialog.setLocationRelativeTo(menuFrame);
		portDialog.setResizable(false);
		portDialog.setModal(true);

		//Jtextfield for name input
		portNum = new JTextField();
		portNum.setText("Port Number");
		portNum.setPreferredSize(new Dimension(140, 30));

		//Jtextfield for name input
		address = new JTextField();
		address.setText("Server Address");
		address.setPreferredSize(new Dimension(140, 30));

		//Jtextfield for name input
		playerName = new JTextField();
		playerName.setText("Username");
		playerName.setPreferredSize(new Dimension(140, 30));

		//JButton setup
		JButton connect = new JButton("Connect");
		connect.addActionListener(this);
		connect.setPreferredSize(new Dimension(140, 30));

		//Setup Jpanel
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(playerName);
		panel.add(address);
		panel.add(portNum);
		panel.add(connect);

		portDialog.add(panel);
		portDialog.setVisible(true);
	}

	private void connect(){
		Integer portNum = null;
		try{
			portNum = Integer.parseInt(this.portNum.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(menuFrame, "Enter a port Number.", "Input Error", JOptionPane.WARNING_MESSAGE);
		}

		String serverAddress = this.address.getText();
		String username = this.playerName.getText();

		System.out.println("PortNum: " + portNum + "\n");
		System.out.println("Server Address: " + serverAddress + "\n");
		System.out.println("Username: " + username + "\n");

		if(portNum != null){
			Client c = new Client(serverAddress, portNum, menuFrame);
		}
	}

	/**
	 * Helper method for loading image icons.
	 * @param filename
	 * @return
	 */
	private static ImageIcon makeImageIcon(String filename) {
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("assets/menu/"+filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(myPicture);
	}

	/**
	 * Show port selecter
	 * @param arg0
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		selectPort();
		
	}

	/**
	 * When timer goes off for looping image
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof Timer) loopImage();
		else{
			connect();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}

	public static ImageIcon m1 = makeImageIcon("m1.png");
	private ImageIcon m2 = makeImageIcon("m2.png");
	private ImageIcon m3 = makeImageIcon("m3.png");
	private ImageIcon m4 = makeImageIcon("m4.png");
	private ImageIcon[] images = { m1, m2, m3, m4 };

	public static void main(String[] args) {
		new Menu();
	}
}
