package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class GrassTile extends Tile {

	public GrassTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		try {
			double d = Math.random() * 100;
			if (d > 25) {
				image = ImageIO.read(new File("assets/tiles/grassTile.png"));
			} else {
				image = ImageIO.read(new File("assets/tiles/flowerTile.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "G";
	}

}
