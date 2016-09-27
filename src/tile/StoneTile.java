package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class StoneTile extends Tile {

	public StoneTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		try {
			image = ImageIO.read(new File("assets/tiles/stoneTile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "P";
	}
}
