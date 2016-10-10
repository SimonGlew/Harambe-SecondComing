package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class DoorOutTile extends Tile {
	
	int LocationID;
	Position doorPos;

	public DoorOutTile(Position pos, GameObject gameObject, int locationID, Position doorPos) {
		super(pos, gameObject);
		this.locationID = this.locationID;
		this.doorPos = doorPos;
		try {
			double d = Math.random() * 100;
			if (d >= 0) {
				image = ImageIO.read(new File("assets/tiles/doorOutTile.png"));
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		String s = "DoorOut(" + locationID + "," + doorPos.getX() + "," + doorPos.getY() + ")";
		if(gameObject!=null){
			s+= "(" + gameObject.toString() + ")";
		}
		return s;
	}

}
