package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class FlowerTile extends Tile{
	public FlowerTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		try {
			image = ImageIO.read(new File("src/flowerTile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "G";
	}
}
