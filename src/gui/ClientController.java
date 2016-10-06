package gui;

import clientserver.*;
import core.Board;
import core.GameSystem.Direction;
import core.Location;
import renderer.Renderer;
import tile.Tile;
import util.Position;

public class ClientController {
	Client client;
	GUI gui;
	Renderer renderer;
	Board board;
	int time;
	UltimateDijkstras uDijkstras;

	public ClientController(Client c) {
		this.client = c;
		renderer = new Renderer();
		uDijkstras = null;
		drawBoard();
	}

	public void showGUI() {
		gui = new GUI(this);	
	}

	public void hideGUI(){
		if(gui != null){
			gui.hideGUI();
		}
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
		if (board != null && gui != null) {
			gui.showBoard(renderer.paintBoard(board, board.getPlayer(client.getUsername()), 1000, 800));
		}
	}

	public void sendBoard(Board board, int time) {
		this.board = board;
		this.time = time;

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

	public void moveWithUltimateDijkstras(int x, int y){
		if(uDijkstras != null) uDijkstras.path = null;

		if (board != null) {
			Position p = renderer.isoToIndex(x, y);
			Location loc = board.getPlayer(getName()).getLocation();
			Tile t = loc.getTileAtPosition(p);
			if(t != null){
				uDijkstras = new UltimateDijkstras(this, board.getPlayer(getName()).getTile(), loc, t, board);
				uDijkstras.createPath();
				uDijkstras.startTimer();
			}
		}
	}


	public void moveToPos(Tile t){
		Location loc = board.getPlayer(getName()).getLocation();
		Direction d = loc.getDirDijkstras(board.getPlayer(getName()).getTile(), t);
		if(d != null){
			String command = "move " + getName() + " " + d.toString();
			sendMessage(new PlayerCommand(command));
		}
		drawBoard();
	}
}
