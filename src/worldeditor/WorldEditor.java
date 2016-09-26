package worldeditor;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import core.Board;
import core.GameSystem;
import core.GameSystem.Direction;
import core.Location;
import gameobjects.Chest;
import gameobjects.Tree;
import gameobjects.Wall;
import renderer.Renderer;
import tile.GrassTile;
import tile.SandTile;
import tile.StoneTile;
import tile.Tile;
import tile.WaterTile;
import util.Position;

public class WorldEditor {

	Board board;
	int currentLocation = 0;
	EditorFrame frame;
	ToolSelectionFrame toolSelect;
	Renderer renderer;

	String tool = "none";
	String floor = "grass";
	private String gameObject = "tree";

	public WorldEditor() {
		renderer = new Renderer();
		//LOAD BOARD
		board = new Board(new HashMap<Integer, Location>());
		currentLocation = createBlankLocation();
		
		frame = new EditorFrame(this);
		toolSelect = new ToolSelectionFrame(this);
		update();
	}

	public int createBlankLocation() {
		Location loc = new Location(board.getNextUniqueId(), null, new Tile[10][10], board);
		for (int i = 0; i < loc.getTiles().length; i++) {
			for (int j = 0; j < loc.getTiles()[0].length; j++) {
				loc.getTiles()[i][j] = new GrassTile(new Position(i, j), null);
			}
		}
		board.addLocation(loc.getId(), loc);
		return loc.getId();
	}

	public void update() {
		if (board.getLocationById(currentLocation) != null) {
			frame.setImage(renderer.paintLocation(board.getLocationById(currentLocation), frame.panel.getWidth(), frame.panel.getHeight()));
		}
	}

	public static void main(String[] args) {
		new WorldEditor();
	}

	public void processTile(int i, int j) {
		if (i >= 0 && j >= 0 && i < board.getLocationById(currentLocation).getTiles().length && j < board.getLocationById(currentLocation).getTiles()[0].length) {
			Tile tile = board.getLocationById(currentLocation).getTiles()[i][j];
			if (tool.equals("Set Floor Type")) {
				Tile newTile = null;
				switch (floor) {
				case "grass":
					newTile = new GrassTile(tile.getPos(), tile.getGameObject());
					break;
				case "stone":
					newTile = new StoneTile(tile.getPos(), tile.getGameObject());
					break;
				case "water":
					newTile = new WaterTile(tile.getPos(), tile.getGameObject());
					break;
				case "sand":
					newTile = new SandTile(tile.getPos(), tile.getGameObject());
					break;
				}
				renderer.selectTile(newTile);
				board.getLocationById(currentLocation).getTiles()[i][j] = newTile;
			}
			if (tool.equals("Add Game Object")) {
				switch (gameObject) {
				case "tree":
					tile.setGameObject(new Tree());
					break;
				case "wall":
					tile.setGameObject(new Wall());
					break;
				case "chest":
					tile.setGameObject(new Chest());
					break;
				}
				
			}
			update();
		}
	}

	public void setFloorType(String string) {
		this.floor = string;
	}

	public void setTool(String string) {
		this.tool = string;
	}

	public void setObjectType(String string) {
		this.gameObject = string;
	}

	public void clearTile(int i, int j) {
		if (i >= 0 && j >= 0 && i < board.getLocationById(currentLocation).getTiles().length && j < board.getLocationById(currentLocation).getTiles()[0].length) {
			board.getLocationById(currentLocation).getTiles()[i][j].setGameObject(null);
		}
		update();
	}

	public void selectTile(Point selected) {
		renderer.selectTile(new Position((int) selected.getX(), (int) selected.getY()), board.getLocationById(currentLocation));
		update();
	}

	public void selectLocation(GameSystem.Direction dir) {
		renderer.selectLocation(dir);
		update();
	}

	public void clickLocation(Direction dir) {
		if (dir != null) {
			if (board.getLocationById(currentLocation).getNeighbours().get(dir) == null) {
				board.getLocationById(currentLocation).getNeighbours().put(dir, createBlankLocation());
				board.getLocationById(board.getLocationById(currentLocation).getNeighbours().get(dir)).getNeighbours().put(oppositeDir(dir), currentLocation);
				Map<Point, Integer> map = board.mapLocations(currentLocation, 0, 0, new HashMap<Point, Integer>());
				board.linkLocations(map);
			} else {
				currentLocation = board.getLocationById(currentLocation).getNeighbours().get(dir);
			}
		}
		update();
	}

	
	public Map<Point, Location> mergeMaps(Map<Point, Location> map1, Map<Point, Location> map2){
		Map<Point, Location> mergedMap = new HashMap<Point, Location>();
		for(Point p: map1.keySet()){
			mergedMap.put(p, map1.get(p));
		}
		for(Point p: map2.keySet()){
			mergedMap.put(p, map2.get(p));
		}
		return mergedMap;
	}

	public Location getLocationAt(int startingLoc, Direction[] directions) {
		int finalLoc = startingLoc;
		for (Direction d : directions) {
			if (board.getLocationById(finalLoc).getNeighbours().get(d) != null) {
				finalLoc = board.getLocationById(finalLoc).getNeighbours().get(d);
			} else {
				return null;
			}
		}
		return board.getLocationById(finalLoc);
	}

	public Direction oppositeDir(Direction d) {
		if (d == Direction.NORTH) {
			return Direction.SOUTH;
		}
		if (d == Direction.EAST) {
			return Direction.WEST;
		}
		if (d == Direction.WEST) {
			return Direction.EAST;
		}
		if (d == Direction.SOUTH) {
			return Direction.NORTH;
		}
		return null;
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
}