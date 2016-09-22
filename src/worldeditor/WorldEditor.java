package worldeditor;

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
		loc = createBlankLocation();
		frame = new EditorFrame(this);
		toolSelect = new ToolSelectionFrame(this);
		renderer = new Renderer();
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
}