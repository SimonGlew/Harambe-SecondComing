package core;

import java.util.HashMap;
import java.util.Map;

import core.GameSystem.Direction;
import tile.Tile;
import util.Position;

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

	public Map<GameSystem.Direction, Location> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(Map<GameSystem.Direction, Location> neighbours) {
		this.neighbours = neighbours;
	}	
	
	public static Tile getTileInDirection(Location loc, Position pos, Direction d){
		Position p = null;
		if(d == Direction.NORTH){
			p = new Position(pos.getX(), pos.getY()-1);
			if(withinBounds(loc, p)){
				return loc.getTiles()[p.getX()][p.getY()];
			}
			Location nextLoc = loc.getNeighbours().get(Direction.NORTH);
			if(nextLoc != null){
				return nextLoc.getTiles()[p.getX()][nextLoc.getTiles()[0].length - 1];
			}
		}
		if(d == Direction.SOUTH){
			p = new Position(pos.getX(), pos.getY()+1);
			if(withinBounds(loc, p)){
				return loc.getTiles()[p.getX()][p.getY()];
			}
			Location nextLoc = loc.getNeighbours().get(Direction.SOUTH);
			if(nextLoc != null){
				return nextLoc.getTiles()[p.getX()][0];
			}
		}
		if(d == Direction.EAST){
			p = new Position(pos.getX() + 1, pos.getY());
			if(withinBounds(loc, p)){
				return loc.getTiles()[p.getX()][p.getY()];
			}
			Location nextLoc = loc.getNeighbours().get(Direction.EAST);
			if(nextLoc != null){
				return nextLoc.getTiles()[0][p.getY()];
			}
		}
		if(d == Direction.WEST){
			p = new Position(pos.getX() - 1, pos.getY());
			if(withinBounds(loc, p)){
				return loc.getTiles()[p.getX()][p.getY()];
			}
			Location nextLoc = loc.getNeighbours().get(Direction.WEST);
			if(nextLoc != null){
				return nextLoc.getTiles()[nextLoc.getTiles().length - 1][p.getY()];
			}
		}
		return null;
	}
	
	public static boolean withinBounds(Location loc, Position pos){
		return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < loc.getTiles().length && pos.getY() < loc.getTiles()[0].length;
	}

	
}
