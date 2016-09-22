package core;

public class Location {
	private Tile[][] tiles;
	private String name;
	
	public Location(String name, Tile[][] tiles){
		this.tiles = tiles;
		this.name = name;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public String getName() {
		return name;
	}	
	
}
