package core;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import core.GameSystem.Direction;
import gameobjects.Player;

public class Board {
	private Map<String, Player> players;
	private Map<Integer, Location> locations;

	public Board() {
		this.locations = new HashMap<Integer, Location>();
		this.players = new HashMap<String, Player>();
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

	public Map<Point, Integer> mapLocations(int id, int x, int y, Map<Point, Integer> map) {
		Location loc = getLocationById(id);
		if (map.containsKey(new Point(x, y))) {
			return map;
		}
		map.put(new Point(x, y), id);
		for (Direction d : Direction.values()) {
			if (loc.getNeighbours().keySet().contains(d)) {
				if (!map.entrySet().contains(loc.getNeighbours().get(d))) {
					Point offset = getOffset(d);
					mapLocations(loc.getNeighbours().get(d), x + offset.x, y + offset.y, map);
				}
			}
		}
		return map;
	}

	public Point getOffset(Direction d) {
		switch (d) {
		case NORTH:
			return new Point(0, 1);
		case SOUTH:
			return new Point(0, -1);
		case EAST:
			return new Point(1, 0);
		case WEST:
			return new Point(-1, 0);
		}
		return null;
	}

	public void addPlayer(String userName, Player player) {
		players.put(userName, player);
	}

	public Player getPlayer(String username) {
		return players.get(username);
	}

	public Map<String, Player> getPlayers() {
		return players;
	}
}
