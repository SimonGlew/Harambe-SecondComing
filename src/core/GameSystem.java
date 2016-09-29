package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gameobjects.Item;
import gameobjects.Player;
import iohandling.BoardCreator;
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
			
		case EAST:
			newTile = playerLoc.getTileInDirection(playerPos, Direction.EAST);
			
		case WEST:
			newTile = playerLoc.getTileInDirection(playerPos, Direction.WEST);

		case SOUTH:
			newTile = playerLoc.getTileInDirection(playerPos, Direction.SOUTH);

		}
		
		if(newTile != null){
			if(newTile.getGameObject() == null){
				playerTil.setGameObject(null);
				newTile.setGameObject(p);
				p.setTile(newTile);
				return true;
			}
			else if(newTile.getGameObject() instanceof Item){
				playerTil.setGameObject(null);
				p.pickUpItem((Item)newTile.getGameObject());
				newTile.setGameObject(p);
				p.setTile(newTile);
				return true;
			}
		}
		
		return false;
	}
	
}