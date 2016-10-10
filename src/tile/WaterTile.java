package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class WaterTile extends Tile {

	public WaterTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		fname = "assets/tiles/waterTile.png";
	}

	public String toString() {
		String s = "Water";
		if(gameObject!=null){
			s+= "(" + gameObject.toString() + ")";
		}
		return s;
	}
}
