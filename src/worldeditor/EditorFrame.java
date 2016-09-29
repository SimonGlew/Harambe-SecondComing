package worldeditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import core.GameSystem;
import core.Location;
import tile.Tile;
import util.Position;

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
		panel.addMouseMotionListener(new EditorMouseMotionListener());
		add(panel);
		pack();
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paintPanel(Graphics2D g) {
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		panel.repaint();
	}

	private class EditorMouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			Location loc = editor.board.getLocationById(editor.currentLocation);
			Position selected = editor.renderer.isoToIndex(e.getX(), e.getY());
			editor.selectTile(selected);
			if (e.getButton() == 1 || e.getButton() == 0) {
				editor.processTile(selected.getX(), selected.getY());
			} else if (e.getButton() == 3) {
				editor.clearTile(selected.getX(), selected.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			Location loc = editor.board.getLocationById(editor.currentLocation);
			Position selected = editor.renderer.isoToIndex(e.getX(), e.getY());
			Tile tile = editor.renderer.getTileAtPos(selected, loc);
			editor.selectTile(selected);
			editor.selectLocation(null);
			if (tile == null) {
				if (selected.getX() >= 0 && selected.getX() < loc.getTiles().length) {
					if (selected.getY() < 0) {
						editor.selectLocation(GameSystem.Direction.NORTH);
					}
					if (selected.getY() > loc.getTiles()[0].length) {
						editor.selectLocation(GameSystem.Direction.SOUTH);
					}
				}
				if (selected.getY() >= 0 && selected.getY() < loc.getTiles()[0].length) {
					if (selected.getX() < 0) {
						editor.selectLocation(GameSystem.Direction.WEST);
					}
					if (selected.getX() > loc.getTiles().length) {
						editor.selectLocation(GameSystem.Direction.EAST);
					}
				}
			}
		}
	}

	private class EditorMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Location loc = editor.board.getLocationById(editor.currentLocation);
			Position selected = editor.renderer.isoToIndex(e.getX(), e.getY());
			if(editor.renderer.getTileAtPos(new Position(selected.getX(), selected.getY()), loc) == null){
				if (selected.getX() >= 0 && selected.getX() < loc.getTiles().length) {
					if (selected.getY() < 0) {
						editor.clickLocation(GameSystem.Direction.NORTH);
					}
					if (selected.getY() > loc.getTiles()[0].length) {
						editor.clickLocation(GameSystem.Direction.SOUTH);
					}
				}
				if (selected.getY() >= 0 && selected.getY() < loc.getTiles()[0].length) {
					if (selected.getX() < 0) {
						editor.clickLocation(GameSystem.Direction.WEST);
					}
					if (selected.getX() > loc.getTiles().length) {
						editor.clickLocation(GameSystem.Direction.EAST);
					}
				}
			}
			editor.selectTile(selected);
			if (e.getButton() == 1) {
				editor.processTile(selected.getX(), selected.getY());
			} else if (e.getButton() == 3) {
				editor.clearTile(selected.getX(), selected.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}
}
