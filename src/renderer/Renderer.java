package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import core.Board;
import core.GameSystem;
import core.GameSystem.Direction;
import core.Location;
import tile.Tile;
import util.Position;

public class Renderer {

	TempWindow window;
	private Tile selected;
	BufferedImage highlightTile;
	BufferedImage highlightLocation;
	Direction selectedLocation = Direction.NORTH;

	final int TILE_WIDTH = 45;
	int xOffset;
	int yOffset;

	public Renderer() {
		try {
			highlightTile = ImageIO.read(new File("src/highlightTile.png"));
			highlightLocation = ImageIO.read(new File("src/highlightLocation.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage paintLocation(Location loc, int w, int h) {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		Map<Point, Integer> map = loc.getBoard().mapLocations(loc.getId(), 0, 0, new HashMap<Point, Integer>());
		loc.getBoard().linkLocations(map);
		drawBoard(g, loc.getBoard(), map, w, h, new Point(-1, 1));
		drawBoard(g, loc.getBoard(), map, w, h, new Point(0, 1));
		drawBoard(g, loc.getBoard(), map, w, h, new Point(-1, 0));
		drawBoard(g, loc.getBoard(), map, w, h, new Point(1, 1));
		drawBoard(g, loc.getBoard(), map, w, h, new Point(0, 0));
		drawBoard(g, loc.getBoard(), map, w, h, new Point(-1, -1));
		drawBoard(g, loc.getBoard(), map, w, h, new Point(1, 0));
		drawBoard(g, loc.getBoard(), map, w, h, new Point(0, -1));
		drawBoard(g, loc.getBoard(), map, w, h, new Point(1, -1));

		return image;
	}


	public void drawBoard(Graphics2D g, Board board, Map<Point, Integer> map, int w, int h, Point p) {
		if(!map.containsKey(p)){
			return;
		}
		int xOff = p.x*10*TILE_WIDTH;
		int yOff = -p.y*10*TILE_WIDTH;
		calculateOffsets(board.getLocationById(map.get(new Point(0, 0))), w, h);
		for (int i = 0; i < board.getLocationById(map.get(p)).getTiles().length; i++) {
			for (int j = 0; j < board.getLocationById(map.get(p)).getTiles()[0].length; j++) {
				int x = xOff + i * TILE_WIDTH;
				int y = yOff + j * TILE_WIDTH;
				Point iso = twoDToIso(x, y);
				drawTile(g, board.getLocationById(map.get(p)).getTiles()[i][j], iso, board.getLocationById(map.get(p)), new Position(i, j));
			}
		}
		drawSelected(g);
		drawSelectedLocation(g);
	}

	private void drawTile(Graphics2D g, Tile tile, Point iso, Location loc, Position pos) {
		BufferedImage floor = tile.getImage();
		if (floor != null) {
			g.drawImage(floor, iso.x, iso.y - floor.getHeight(), null);
		}

		if (tile.getGameObject() != null) {
			BufferedImage gameObject = tile.getGameObject().getImage(loc, pos);
			g.drawImage(gameObject, iso.x, iso.y - gameObject.getHeight(), null);
		}
	}

	public void drawSelected(Graphics2D g) {
		if (selected != null) {
			int x = selected.getPos().getX() * TILE_WIDTH;
			int y = selected.getPos().getY() * TILE_WIDTH;
			Point iso = twoDToIso(x, y);
			g.drawImage(highlightTile, (int) iso.getX(), (int) iso.getY() - highlightTile.getHeight(), null);
		}
	}

	public void drawSelectedLocation(Graphics2D g) {
		if (selectedLocation != null) {
			int locationSize = 10;
			Point iso;
			switch (selectedLocation.toString()) {
			case "NORTH":
				iso = twoDToIso((-locationSize * TILE_WIDTH / 2), -(locationSize / 2) * TILE_WIDTH);
				g.drawImage(highlightLocation, iso.x + TILE_WIDTH, iso.y - TILE_WIDTH, null);
				break;
			case "EAST":
				iso = twoDToIso((locationSize * TILE_WIDTH / 2), (locationSize / 2) * TILE_WIDTH);
				g.drawImage(highlightLocation, iso.x + TILE_WIDTH, iso.y - TILE_WIDTH, null);
				break;
			case "WEST":
				iso = twoDToIso((-3 * locationSize * TILE_WIDTH / 2), (locationSize * TILE_WIDTH / 2));
				g.drawImage(highlightLocation, iso.x + TILE_WIDTH, iso.y - TILE_WIDTH, null);
				break;
			case "SOUTH":
				iso = twoDToIso((-locationSize * TILE_WIDTH / 2), (3 * locationSize / 2) * TILE_WIDTH);
				g.drawImage(highlightLocation, iso.x + TILE_WIDTH, iso.y - TILE_WIDTH, null);
				break;
			}
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
		tempPt.x = (2 * (y - yOffset) + (x - xOffset)) / 2;
		tempPt.y = (2 * (y - yOffset) - (x - xOffset)) / 2;
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
		Point index = new Point((twoD.x + TILE_WIDTH / 2) / TILE_WIDTH,
				(int) ((twoD.y + 1.5 * TILE_WIDTH)) / TILE_WIDTH);
		return index;
	}

	public void selectTile(Tile t) {
		setSelected(t);
	}

	public void selectTile(Position pos, Location loc) {
		setSelected(getTileAtPos(pos, loc));
	}

	public Tile getTileAtPos(Position pos, Location loc) {
		if (pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < loc.getTiles().length
				&& pos.getY() < loc.getTiles()[0].length) {
			return loc.getTiles()[pos.getX()][pos.getY()];
		}
		return null;
	}

	public Tile getSelected() {
		return selected;
	}

	public void setSelected(Tile selected) {
		this.selected = selected;
	}

	public void selectLocation(Direction dir) {
		this.selectedLocation = dir;
	}
}