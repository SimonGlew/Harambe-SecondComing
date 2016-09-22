	package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TempWindow extends JFrame {

	JPanel panel;
	BufferedImage image;

	public TempWindow() {
		panel = new JPanel() {
			public void paint(Graphics g) {
				paintPanel((Graphics2D) g);
			}
		};
		panel.setPreferredSize(new Dimension(1000, 800));
		add(panel);
		pack();
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paintPanel(Graphics2D g) {
		int width = panel.getWidth();
		int height = panel.getHeight();
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		panel.repaint();
	}
}
