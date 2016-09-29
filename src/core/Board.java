package core;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import core.GameSystem.Direction;
import gameobjects.GameObject;
import gameobjects.Player;

public class Board {
	private Map<String, Player> players;
	private Map<Integer, Location> locations;

	public Board(Map<Integer, Location> locations) {
		this.locations = locations;
		players = new HashMap<String, Player>();
	}

	public Map<Integer, Location> getLocations() {
		return locations;
	}

	public void setLocations(Map<Integer, Location> locations) {
		this.locations = locations;
	}

	public Location getLocationById(Integer id) {
		return locations.get(id);
	}

	public void addLocation(Integer id, Location location) {
		locations.put(id, location);
	}

	public int getNextUniqueId() {
		int i = 0;
		while (locations.get(i) != null) {
			i++;
		}
		return i;
	}

	public void linkLocations(Map<Point, Integer> map) {
		for (Point point : map.keySet()) {
			for (Point point2 : map.keySet()) {
				if (point.x == point2.x) {
					// NORTH OR SOUTH?
					if (point.y + 1 == point2.y) {
						// NORTH
						getLocationById(map.get(point)).getNeighbours().put(Direction.NORTH, map.get(point2));
					}
					if (point.y - 1 == point2.y) {
						// SOUTH
						getLocationById(map.get(point)).getNeighbours().put(Direction.SOUTH, map.get(point2));
					}
				}
				if (point.y == point2.y) {
					// WEST OR EAST?
					if (point.x + 1 == point2.x) {
						// EAST
						getLocationById(map.get(point)).getNeighbours().put(Direction.EAST, map.get(point2));
					}
					if (point.x - 1 == point2.x) {
						// WEST
						getLocationById(map.get(point)).getNeighbours().put(Direction.WEST, map.get(point2));
					}
				}
			}
		}
	}

	public Map<Point, Integer> mapLocations(int id, int x, int y, Map<Point, Integer> map, Direction viewingDir) {
		Location loc = getLocationById(id);
		
		if (map.containsKey(new Point(x, y))) {
			//System.out.println("gtfo");

			return map;
		}
		//System.out.println(x +"," + y);
//		System.out.println(id);
//		System.out.println(loc.getNeighbours().get(Direction.SOUTH));
		map.put(new Point(x, y), loc.getId());
		/*for (Direction d : GameSystem.Direction.values()) {
			if (loc.getNeighbours().keySet().contains(d)) {
				if (!map.entrySet().contains(loc.getNeighbours().get(d))) {
					Point offset = getOffset(d, viewingDir, x, y);
//					System.out.println(d);
					mapLocations(loc.getNeighbours().get(d), offset.x, offset.y, map, viewingDir);
				}
			}
		}*/
		return map;
	}

	public Point getOffset(Direction d, Direction viewingDirection, int x, int y){
		Direction relative = Location.getRelativeDirection(d, viewingDirection);
		System.out.println(d + " ----- " + relative);
		relative = Location.oppositeDir(relative);
		switch(relative){
			case NORTH:
				return new Point(x, y + 1);
			case SOUTH:
				return new Point(x, y - 1);
			case EAST:
				return new Point(x + 1, y);
			case WEST:
				return new Point(x - 1, y);
		}
		return null;
	}

	public void addPlayer(String userName, Player player) {
		players.put(userName, player);
	}

	public Player getPlayer(String username) {
		return players.get(username);
	}
}
