package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Location;
import tile.Tile;

public class Renderer {

	TempWindow window;

	char[][] board = { { 'w', 'w', 'w', 'w', 1, 1, 1, 1, 1, 1 }, { 'w', 'w', 'w', 'w', 0, 0, 0, 0, 0, 1 },
			{ 'w', 'w', 'w', 0, 0, 0, 0, 0, 0, 0 }, { 'w', 'w', 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 'w', 'w', 0, 0, 0, 0, 0, 0, 0, 0 }, { 'w', 0, 0, 0, 0, 0, 0, 0, 1, 1 },
			{ 'w', 0, 0, 0, 0, 0, 0, 0, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
			{ 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 } };

	final int TILE_WIDTH = 45;
	int xOffset;
	int yOffset;

	public Renderer() {

	}

	public BufferedImage paintLocation(Location loc, int w, int h) {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		drawBoard(g, loc, w, h);
		return image;
	}

	public void drawBoard(Graphics2D g, Location loc, int w, int h) {
		calculateOffsets(loc, w, h);
		for (int i = 0; i < loc.getTiles().length; i++) {
			for (int j = 0; j < loc.getTiles()[0].length; j++) {
				int x = i * TILE_WIDTH;
				int y = j * TILE_WIDTH;
				Point iso = twoDToIso(x, y);
				drawTile(g, loc.getTiles()[i][j], iso);
			}
		}
	}

	private void drawTile(Graphics2D g, Tile tile, Point pos) {
		BufferedImage floor = tile.getImage();
		if (floor != null) {
			g.drawImage(floor, pos.x, pos.y - floor.getHeight(), null);
		}
		if (tile.getGameObject() != null) {
			BufferedImage gameObject = tile.getGameObject().getImage();
			g.drawImage(gameObject, pos.x, pos.y - gameObject.getHeight(), null);
		}
	}

	public void calculateOffsets(Location loc, int w, int h) {
		int boardHeight = (int) ((loc.getTiles().length + loc.getTiles()[0].length - 1) * TILE_WIDTH
				* Math.sin(Math.PI / 6));
		xOffset = (int) (w / 2 - 2 * TILE_WIDTH * Math.sin(Math.PI / 6));
		yOffset = (int) ((h - boardHeight) / 2 + TILE_WIDTH * Math.cos(Math.PI / 6));
	}

	public Point isoTo2D(int x, int y) {
		Point tempPt = new Point(0, 0);
		tempPt.x = (2 * (y-yOffset) + (x-xOffset)) / 2;
		tempPt.y = (2 * (y-yOffset) - (x-xOffset)) / 2;
		return (tempPt);
	}

	public Point twoDToIso(int x, int y) {
		Point tempPt = new Point(0, 0);
		tempPt.x = xOffset + x - y;
		tempPt.y = yOffset + (x + y) / 2;
		return (tempPt);
	}

	public static void main(String[] args) {
		new Renderer();
	}

	public Point isoToIndex(int x, int y) {
		Point twoD = isoTo2D(x, y);
		System.out.println(twoD);
		Point index = new Point((twoD.x + TILE_WIDTH/2)/TILE_WIDTH, (int) ((twoD.y + 1.5*TILE_WIDTH))/TILE_WIDTH);
		System.out.println(index);
		return index;
	}
}