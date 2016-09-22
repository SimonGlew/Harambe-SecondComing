package core;

import java.util.ArrayList;

import gameobjects.Player;
import iohandling.BoardCreator;

public class GameSystem {

	private Location[] board;
	private ArrayList<Player> players;
	
	private BoardCreator boardCreator;

	public enum Direction {
		NORTH, SOUTH, EAST, WEST
	}

	public GameSystem() {
		this.boardCreator = new BoardCreator("lalala");
//		this.board = boardCreator.loadBoard();
		this.players = new ArrayList<Player>();
	}

	public void movePlayer(Player p, Direction d) {
	}
}