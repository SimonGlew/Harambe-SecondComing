package core;

import java.util.ArrayList;

import gameobjects.Player;
import iohandling.BoardCreator;

public class GameSystem {

	private ArrayList<Player> players;
	private Board board;
	
	public enum Direction {
		NORTH, SOUTH, EAST, WEST
	}

	public GameSystem() {
		this.board = BoardCreator.loadBoard();
		this.players = new ArrayList<Player>();
	}

	public void movePlayer(Player p, Direction d) {
		
	}
}