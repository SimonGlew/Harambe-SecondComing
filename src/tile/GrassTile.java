package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class GrassTile extends Tile {

	public GrassTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		fname = "assets/tiles/grassTile.png";
	}

	public String toString() {
		String s = "Grass";
		if (gameObject != null) {
			s += "(" + gameObject.toString() + ")";
		}
		return s;
	}

}
