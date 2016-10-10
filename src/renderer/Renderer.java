package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import core.Board;
import core.GameSystem;
import core.GameSystem.Direction;
import core.Location;
import gameobjects.Player;
import tile.Tile;
import util.Position;

public class Renderer {

	TempWindow window;
	private Tile selected;
	BufferedImage highlightTile;
	BufferedImage highlightLocation;
	BufferedImage playerSelect;
	Direction selectedLocation = null;

	Point selectedPoint = null;

	public Direction viewingDir = Direction.NORTH;

	final int TILE_WIDTH = 45;
	int xOffset;
	int yOffset;

	int xCenter;
	int yCenter;
	
	Point lastPoint;

	public Renderer() {
		try {
			highlightTile = ImageIO.read(new File("assets/renderer/highlightTile.png"));
			highlightLocation = ImageIO.read(new File("assets/renderer/highlightLocation.png"));
			playerSelect = ImageIO.read(new File("assets/renderer/playerSelect.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage paintBoard(Board board, Player player, int w, int h) {
		selectedPoint = null;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new Color(5, 26, 37));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());

		Location loc = player.getLocation();
		Map<Point, Integer> map = board.mapLocations(loc.getId(), 0, 0, new HashMap<Point, Integer>());
		int[] drawOrderX = null;
		int[] drawOrderY = null;
		switch (viewingDir) {
		case NORTH:
			int[] tempX1 = { -1, -1, 0, -1, 0, 1, 0, 1, 1 };
			int[] tempY1 = { 1, 0, 1, -1, 0, 1, -1, 0, -1 };
			drawOrderX = tempX1;
			drawOrderY = tempY1;
			break;
		case WEST:
			int[] tempX2 = { -1, 0, -1, 1, 0, -1, 1, 0, 1 };
			int[] tempY2 = { -1, -1, 0, -1, 0, 1, 0, 1, 1 };
			drawOrderX = tempX2;
			drawOrderY = tempY2;
			break;
		case SOUTH:
			int[] tempX3 = { 1, 1, 0, 1, 0, -1, 0, -1, -1 };
			int[] tempY3 = { -1, 0, -1, 1, 0, -1, 1, 0, 1 };
			drawOrderX = tempX3;
			drawOrderY = tempY3;
			break;
		case EAST:
			int[] tempX4 = { 1, 0, 1, -1, 0, 1, -1, 0, -1 };
			int[] tempY4 = { 1, 1, 0, 1, 0, -1, 0, -1, -1 };
			drawOrderX = tempX4;
			drawOrderY = tempY4;
			break;
		}

		for (int i = 0; i < drawOrderX.length; i++) {
			Point p = new Point(drawOrderX[i], drawOrderY[i]);
			drawBoard(g, board, map, w, h, p, player);
		}
		g.setColor(Color.MAGENTA);
		return image;
	}

	public BufferedImage paintLocation(Location loc, int w, int h) {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new Color(5, 26, 37));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		Map<Point, Integer> map = loc.getBoard().mapLocations(loc.getId(), 0, 0, new HashMap<Point, Integer>());
		int[] drawOrderX = null;
		int[] drawOrderY = null;
		switch (viewingDir) {
		case NORTH:
			int[] tempX1 = { -1, -1, 0, -1, 0, 1, 0, 1, 1 };
			int[] tempY1 = { 1, 0, 1, -1, 0, 1, -1, 0, -1 };
			drawOrderX = tempX1;
			drawOrderY = tempY1;
			break;
		case WEST:
			int[] tempX2 = { -1, 0, -1, 1, 0, -1, 1, 0, 1 };
			int[] tempY2 = { -1, -1, 0, -1, 0, 1, 0, 1, 1 };
			drawOrderX = tempX2;
			drawOrderY = tempY2;
			break;
		case SOUTH:
			int[] tempX3 = { 1, 1, 0, 1, 0, -1, 0, -1, -1 };
			int[] tempY3 = { -1, 0, -1, 1, 0, -1, 1, 0, 1 };
			drawOrderX = tempX3;
			drawOrderY = tempY3;
			break;
		case EAST:
			int[] tempX4 = { 1, 0, 1, -1, 0, 1, -1, 0, -1 };
			int[] tempY4 = { 1, 1, 0, 1, 0, -1, 0, -1, -1 };
			drawOrderX = tempX4;
			drawOrderY = tempY4;
			break;
		}
		
		for (int i = 0; i < drawOrderX.length; i++) {
			Point p = new Point(drawOrderX[i], drawOrderY[i]);
			drawBoard(g, loc.getBoard(), map, w, h, p, null);
		}
		xCenter = w / 2;
		yCenter = h / 2;
		return image;
	}

	public void drawBoard(Graphics2D g, Board board, Map<Point, Integer> map, int w, int h, Point p, Player player) {
		if (!map.containsKey(p)) {
			return;
		}
		calculateOffsets(board.getLocationById(map.get(new Point(0, 0))), w, h);
		switch (viewingDir) {
		case NORTH:
			drawBoardFromNorth(g, board, map, w, h, p, player);
			break;
		case SOUTH:
			drawBoardFromSouth(g, board, map, w, h, p, player);
			break;
		case EAST:
			drawBoardFromEast(g, board, map, w, h, p, player);
			break;
		case WEST:
			drawBoardFromWest(g, board, map, w, h, p, player);
			break;
		}
		drawSelected(g);
		drawSelectedLocation(g);
	}

	public void drawBoardFromNorth(Graphics2D g, Board board, Map<Point, Integer> map, int w, int h, Point p,
			Player player) {
		for (int i = 0; i < board.getLocationById(map.get(p)).getTiles().length; i++) {
			for (int j = 0; j < board.getLocationById(map.get(p)).getTiles()[0].length; j++) {
				Point iso = twoDToIso((int) (i + p.getX() * 10), (int) (j - p.getY() * 10));
				drawTile(g, board.getLocationById(map.get(p)).getTiles()[i][j], iso, board.getLocationById(map.get(p)),
						new Position(i, j));
			}
		}
		for (int i = 0; i < board.getLocationById(map.get(p)).getTiles().length; i++) {
			for (int j = 0; j < board.getLocationById(map.get(p)).getTiles()[0].length; j++) {
				Point iso = twoDToIso((int) (i + p.getX() * 10), (int) (j - p.getY() * 10));
				drawObject(g, board.getLocationById(map.get(p)).getTiles()[i][j], iso,
						board.getLocationById(map.get(p)), new Position(i, j), player);
			}
		}
	}

	public void drawBoardFromEast(Graphics2D g, Board board, Map<Point, Integer> map, int w, int h, Point p,
			Player player) {
		for (int j = 0; j < board.getLocationById(map.get(p)).getTiles()[0].length; j++) {
			for (int i = 9; i >= 0; i--) {
				Point iso = twoDToIso((int) (i + p.getX() * 10), (int) (j - p.getY() * 10));
				drawTile(g, board.getLocationById(map.get(p)).getTiles()[i][j], iso, board.getLocationById(map.get(p)),
						new Position(i, j));
			}
		}
		for (int j = 0; j < board.getLocationById(map.get(p)).getTiles()[0].length; j++) {
			for (int i = 9; i >= 0; i--) {
				Point iso = twoDToIso((int) (i + p.getX() * 10), (int) (j - p.getY() * 10));
				drawObject(g, board.getLocationById(map.get(p)).getTiles()[i][j], iso,
						board.getLocationById(map.get(p)), new Position(i, j), player);
			}
		}
	}

	public void drawBoardFromSouth(Graphics2D g, Board board, Map<Point, Integer> map, int w, int h, Point p,
			Player player) {
		for (int i = 9; i >= 0; i--) {
			for (int j = 9; j >= 0; j--) {
				Point iso = twoDToIso((int) (i + p.getX() * 10), (int) (j - p.getY() * 10));
				drawTile(g, board.getLocationById(map.get(p)).getTiles()[i][j], iso, board.getLocationById(map.get(p)),
						new Position(i, j));
			}
		}
		for (int i = 9; i >= 0; i--) {
			for (int j = 9; j >= 0; j--) {
				Point iso = twoDToIso((int) (i + p.getX() * 10), (int) (j - p.getY() * 10));
				drawObject(g, board.getLocationById(map.get(p)).getTiles()[i][j], iso,
						board.getLocationById(map.get(p)), new Position(i, j), player);
			}
		}
	}

	public void drawBoardFromWest(Graphics2D g, Board board, Map<Point, Integer> map, int w, int h, Point p,
			Player player) {
		for (int j = 9; j >= 0; j--) {
			for (int i = 0; i < board.getLocationById(map.get(p)).getTiles().length; i++) {
				Point iso = twoDToIso((int) (i + p.getX() * 10), (int) (j - p.getY() * 10));
				drawTile(g, board.getLocationById(map.get(p)).getTiles()[i][j], iso, board.getLocationById(map.get(p)),
						new Position(i, j));
			}
		}
		for (int j = 9; j >= 0; j--) {
			for (int i = 0; i < board.getLocationById(map.get(p)).getTiles().length; i++) {
				Point iso = twoDToIso((int) (i + p.getX() * 10), (int) (j - p.getY() * 10));
				drawObject(g, board.getLocationById(map.get(p)).getTiles()[i][j], iso,
						board.getLocationById(map.get(p)), new Position(i, j), player);
			}
		}
	}

	private void drawObject(Graphics2D g, Tile tile, Point iso, Location loc, Position pos, Player player) {
		if (tile.getGameObject() != null) {
			if (tile.getGameObject() instanceof Player) {
				if (tile.getGameObject() == player) {
					g.drawImage(playerSelect, iso.x, iso.y - playerSelect.getHeight(), null);
				}
			}
			BufferedImage gameObject = tile.getGameObject().getImage(loc, pos, viewingDir);
			g.drawImage(gameObject, iso.x, iso.y - gameObject.getHeight(), null);
		}
	}

	private void drawTile(Graphics2D g, Tile tile, Point iso, Location loc, Position pos) {
		BufferedImage floor = tile.getImage();
		if (tile == selected) {
			selectedPoint = iso;
		}
		if (floor != null) {
			g.drawImage(floor, iso.x, iso.y - floor.getHeight(), null);
		}

	}

	public void drawSelected(Graphics2D g) {
		if (selectedPoint != null) {
			g.drawImage(highlightTile, (int) selectedPoint.getX(),
					(int) selectedPoint.getY() - highlightTile.getHeight(), null);
		}
	}

	public void drawSelectedLocation(Graphics2D g) {
		if (selectedLocation != null) {
			int locationSize = 10;
			Point iso;
			switch (selectedLocation) {
			case NORTH:
				iso = twoDToIso((-locationSize/2), -(locationSize / 2));
				g.drawImage(highlightLocation, iso.x + TILE_WIDTH, iso.y - TILE_WIDTH, null);
				break;
			case EAST:
				iso = twoDToIso((locationSize/ 2), (locationSize / 2));
				g.drawImage(highlightLocation, iso.x + TILE_WIDTH, iso.y - TILE_WIDTH, null);
				break;
			case WEST:
				iso = twoDToIso((-3 * locationSize/ 2), (locationSize/ 2));
				g.drawImage(highlightLocation, iso.x + TILE_WIDTH, iso.y - TILE_WIDTH, null);
				break;
			case SOUTH:
				iso = twoDToIso((-locationSize/ 2), (3 * locationSize / 2));
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

	public Point twoDToIso(int i, int j) {
		int x = 0, y = 0;
		switch (viewingDir) {
		case NORTH:
			x = TILE_WIDTH * i;
			y = TILE_WIDTH * j;
			break;
		case SOUTH:
			x = TILE_WIDTH * (10 - i - 1);
			y = TILE_WIDTH * (10 - j - 1);
			break;
		case EAST:
			x = TILE_WIDTH * j;
			y = TILE_WIDTH * (10 - i - 1);
			break;
		case WEST:
			x = TILE_WIDTH * (10 - j - 1);
			y = TILE_WIDTH * i;
			break;
		}
		Point tempPt = new Point(0, 0);	
		tempPt.x = xOffset + (x - y);
		tempPt.y = yOffset + ((x + y) / 2);
		return (tempPt);
	}

	public static void main(String[] args) {
		new Renderer();
	}

	public Position isoToIndex(int x, int y) {
		double a = (x - xOffset)/2 + y - yOffset;
		double b = 2 * (y - yOffset) - a;
		
		int i = 0, j = 0;
		switch(viewingDir){
			case NORTH:
				i = (int) Math.round(a / TILE_WIDTH);
				j = (int) Math.round(b / TILE_WIDTH + 1);
				break;
			case SOUTH:
				i = (int) Math.round(-1 * a / TILE_WIDTH + 9);
				j = (int) Math.round(-1 * b / TILE_WIDTH + 8);
				break;
			case EAST:
				i = (int) Math.round(-1 * b / TILE_WIDTH + 8);
				j = (int) Math.round(a / TILE_WIDTH);
				break;
			case WEST:
				i = (int) Math.round(b / TILE_WIDTH + 1);
				j = (int) Math.round(-1 * a / TILE_WIDTH + 9);
				break;
		}
		System.out.println(viewingDir.toString());
		System.out.println(i + ", " + j);

		Position index = new Position(i, j);
		lastPoint = new Point(index.getX(), index.getY());
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

	public Direction clockwiseDir(Direction d) {
		if (d == Direction.NORTH) {
			return Direction.EAST;
		}
		if (d == Direction.EAST) {
			return Direction.SOUTH;
		}
		if (d == Direction.WEST) {
			return Direction.NORTH;
		}
		if (d == Direction.SOUTH) {
			return Direction.WEST;
		}
		return null;
	}

	public Direction counterClockwiseDir(Direction d) {
		if (d == Direction.NORTH) {
			return Direction.WEST;
		}
		if (d == Direction.EAST) {
			return Direction.NORTH;
		}
		if (d == Direction.WEST) {
			return Direction.SOUTH;
		}
		if (d == Direction.SOUTH) {
			return Direction.EAST;
		}
		return null;
	}

	public void rotateCounterClockwise() {
		viewingDir = clockwiseDir(viewingDir);
	}

	public void rotateClockwise() {
		viewingDir = counterClockwiseDir(viewingDir);

	}
}