package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class WoodTile extends Tile{

	public WoodTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		try {
			image = ImageIO.read(new File("assets/tiles/woodTile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return "Wood";
	}

}
