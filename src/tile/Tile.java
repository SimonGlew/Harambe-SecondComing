package tile;

import java.awt.image.BufferedImage;

import core.GameSystem.Direction;
import gameobjects.GameObject;
import util.Position;

public abstract class Tile {

	protected Position pos;
	protected GameObject gameObject;
	protected String fname;
	protected int locationID;
	
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
	
	public String getImage(Direction viewing){
		return fname;
	}
	
	public int getLocationID(){
		return locationID;
	}
	
	public void setLocationID(int i){
		locationID = i;
	}
	
}
