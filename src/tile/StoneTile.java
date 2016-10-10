package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class StoneTile extends Tile {

	public StoneTile(Position pos, GameObject gameObject) {
		super(pos, gameObject);
		fname = "assets/tiles/stoneTile.png";
	}

	public String toString() {
		String s = "Stone";
		if(gameObject!=null){
			s+= "(" + gameObject.toString() + ")";
		}
		return s;
	}
}
