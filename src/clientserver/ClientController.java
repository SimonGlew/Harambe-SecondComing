package clientserver;

import java.util.ArrayList;

import clientserver.*;
import core.Board;
import core.GameSystem.Direction;
import gui.GUI;
import gui.GameOver;
import gui.UltimateDijkstras;
import core.Location;
import gameobjects.Player;
import iohandling.BoardWriter;
import items.Item;
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

	public void hideGUI() {
		if (gui != null) {
			gui.hideGUI();
		}
	}

	public String getName() {
		return client.getUsername();
	}

	public int getBananaCount() {
		if(board == null)return 0;
		return board.getPlayer(getName()).getNumOfBananas();
		
	}

	public void rotateLeft() {
		// rotate camera left
		renderer.rotateCounterClockwise();
		drawBoard();
	}

	public void rotateRight() {
		// rotate camera right
		renderer.rotateClockwise();
		drawBoard();
	}

	public void sendMessage(PlayerCommand msg) {
		client.sendMessage(msg);
	}

	public void drawBoard() {
		if (board != null && gui != null) {
			// System.out.println(board.getPlayer(client.getUsername()).getInventory());
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

	public Tile getTile(int x, int y) {
		Position p = renderer.isoToIndex(x, y);
		return board.getPlayer(getName()).getLocation().getTileAtPosition(p);
	}

	public void moveSinglePos(String dir) {
		Direction d = null;
		if (dir.equals("N"))
			d = Direction.NORTH;
		else if (dir.equals("E"))
			d = Direction.EAST;
		else if (dir.equals("S"))
			d = Direction.SOUTH;
		else if (dir.equals("W"))
			d = Direction.WEST;

		Direction temp = Location.getRelativeDirection(d, renderer.viewingDir);

		Location loc = board.getPlayer(getName()).getLocation();
		Tile t = loc.getTileInDirection(board.getPlayer(getName()).getPosition(), temp);

		if (t != null) {
			Direction direction = loc.getDirDijkstras(board.getPlayer(getName()).getTile(), t);
			if (direction != null) {
				String command = "move " + getName() + " " + direction.toString();
				sendMessage(new PlayerCommand(command));
			}
			drawBoard();
		}
	}

	public void moveWithUltimateDijkstras(int x, int y) {
		if (uDijkstras != null)
			uDijkstras.setPath(null);

		if (board != null) {
			Position p = renderer.isoToIndex(x, y);
			Location loc = board.getPlayer(getName()).getLocation();
			Tile t = loc.getTileAtPosition(p);
			renderer.selectTile(t);
			if (t != null) {
				uDijkstras = new UltimateDijkstras(this, board.getPlayer(getName()).getTile(), loc, t, board);
				uDijkstras.createPath();

				uDijkstras.startTimer();
			}
		}
	}

	public ArrayList<Item> getInventory() {
		if (board != null) {
			return board.getPlayer(getName()).getInventory();
		}
		return null;
	}

	public void dropItemPlayer(int index) {
		String name = getName();

		String command = "drop " + name + " " + index;
		sendMessage(new PlayerCommand(command));
	}

	public void useItem(int index) {
		String name = getName();

		String command = "use " + name + " " + index;
		sendMessage(new PlayerCommand(command));
	}

	public void siphonBananaPlayer(int index) {
		String name = getName();

		String command = "siphon " + name + " " + index;
		sendMessage(new PlayerCommand(command));
	}

	public Item getInventoryItem(int i) {
		return board.getPlayer(getName()).getInventory().get(i);
	}

	public void pickupItemPlayer(Tile t) {
		String name = getName();
		if (t.getGameObject() != null) {
			String command = "pickup " + name;
			sendMessage(new PlayerCommand(command));
		}
	}

	public void moveToPos(Tile t) {
		
		Tile from = null;
		if(uDijkstras.oldTile == null){
			from = board.getPlayer(getName()).getTile();
			uDijkstras.oldTile = from;
		}else{
			from = uDijkstras.oldTile;
		}
		
		Direction d = Location.getDirDijkstras(from, t);
		
		if (d != null) {
			String command = "move " + getName() + " " + d.toString();
			sendMessage(new PlayerCommand(command));
		}
		uDijkstras.oldTile = t;
		drawBoard();
	}

	public void showEndGameScreen(String playerName) {
		new GameOver(playerName);
		sendMessage(new PlayerCommand("close"));
	}

	public Player getPlayer() {
		return board.getPlayer(client.getUsername());
	}
}
