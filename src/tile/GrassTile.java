package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class GrassTile extends Tile{

	public GrassTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		try {
			image = ImageIO.read(new File("src/grassTile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
