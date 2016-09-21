package core;

import util.Position;

public class Board {

	private Tile[][] board;

	private final int BOARD_WIDTH = 50;
	private final int BOARD_HEIGHT = 50;

	public Board() {
		board = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
	}

	public Tile getTile(Position pos) {
		return board[pos.getX()][pos.getY()];
	}

	public void setTile(Position pos, Tile tile) {
		board[pos.getX()][pos.getY()] = tile;
	}
	
}
