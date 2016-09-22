package gameobjects;

import java.util.ArrayList;

import core.GameSystem;
import core.GameSystem.Direction;
import core.Location;

public class Player extends GameObject {
	
	private String name;
	
	private ArrayList<Item> inventory;
	
	private GameSystem system;
	
	private Location location;
	
	public Player(String name, Location location, GameSystem system){
		this.location = location;
		this.system = system;
		this.inventory = new ArrayList<Item>();
	}

	public String getName() {
		return name;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
	public void move(Direction d){
		system.movePlayer(this,d);		
	}
}
