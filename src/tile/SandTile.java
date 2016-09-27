package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class SandTile extends Tile{

	public SandTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		try {
			image = ImageIO.read(new File("assets/tiles/sandTile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return "S";
	}

}
