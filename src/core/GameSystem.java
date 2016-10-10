package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clientserver.ServerController;
import gameobjects.Chest;
import gameobjects.Door;
import gameobjects.GameObject;
import gameobjects.Player;
import iohandling.BoardParser;
import items.Banana;
import items.FloatingDevice;
import items.Item;
import items.Key;
import items.Teleporter;
import tile.DoorOutTile;
import tile.Tile;
import tile.WaterTile;
import util.Position;

public class GameSystem {

	private Board board;
	
	public ServerController serverController;

	public final Integer WINNING_BANANA_COUNT = 5;
	public final Integer PLAYER_KEY_LIMIT = 3;

	public enum Direction {
		NORTH, SOUTH, EAST, WEST
	}

	public GameSystem(ServerController serverController) {
		this.serverController = serverController;
		this.board = BoardParser.parseBoardFName("map-new.txt");
		generateCodes();
	}

	public Board getBoard() {
		return board;
	}

	public void generateCodes() {
		ArrayList<Chest> chests = new ArrayList<Chest>();
		ArrayList<Key> keys = new ArrayList<Key>();

		for (Location location : board.getLocations().values()) {
			for (Tile ta[] : location.getTiles()) {
				for (Tile t : ta) {
					if (t.getGameObject() instanceof Chest) {
						chests.add((Chest) t.getGameObject());
					} else if (t.getGameObject() instanceof Key) {
						keys.add((Key) t.getGameObject());
					}
				}
			}
		}

		if (keys.size() != chests.size()) {
			throw new RuntimeException("must have same numer of keys and chests");
		}

		int num = keys.size();
		for (int i = 0; i < num; i++) {
			int randy = (int) (Math.random() * keys.size());
			int orton = (int) (Math.random() * keys.size());

			keys.get(randy).setCode(i);
			chests.get(orton).setCode(i);
			chests.get(orton).setContents(new Banana("Banana"));
			keys.remove(randy);
			chests.remove(orton);
		}

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
				if (newTile instanceof WaterTile) {
					if (!p.getHasFloatingDevice()) {
						return false;
					}
				}
				if (newTile instanceof DoorOutTile) {
					DoorOutTile dot = (DoorOutTile) newTile;

					if (board.getLocationById(dot.getOutLocationID()).getTileAtPosition(dot.getDoorPos())
							.getGameObject() != null) {
						return false;
					}
					playerTil.setGameObject(null);

					p.setLocation(dot.getOutLocationID());
					p.setTile(board.getLocationById(dot.getOutLocationID()).getTileAtPosition(dot.getDoorPos()));

					board.getLocationById(dot.getOutLocationID()).getTileAtPosition(dot.getDoorPos()).setGameObject(p);
					p.setFacing(Direction.SOUTH);
					return true;
				}
				playerTil.setGameObject(null);
				newTile.setGameObject(p);
				p.setTile(newTile);
				p.setLocation(board.getLocationById(newTile.getLocationID()));
				return true;
			} else {
				triggerInteraction(p, newTile);
				return true;
			}
		}

		return false;
	}

	public void triggerInteraction(Player p, Tile newTile) {
		Tile playerTil = p.getTile();
		GameObject object = newTile.getGameObject();
		if (object instanceof Item) {
			if (!p.inventoryIsFull()) {
				if (object instanceof Key) {
					int keyCount = 0;
					for (Item i : p.getInventory()) {
						if (i instanceof Key) {
							keyCount++;
						}
					}

					if (keyCount >= PLAYER_KEY_LIMIT) {
						return;
					}
				}
				playerTil.setGameObject(null);
				p.pickUpItem((Item) object);
				newTile.setGameObject(p);
				p.setTile(newTile);
				p.setLocation(board.getLocationById(newTile.getLocationID()));
			}
		} else if (object instanceof Chest) {
			Chest c = (Chest) object;
			for (Item i : p.getInventory()) {
				if (i instanceof Key) {
					Key k = ((Key) i);
					if (k.getCode() == c.getCode()) {
						if (!p.inventoryIsFull() && c.getContents() != null) {
							p.pickUpItem(c.getContents());
							c.setContents(null);
							p.getInventory().remove(i);
							return;
						}
					}
				}
			}
		

		} else if (object instanceof Door) {
			Door door = (Door) object;
			p.getTile().setGameObject(null);
			p.setLocation(door.getLocationID());
			p.setTile(p.getLocation().getTileAtPosition(door.getDoorPosition()));
			p.getTile().setGameObject(p);
		}
	}

	public void playerDropItem(Player p, Item i) {
		Tile tileInFront = p.getLocation().getTileInDirection(p.getPosition(), p.getFacing());

		if (tileInFront.getGameObject() == null) {
			tileInFront.setGameObject(i);
			p.getInventory().remove(i);
		}

	}

	public boolean playerSiphonBanana(Player p, Banana b) {
		if (p != null && b != null) {
			p.setNumOfBananas(p.getNumOfBananas() + 1);
			p.getInventory().remove(b);
			
			serverController.broadcastMessage(p.getUserName() + " has siphoned "+ p.getNumOfBananas() + " banana/s, step it up soldier!");
			return p.getNumOfBananas() == WINNING_BANANA_COUNT;
		}
		return false;
	}

	public void playerUseItem(Player player, Item item) {
		if (item instanceof FloatingDevice) {
			player.setHasFloatingDevice(!player.getHasFloatingDevice());
		} else if (item instanceof Teleporter) {
			player.getTile().setGameObject(null);
			player.setLocation(0);
			Tile t;
			if (!(player.getLocation().getTileAtPosition(new Position(5, 5)).getGameObject() instanceof Player)) {
				player.setTile(player.getLocation().getTileAtPosition(new Position(5,5)));
				player.getTile().setGameObject(player);
			}
			else if (!(player.getLocation().getTileAtPosition(new Position(4, 5)).getGameObject() instanceof Player)) {
				player.setTile(player.getLocation().getTileAtPosition(new Position(4,5)));
				player.getTile().setGameObject(player);
			}
			else if (!(player.getLocation().getTileAtPosition(new Position(4, 4)).getGameObject() instanceof Player)) {
				player.setTile(player.getLocation().getTileAtPosition(new Position(4,4)));
				player.getTile().setGameObject(player);
			}
			else if (!(player.getLocation().getTileAtPosition(new Position(5, 4)).getGameObject() instanceof Player)) {
				player.setTile(player.getLocation().getTileAtPosition(new Position(5,4)));
				player.getTile().setGameObject(player);
			}
			player.getInventory().remove(item);

		}
	}

}