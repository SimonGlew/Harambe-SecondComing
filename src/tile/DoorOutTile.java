package tile;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameobjects.GameObject;
import util.Position;

public class DoorOutTile extends Tile {
<<<<<<< HEAD
	
	int LocationID;
=======

	int outLocationID;
>>>>>>> e57948c0e3f0b516ba21b59543b98eee52e9b050
	Position doorPos;

	public DoorOutTile(Position pos, GameObject gameObject, int locationID, Position doorPos) {
		super(pos, gameObject);
<<<<<<< HEAD
		this.locationID = this.locationID;
=======
		this.outLocationID = locationID;
>>>>>>> e57948c0e3f0b516ba21b59543b98eee52e9b050
		this.doorPos = doorPos;
		try {
			double d = Math.random() * 100;
			if (d >= 0) {
				image = ImageIO.read(new File("assets/tiles/doorOutTile.png"));
<<<<<<< HEAD
			} 
=======
			}
>>>>>>> e57948c0e3f0b516ba21b59543b98eee52e9b050
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
	public String toString() {
		String s = "DoorOut(" + locationID + "," + doorPos.getX() + "," + doorPos.getY() + ")";
		if(gameObject!=null){
			s+= "(" + gameObject.toString() + ")";
=======
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
>>>>>>> e57948c0e3f0b516ba21b59543b98eee52e9b050
		}
		return s;
	}

}
