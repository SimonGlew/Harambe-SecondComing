package core;

import java.awt.Point;
import java.util.Map;

import core.GameSystem.Direction;

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
	
	public Location getLocationById(Integer id){
		return locations.get(id);
	}
	
	public void addLocation(Integer id, Location location){
		locations.put(id, location);
	}
	
	public int getNextUniqueId(){
		int i = 0;
		while(locations.get(i) != null){
			i++;
		}
		return i;
	}
	
	public void linkLocations(Map<Point, Integer> map){
		for(Point point: map.keySet()){
			for(Point point2: map.keySet()){
				if(point.x == point2.x){
					//NORTH OR SOUTH?
					if(point.y + 1 == point2.y){
						//NORTH
						getLocationById(map.get(point)).getNeighbours().put(Direction.NORTH, map.get(point2));
					}
					if(point.y - 1 == point2.y){
						//SOUTH
						getLocationById(map.get(point)).getNeighbours().put(Direction.SOUTH, map.get(point2));
					}
				}
				if(point.y == point2.y){
					//WEST OR EAST?
					if(point.x + 1 == point2.x){
						//EAST
						getLocationById(map.get(point)).getNeighbours().put(Direction.EAST, map.get(point2));
					}
					if(point.x - 1 == point2.x){
						//WEST
						getLocationById(map.get(point)).getNeighbours().put(Direction.WEST, map.get(point2));
					}
				}
			}
		}
	}
	
	public Map<Point, Integer> mapLocations(int id, int x, int y, Map<Point, Integer> map){
		Location loc = getLocationById(id);
		if(map.containsKey(new Point(x, y))){
			return map;
		}
		map.put(new Point(x, y), loc.getId());
		if(loc.getNeighbours().keySet().contains(Direction.SOUTH)){
			if(!map.entrySet().contains(loc.getNeighbours().get(Direction.SOUTH)))
			mapLocations(loc.getNeighbours().get(Direction.SOUTH), x, y - 1, map);
		}
		if(loc.getNeighbours().keySet().contains(Direction.WEST)){
			if(!map.entrySet().contains(loc.getNeighbours().get(Direction.WEST)))
			mapLocations(loc.getNeighbours().get(Direction.WEST), x - 1, y, map);
		}
		if(loc.getNeighbours().keySet().contains(Direction.NORTH)){
			if(!map.entrySet().contains(loc.getNeighbours().get(Direction.NORTH)))
			mapLocations(loc.getNeighbours().get(Direction.NORTH), x, y + 1, map);
		}
		if(loc.getNeighbours().keySet().contains(Direction.EAST)){
			if(!map.entrySet().contains(loc.getNeighbours().get(Direction.EAST)))
			mapLocations(loc.getNeighbours().get(Direction.EAST), x + 1, y, map);
		}
		return map;
	}
}
