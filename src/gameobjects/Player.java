package gameobjects;

import java.util.ArrayList;

import core.GameSystem;
import core.GameSystem.Direction;
import core.Location;
import tile.Tile;

public class Player extends GameObject {
	
	private String name;
	
	private ArrayList<Item> inventory;
	
	private GameSystem system;
	
	private Location location;
	
	private Tile tile;
	
	public Player(String name, Location location, Tile tile, GameSystem system){
		this.location = location;
		this.system = system;
		this.inventory = new ArrayList<Item>();
		this.tile = tile;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public void move(Direction d){
		system.movePlayer(this,d);		
	}
}
