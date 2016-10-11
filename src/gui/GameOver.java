package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameOver implements ActionListener{
	JFrame gameFrame;
	Timer timer;
	int count = 1;
	JLabel image;
	
	public GameOver(String name){
		gameFrame = new JFrame("Harambe, Second Coming: WINNER");
		gameFrame.setSize(1150, 850);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		
		timer = new Timer(500, this);
		
		JPanel gamePanel = new JPanel(new FlowLayout());
		gamePanel.setBackground(GUI.MAINCOLOR);
		
		JLabel winner = new JLabel();
		winner.setPreferredSize(new Dimension(1150, 30));
		winner.setForeground(Color.WHITE);
		winner.setFont(new Font("title", Font.BOLD, 22));
		winner.setText("                                                                   " + name + " is the WINNER!");
		
		image = new JLabel();
		image.setPreferredSize(new Dimension(1150, 710));
		image.setIcon(image1);
		
		JButton close = new JButton();
		close.addActionListener(this);
		close.setPreferredSize(new Dimension(100, 20));
		close.setText("Quit");
		
		gamePanel.add(winner);
		gamePanel.add(image);
		gamePanel.add(close);
		gameFrame.add(gamePanel);
		gameFrame.setVisible(true);
		timer.start();
	}
	
	private void setIcon() {
		if(count == 2) image.setIcon(image2);
		else if(count == 3) image.setIcon(image3);
		else if(count == 4) image.setIcon(image4);
		else if(count == 5) image.setIcon(image5);
		else{
			int temp = count%2;
			if(temp == 0) image.setIcon(image10);
			else image.setIcon(image11);
		}
	}
	
	public static ImageIcon image1 = Menu.makeImageIcon("menu/endGame/image1.png");
	public static ImageIcon image2 = Menu.makeImageIcon("menu/endGame/image2.png");
	public static ImageIcon image3 = Menu.makeImageIcon("menu/endGame/image3.png");
	public static ImageIcon image4 = Menu.makeImageIcon("menu/endGame/image4.png");
	public static ImageIcon image5 = Menu.makeImageIcon("menu/endGame/image5.png");
	public static ImageIcon image10 = Menu.makeImageIcon("menu/endGame/image10.png");
	public static ImageIcon image11 = Menu.makeImageIcon("menu/endGame/image11.png");

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof Timer){
			count++;
			setIcon();
		}else{
			System.exit(0);
		}
	}
}
