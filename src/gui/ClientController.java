package gui;

import java.awt.Point;

import clientserver.*;
import core.Board;
import core.GameSystem.Direction;
import core.Location;
import iohandling.BoardCreator;
import renderer.Renderer;
import tile.Tile;
import util.Position;

public class ClientController {
	Client client;
	GUI gui;
	Renderer renderer;
	Board board;

	public ClientController(Client c) {
		this.client = c;
		gui = new GUI(this);
		renderer = new Renderer();
		drawBoard();
	}

	public void hideGUI() {
		gui.hideGUI();
	}

	public String getName() {
		return client.getUsername();
	}

	public int getBananaCount() {
		// get player banana count
		return 0;
	}

	public void rotateLeft() {
		// rotate camera left
		renderer.rotateClockwise();
		drawBoard();
	}

	public void rotateRight() {
		renderer.rotateCounterClockwise();

		// rotate camera right
		drawBoard();
	}

	public void sendMessage(PlayerCommand msg) {
		client.sendMessage(msg);
	}

	public void drawBoard() {
		if (board != null) {
			gui.showBoard(renderer.paintBoard(board, board.getPlayer(client.getUsername()), 1000, 800));
		}
	}

	public void sendBoard(Board board) {
		this.board = board;
		drawBoard();
	}

	public void selectTile(int x, int y) {
		if (board != null) {
			Position p = renderer.isoToIndex(x, y);
			Tile t = board.getPlayer(getName()).getLocation().getTileAtPosition(p);
			renderer.selectTile(t);
			drawBoard();
		}
	}

	public void moveTo(int x, int y) {
		if (board != null) {
			Position p = renderer.isoToIndex(x, y);
			Location loc = board.getPlayer(getName()).getLocation();
			Tile t = loc.getTileAtPosition(p);
			Direction d = loc.getDirOfTile(board.getPlayer(getName()).getPosition(), t);
			if(d != null){
				System.out.println(d);
				String command = "";
				//SEND MOVE COMMAND HERE
			}
			drawBoard();
		}
	}

}
