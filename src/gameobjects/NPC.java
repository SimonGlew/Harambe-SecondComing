package gameobjects;

import core.Location;
import core.GameSystem.Direction;
import util.Position;

public class NPC extends GameObject {

	String strategyType;
	Direction facing = Direction.NORTH;

	public NPC(String s, Direction d) {
		this.strategyType = s;
		facing = d;
	}

	public String toString() {
		return "NPC(" + strategyType + "," + facing.toString() + ")";
	}

	public String getImage(Location loc, Position pos, Direction viewingDir) {
		return "lol";
	}

}
