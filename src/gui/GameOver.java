package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameOver implements ActionListener{
	JFrame gameFrame;

	public GameOver(String name){
		gameFrame = new JFrame("Harambe, Second Coming: WINNER");
		gameFrame.setSize(1150, 860);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		
		JLabel winner = new JLabel();
		winner.setPreferredSize(new Dimension(500, 500));
		winner.setText(name + " IS THE WINNER");
		
		JButton close = new JButton();
		close.addActionListener(this);
		close.setText("Close");
		
		gameFrame.add(winner);
		gameFrame.add(close);
		gameFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}
}
