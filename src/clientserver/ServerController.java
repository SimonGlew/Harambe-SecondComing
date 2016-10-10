package clientserver;

import java.util.Scanner;

import core.Board;
import core.GameSystem;
import core.GameSystem.Direction;
import gameobjects.Player;
import items.Banana;
import items.Item;
import tile.Tile;
import util.Position;

public class ServerController {
	private GameSystem gameSystem;
	private Server server;

	public ServerController(Server server) {
		this.server = server;
		this.gameSystem = new GameSystem(this);
	}

	public Board requestBoard() {
		return gameSystem.getBoard();
	}

	public String parseInput(PlayerCommand message) {
		Scanner s = new Scanner(message.getMessage());

		if (s.hasNext()) {
			String action = s.next();
			if (action.equals("move")) {
				return parseMoveCommand(s);
			} else if (action.equals("login")) {
				return parseLoginCommand(s);
			} else if (action.equals("drop")) {
				return parseDropItemCommand(s);
			} else if (action.equals("siphon")) {
				return parseSiphonBananaCommand(s);
			} else if (action.equals("use")) {
				return parseUseItemCommand(s);
			} else if (action.equals("pickup")) {
				return parsePickupItemCommand(s);
			}

			s.close();
			return "false";
		}

		s.close();
		return "false";
	}

	public String parsePickupItemCommand(Scanner s) {
		try {
			Player player = getPlayerByUserName(s.next());

			Tile t = gameSystem.getBoard().getLocationById(player.getLocation().getId())
					.getTileAtPosition(player.getPosition());

			if (t.getGameObject() == null) {
				return "false";
			}
			if (!(t.getGameObject() instanceof Item)) {
				return "false";
			}
			Item i = (Item) t.getGameObject();

			player.getInventory().add(i);
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	public String parseLoginCommand(Scanner s) {
		try {
			String name = s.next();
			Player p = gameSystem.getBoard().getPlayer(name);

			if (p != null && p.isLoggedIn()) {
				return "fail login";
			} else if (p != null && !p.isLoggedIn()) {
				gameSystem.getBoard().getLocationById(p.getLocation().getId()).getTileAtPosition(p.getPosition())
						.setGameObject(p);
				p.setLoggedIn(true);
				return "true";
			} else {
				if (!(gameSystem.getBoard().getLocationById(0).getTileAtPosition(new Position(5, 5))
						.getGameObject() instanceof Player)) {
					p = new Player(name, 0, new Position(5, 5), gameSystem.getBoard());
				} else if (!(gameSystem.getBoard().getLocationById(0).getTileAtPosition(new Position(4, 5))
						.getGameObject() instanceof Player)) {
					p = new Player(name, 0, new Position(4, 5), gameSystem.getBoard());
				} else if (!(gameSystem.getBoard().getLocationById(0).getTileAtPosition(new Position(4, 4))
						.getGameObject() instanceof Player)) {
					p = new Player(name, 0, new Position(4, 4), gameSystem.getBoard());
				} else if (!(gameSystem.getBoard().getLocationById(0).getTileAtPosition(new Position(5, 4))
						.getGameObject() instanceof Player)) {
					p = new Player(name, 0, new Position(5, 4), gameSystem.getBoard());
				}
				gameSystem.getBoard().addPlayer(name, p);
				gameSystem.getBoard().getLocationById(p.getLocation().getId()).getTileAtPosition(p.getPosition())
						.setGameObject(p);
				p.setLoggedIn(true);
				return "true";
			}
		} catch (Exception e) {
			return "false";
		}
	}

	public String parseMoveCommand(Scanner s) {
		try {
			Player player = getPlayerByUserName(s.next());
			Direction direction = convertToDirection(s.next());

			if (direction == null)
				return "false";
			if (player == null)
				return "false";

			gameSystem.movePlayer(player, direction);
			return "true";

		} catch (Exception e) {
			return "false";
		}
	}

	public Direction convertToDirection(String s) {
		if (s.toLowerCase().equals("north")) {
			return Direction.NORTH;
		}
		if (s.toLowerCase().equals("south")) {
			return Direction.SOUTH;
		}
		if (s.toLowerCase().equals("east")) {
			return Direction.EAST;
		}
		if (s.toLowerCase().equals("west")) {
			return Direction.WEST;
		}
		return null;
	}

	public String parseDropItemCommand(Scanner s) {
		try {
			Player player = getPlayerByUserName(s.next());
			int indexOfItem = s.nextInt();
			Item item = player.getInventory().get(indexOfItem);

			gameSystem.playerDropItem(player, item);
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	public String parseSiphonBananaCommand(Scanner s) {
		try {
			Player player = getPlayerByUserName(s.next());
			int indexOfItem = s.nextInt();
			Item item = player.getInventory().get(indexOfItem);

			Banana b = (Banana) item;
			if (gameSystem.playerSiphonBanana(player, b)) {
				return "endgame";
			}
			return "true";
		} catch (Exception e) {
			return "false";
		}

	}

	public String parseUseItemCommand(Scanner s) {
		try {
			Player player = getPlayerByUserName(s.next());
			int indexOfItem = s.nextInt();
			Item item = player.getInventory().get(indexOfItem);

			gameSystem.playerUseItem(player, item);
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}
	
	public Player getPlayerByUserName(String name) {
		return gameSystem.getBoard().getPlayer(name);
	}
	
	public void tick(int time){
		gameSystem.tick(time);
	}
	
	public int getServerTime(){
		return server.getServerTime();
	}
	
	public void broadcastPlayerMessage(String message, Player p){
		server.broadcast(new Packet("popup", null ,message, 0), server.getID(p.getUserName()));
	}
	
	public void broadcastGameMessage(String message){
		server.broadcast(new Packet("popup", null ,message, 0), 0);
	} 
}
