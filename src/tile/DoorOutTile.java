package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class DoorOutTile extends Tile {

	int outLocationID;
	Position doorPos;

	public DoorOutTile(Position pos, GameObject gameObject, int locationID, Position doorPos) {
		super(pos, gameObject);
		this.outLocationID = locationID;
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

	public int getOutLocationID() {
		return outLocationID;
	}

	public Position getDoorPos() {
		return doorPos;
	}

	public String toString() {
		String s = "DoorOut(" + outLocationID + "," + doorPos.getX() + "," + doorPos.getY() + ")";
		if (gameObject != null) {
			s += "(" + gameObject.toString() + ")";
		}
		return s;
	}

}
