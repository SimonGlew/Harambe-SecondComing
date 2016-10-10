package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class SandTile extends Tile {

	public SandTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		fname = "assets/tiles/sandTile.png";
	}

	public String toString() {
		String s = "Sand";
		if (gameObject != null) {
			s += "(" + gameObject.toString() + ")";
		}
		return s;
	}

}
