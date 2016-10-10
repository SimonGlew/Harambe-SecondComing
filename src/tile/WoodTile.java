package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class WoodTile extends Tile{

	public WoodTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		fname = "assets/tiles/woodTile.png";
	}
	
	public String toString() {
		String s = "Wood";
		if(gameObject!=null){
			s+= "(" + gameObject.toString() + ")";
		}
		return s;
	}

}
