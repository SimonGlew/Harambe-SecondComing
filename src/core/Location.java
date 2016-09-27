package core;

import java.util.HashMap;
import java.util.Map;

import core.GameSystem.Direction;
import tile.Tile;
import util.Position;

public class Location {
	private Tile[][] tiles;
	private int id;
	private String name;
	private Board board;
	private Map<GameSystem.Direction, Integer> neighbours;

	public Location(int id, String name, Tile[][] tiles, Board board) {
		this.tiles = tiles;
		this.name = name;
		this.id = id;
		this.neighbours = new HashMap<GameSystem.Direction, Integer>();
		this.board = board;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public String getName() {
		return name;
	}

	public Map<GameSystem.Direction, Integer> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(Map<GameSystem.Direction, Integer> neighbours) {
		this.neighbours = neighbours;
	}

	public int getId() {
		return id;
	}

	public Board getBoard() {
		return board;
	}

	public Tile getTileInDirection(Position pos, Direction d) {
		Position p = null;
		if (d == Direction.NORTH) {
			p = new Position(pos.getX(), pos.getY() - 1);
			if (withinBounds(p)) {
				return getTiles()[p.getX()][p.getY()];
			}
			Location nextLoc = board.getLocationById(getNeighbours().get(Direction.NORTH));
			if (nextLoc != null) {
				return nextLoc.getTiles()[p.getX()][nextLoc.getTiles()[0].length - 1];
			}
		}
		if (d == Direction.SOUTH) {
			p = new Position(pos.getX(), pos.getY() + 1);
			if (withinBounds(p)) {
				return getTiles()[p.getX()][p.getY()];
			}
			Location nextLoc = board.getLocationById(getNeighbours().get(Direction.SOUTH));
			if (nextLoc != null) {
				return nextLoc.getTiles()[p.getX()][0];
			}
		}
		if (d == Direction.EAST) {
			p = new Position(pos.getX() + 1, pos.getY());
			if (withinBounds(p)) {
				return getTiles()[p.getX()][p.getY()];
			}
		
			board.getLocationById(null);
			Location nextLoc = board.getLocationById(getNeighbours().get(Direction.EAST));
			if (nextLoc != null) {
				return nextLoc.getTiles()[0][p.getY()];
			}
		}
		if (d == Direction.WEST) {
			p = new Position(pos.getX() - 1, pos.getY());
			if (withinBounds(p)) {
				return getTiles()[p.getX()][p.getY()];
			}
			Location nextLoc = board.getLocationById(getNeighbours().get(Direction.WEST));
			if (nextLoc != null) {
				return nextLoc.getTiles()[nextLoc.getTiles().length - 1][p.getY()];
			}
		}
		return null;
	}

	public boolean withinBounds(Position pos) {
		return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < getTiles().length
				&& pos.getY() < getTiles()[0].length;
	}

}
