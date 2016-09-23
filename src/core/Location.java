package core;

import java.util.HashMap;
import java.util.Map;

import tile.Tile;

public class Location {
	private Tile[][] tiles;
	private String name;
	private Map <GameSystem.Direction, Location> neighbours;
	
	public Location(String name, Tile[][] tiles){
		this.tiles = tiles;
		this.name = name;
		this.neighbours = new HashMap<GameSystem.Direction, Location>();
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public String getName() {
		return name;
	}	
	
	public Map<GameSystem.Direction, Location> getNeighbours(){
		return neighbours;
	}
	
}
