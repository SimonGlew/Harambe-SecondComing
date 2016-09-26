package core;

import java.util.ArrayList;

import gameobjects.Player;
import iohandling.BoardCreator;
import tile.Tile;
import util.Position;

public class GameSystem {

	private ArrayList<Player> players;
	private Board board;

	public enum Direction {
		NORTH, SOUTH, EAST, WEST
	}

	public GameSystem() {
		this.board = BoardCreator.loadBoard("map.txt");
		this.players = new ArrayList<Player>();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Board getBoard() {
		return board;
	}

	public boolean movePlayer(Player p, Direction d) {
		if (p == null)
			return false;

		Location playerLoc = p.getLocation();
		Tile playerTil = p.getTile();
		Position playerPos = playerTil.getPos();

		switch (d) {

		case NORTH:
			Tile t = playerLoc.getTileInDirection(playerPos, Direction.NORTH);

			return true;

		case EAST:

		case WEST:

		case SOUTH:

			return false;

		}
		return false;
	}
}