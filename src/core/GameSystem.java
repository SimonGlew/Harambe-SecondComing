package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gameobjects.Player;
import iohandling.BoardCreator;
import items.Item;
import tile.Tile;
import util.Position;

public class GameSystem {

	private Board board;

	public enum Direction {
		NORTH, SOUTH, EAST, WEST
	}

	public GameSystem() {
		this.board = BoardCreator.loadBoard("map.txt");
	}

	public Board getBoard() {
		return board;
	}

	public boolean movePlayer(Player p, Direction d) {

		Location playerLoc = p.getLocation();
		Tile playerTil = p.getTile();
		Position playerPos = playerTil.getPos();

		Tile newTile = null;

		switch (d) {

		case NORTH:
			newTile = playerLoc.getTileInDirection(playerPos, Direction.NORTH);
			p.setFacing(Direction.NORTH);
			break;
		case EAST:
			newTile = playerLoc.getTileInDirection(playerPos, Direction.EAST);
			p.setFacing(Direction.EAST);
			break;
		case WEST:
			newTile = playerLoc.getTileInDirection(playerPos, Direction.WEST);
			p.setFacing(Direction.WEST);
			break;
		case SOUTH:
			newTile = playerLoc.getTileInDirection(playerPos, Direction.SOUTH);
			p.setFacing(Direction.SOUTH);
			break;
		}

		if (newTile != null) {
			if (newTile.getGameObject() == null) {
				playerTil.setGameObject(null);
				newTile.setGameObject(p);
				p.setTile(newTile);
				p.setLocation(board.getLocationById(newTile.getLocationID()));
				return true;
			} else if (newTile.getGameObject() instanceof Item) {
				playerTil.setGameObject(null);
				if (!p.inventoryIsFull()) {
					p.pickUpItem((Item) newTile.getGameObject());
					newTile.setGameObject(p);
					p.setTile(newTile);
				}
				return true;
			}
		}

		return false;
	}

}