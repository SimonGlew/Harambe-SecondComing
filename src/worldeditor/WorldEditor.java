package worldeditor;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import core.GameSystem;
import core.GameSystem.Direction;
import core.Location;
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

	EditorFrame frame;
	ToolSelectionFrame toolSelect;
	Location loc;
	Renderer renderer;

	String tool = "none";
	String floor = "grass";
	private String gameObject = "tree";

	public WorldEditor() {
		renderer = new Renderer();
		loc = createBlankLocation();
		frame = new EditorFrame(this);
		toolSelect = new ToolSelectionFrame(this);
		update();
	}

	public Location createBlankLocation() {
		Location loc = new Location(null, new Tile[10][10]);
		for (int i = 0; i < loc.getTiles().length; i++) {
			for (int j = 0; j < loc.getTiles()[0].length; j++) {
				loc.getTiles()[i][j] = new GrassTile(new Position(i, j), null);
			}
		}
		return loc;
	}

	public void update() {
		if (loc != null) {
			frame.setImage(renderer.paintLocation(loc, frame.panel.getWidth(), frame.panel.getHeight()));
		}
	}

	public static void main(String[] args) {
		new WorldEditor();
	}

	public void processTile(int i, int j) {
		if (i >= 0 && j >= 0 && i < loc.getTiles().length && j < loc.getTiles()[0].length) {
			Tile tile = loc.getTiles()[i][j];
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
				loc.getTiles()[i][j] = newTile;
			}
			if (tool.equals("Add Game Object")) {
				switch (gameObject) {
				case "tree":
					tile.setGameObject(new Tree());
					break;
				case "wall":
					tile.setGameObject(new Wall());
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
		if (i >= 0 && j >= 0 && i < loc.getTiles().length && j < loc.getTiles()[0].length) {
			loc.getTiles()[i][j].setGameObject(null);
		}
		update();
	}

	public void selectTile(Point selected) {
		renderer.selectTile(new Position((int) selected.getX(), (int) selected.getY()), loc);
		update();
	}

	public void selectLocation(GameSystem.Direction dir) {
		renderer.selectLocation(dir);
		update();
	}

	public void clickLocation(Direction dir) {
		if (dir != null) {
			if (loc.getNeighbours().get(dir) == null) {
				loc.getNeighbours().put(dir, createBlankLocation());
				loc.getNeighbours().get(dir).getNeighbours().put(oppositeDir(dir), loc);
				Map<Point, Location> map = renderer.mapLocations(loc, 0, 0, new HashMap<Point, Location>());
				renderer.linkLocations(map);
			} else {
				loc = loc.getNeighbours().get(dir);
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

	public Location getLocationAt(Location startingLoc, Direction[] directions) {
		Location finalLoc = startingLoc;
		for (Direction d : directions) {
			if (finalLoc.getNeighbours().get(d) != null) {
				finalLoc = finalLoc.getNeighbours().get(d);
			} else {
				return null;
			}
		}
		return finalLoc;
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