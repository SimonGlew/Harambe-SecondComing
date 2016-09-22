package worldeditor;

import core.Location;
import core.Tile;
import renderer.Renderer;
import util.Position;

public class WorldEditor {

	EditorFrame frame;
	ToolSelectionFrame toolSelect;
	Location loc;
	Renderer renderer;

	String tool = "setFloor";
	String floor = "grass";

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
				loc.getTiles()[i][j] = new Tile(new Position(i, j), null, "grass");
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
			if (tool.equals("setFloor")) {
				tile.setFloorType(floor);
			}
			update();
		}
	}

	public void setFloorType(String string) {
		this.floor = string;
	}
}
