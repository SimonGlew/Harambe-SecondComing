package worldeditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import core.GameSystem;
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
			Point selected = editor.renderer.isoToIndex(e.getX(), e.getY());
			if (e.getButton() == 1) {
				editor.processTile(selected.x, selected.y);
			} else if (e.getButton() == 3) {
				editor.clearTile(selected.x, selected.y);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			Point selected = editor.renderer.isoToIndex(e.getX(), e.getY());
			Tile tile = editor.renderer.getTileAtPos(new Position(selected.x, selected.y), editor.loc);
			editor.selectTile(selected);
			editor.selectLocation(null);
			if (tile == null) {
				if (selected.x >= 0 && selected.x < editor.loc.getTiles().length) {
					if (selected.y < 0) {
						editor.selectLocation(GameSystem.Direction.NORTH);
					}
					if (selected.y > editor.loc.getTiles()[0].length) {
						editor.selectLocation(GameSystem.Direction.SOUTH);
					}
				}
				if (selected.y >= 0 && selected.y < editor.loc.getTiles()[0].length) {
					if (selected.x < 0) {
						editor.selectLocation(GameSystem.Direction.WEST);
					}
					if (selected.x > editor.loc.getTiles().length) {
						editor.selectLocation(GameSystem.Direction.EAST);
					}
				}
			}
		}
	}

	private class EditorMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			Point selected = editor.renderer.isoToIndex(e.getX(), e.getY());
			if(editor.renderer.getTileAtPos(new Position(selected.x, selected.y), editor.loc) == null){
				if (selected.x >= 0 && selected.x < editor.loc.getTiles().length) {
					if (selected.y < 0) {
						editor.clickLocation(GameSystem.Direction.NORTH);
					}
					if (selected.y > editor.loc.getTiles()[0].length) {
						editor.clickLocation(GameSystem.Direction.SOUTH);
					}
				}
				if (selected.y >= 0 && selected.y < editor.loc.getTiles()[0].length) {
					if (selected.x < 0) {
						editor.clickLocation(GameSystem.Direction.WEST);
					}
					if (selected.x > editor.loc.getTiles().length) {
						editor.clickLocation(GameSystem.Direction.EAST);
					}
				}
			}
			editor.selectTile(selected);
			if (e.getButton() == 1) {
				editor.processTile(selected.x, selected.y);
			} else if (e.getButton() == 3) {
				editor.clearTile(selected.x, selected.y);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}
}
