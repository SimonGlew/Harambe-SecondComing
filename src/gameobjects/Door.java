package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Location;
import core.GameSystem.Direction;
import util.Position;

public class Door extends GameObject {

	int locationID;
	int code;
	Position doorPosition;

	public Door(int code, int locationid) {
		this.doorPosition = new Position(5, 9);
		this.code = code;
		this.locationID = locationid;
		fname = "assets/game_objects/door/doorNORTH.png";

	}

	public String getDescription() {
		return "This is a door. I wonder what's inside.";
	}

	public Position getDoorPosition() {
		return doorPosition;
	}

	public String toString() {
		return "Door(" + code + "," + locationID + "," + doorPosition.getX() + "," + doorPosition.getY() + ")";
	}

	public int getLocationID() {
		return locationID;
	}

	public void setDoorPosition(Position position) {
		doorPosition = position;
	}
	
	public String getImage(Location loc, Position pos, Direction viewingDir){
		fname = "assets/game_objects/door/door" + viewingDir.toString() + ".png";
		return fname;
	}
}
	
