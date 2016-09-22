package worldeditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class EditorFrame extends JFrame {

	JPanel panel;
	BufferedImage image;
	WorldEditor editor;	

	public EditorFrame(WorldEditor editor) {
		this.editor = editor;
		panel = new JPanel() {
			public void paint(Graphics g) {
				paintPanel((Graphics2D) g);
			}
		};
		panel.setPreferredSize(new Dimension(1000, 800));
		panel.addMouseListener(new EditorMouseListener());
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
	
	private class EditorMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			Point selected = editor.renderer.isoToIndex(e.getX(), e.getY());
			editor.processTile(selected.x, selected.y);
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
		
	}
}
