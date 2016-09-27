package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

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

	/**
	 * Setup JFrame
	 */
	public Menu(){
		menuFrame = new JFrame("Harambe, Second Coming");
		menuFrame.setSize(1300, 900);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupGraphics();
	}

	/**
	 * Creates panels and labels for images to be drawn
	 */
	public void setupGraphics(){
		JPanel menuPanel = new JPanel();
		menuLabel = new JLabel();
		menuPanel.add(menuLabel);

		menuFrame.add(menuPanel);
		menuFrame.addMouseListener(this);
		menuFrame.setResizable(false);
		menuFrame.setVisible(true);

		change = true;
		imageTimer = new Timer(100, this);
		imageTimer.start();
	}

	/**
	 * Loops images to produce small animation while waiting for user to click
	 */
	public void loopImage(){
		System.out.print(imageCount);
		menuLabel.setIcon(images[imageCount]);
		menuFrame.revalidate();

		if(change) imageCount++;
		else imageCount--;
		if(imageCount == 4){ change = false; imageCount = 3; }
		else if(imageCount == -1){ change = true; imageCount = 0; }

	}

	/**
	 * Helper method for loading image icons.
	 * @param filename
	 * @return
	 */
	private ImageIcon makeImageIcon(String filename) {
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("src/images/"+filename));
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
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}

	private ImageIcon m1 = makeImageIcon("m1.png");
	private ImageIcon m2 = makeImageIcon("m2.png");
	private ImageIcon m3 = makeImageIcon("m3.png");
	private ImageIcon m4 = makeImageIcon("m4.png");
	private ImageIcon[] images = { m1, m2, m3, m4 };

	public static void main(String[] args) {
		new Menu();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		loopImage();
	}
}
