package core;

import gameobjects.GameObject;
import util.Position;

public class Tile {

	private Position pos;
	private GameObject gameObject;
	private String floorType;

	public Tile(Position pos, GameObject gameObject, String floorType) {
		this.pos = pos;
		this.gameObject = gameObject;
		this.floorType = floorType;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public String getFloorType() {
		return floorType;
	}

	public void setFloorType(String floorType) {
		this.floorType = floorType;
	}
	
	
}
