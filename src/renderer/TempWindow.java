package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TempWindow extends JFrame{
	
	JPanel panel;
	
	public TempWindow(){
		panel = new JPanel(){
			public void paint(Graphics g){
				paintPanel((Graphics2D)g);
			}
		};
		panel.setPreferredSize(new Dimension(800, 600));
		add(panel);
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public void paintPanel(Graphics2D g){
		int width = panel.getWidth();
		int height = panel.getHeight();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height);
	}
}
