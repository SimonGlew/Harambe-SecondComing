package tile;

import gameobjects.GameObject;
import util.Position;

public abstract class Tile {

	private Position pos;
	private GameObject gameObject;
	private String floorType;

	public Tile(Position pos, GameObject gameObject) {
		this.pos = pos;
		this.gameObject = gameObject;
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

	
	
}
