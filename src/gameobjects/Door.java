package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.Position;

public class Door extends GameObject {

	int locationID;
	int code;
	Position doorPosition;

	public Door(int code, int locationid) {
		this.doorPosition = new Position(5, 9);
		this.code = code;
		this.locationID = locationid;
		fname = "assets/game_objects/door/door.png";

	}

	public String getDescription() {
		return "Damian McKenzie is bae";
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
}
