package tile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.GameSystem.Direction;
import gameobjects.GameObject;
import util.Position;

public class DoorOutTile extends Tile {

	int LocationID;

	int outLocationID;

	Position doorPos;

	final String IMG_PRE = "assets/tiles/doorOut/doorOut";
	final String IMG_POST = ".png";

	public DoorOutTile(Position pos, GameObject gameObject, int locationID, Position doorPos) {
		super(pos, gameObject);
		
		this.locationID = this.locationID;
		this.outLocationID = locationID;
		this.doorPos = doorPos;
		fname = "assets/tiles/doorOut/doorOutNORTH.png";
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

	public String getImage(Direction viewing) {
		fname = IMG_PRE + viewing.toString() + IMG_POST;
		return fname;
	}
}
