package core;

import java.util.Map;

public class Board {
	private Map <Integer, Location> locations;
	
	public Board(Map <Integer, Location> locations){
		this.locations = locations;
	}

	public Map<Integer, Location> getLocations() {
		return locations;
	}

	public void setLocations(Map<Integer, Location> locations) {
		this.locations = locations;
	}
	
	public Location getLocationById(int id){
		return locations.get(id);
	}
	
	public int getNextUniqueId(){
		int i = 0;
		while(locations.get(i) != null){
			i++;
		}
		return i;
	}
	
	
}
