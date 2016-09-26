package core;

import java.util.Map;

public class Board {
	private Map <String, Location> locations;
	
	public Board(Map <String, Location> locations){
		this.locations = locations;
	}

	public Map<String, Location> getLocations() {
		return locations;
	}

	public void setLocations(Map<String, Location> locations) {
		this.locations = locations;
	}
	
	
}
