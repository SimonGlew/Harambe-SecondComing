package iohandling;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import core.Board;
import core.GameSystem.Direction;
import core.Location;
import gameobjects.Building;
import gameobjects.Chest;
import gameobjects.Fence;
import gameobjects.GameObject;
import gameobjects.Player;
import gameobjects.Tree;
import gameobjects.Wall;
import items.Item;
import items.Key;
import tile.GrassTile;
import tile.SandTile;
import tile.StoneTile;
import tile.Tile;
import tile.WaterTile;
import tile.WoodTile;
import util.Position;

public class BoardParser {

	public static Board parseBoardFName(String fname) {
		try {
			Scanner s = new Scanner(new File(fname));
			return parseBoard(s);
		} catch (IOException e) {
			System.out.println("Unable to load file " + fname);
			return null;
		}
	}

	public static Board parseBoardString(String boardString) {
		return parseBoard(new Scanner(boardString));
	}

	private static Board parseBoard(Scanner s) {
		s.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");
		Board board = new Board();
		// Parse Players
		while (checkFor("Player", s)) {
			Player player = parsePlayer(s, board);
			board.addPlayer(player.getUserName(), player);
		}

		// Parse locations
		while (checkFor("Location", s)) {
			Location location = parseLocation(s, board);
			board.addLocation(location.getId(), location);
		}
		return board;
	}

	private static Player parsePlayer(Scanner s, Board board) {
		require("\\{", s);
		String username = s.next().trim();
		require(",", s);
		int locationID = s.nextInt();
		require(",", s);
		int posX = s.nextInt();
		require(",", s);
		int posY = s.nextInt();
		require(",", s);
		Direction facing = parseDirection(s);
		require(",", s);
		boolean loggedIn = s.nextBoolean();

		Player player = new Player(username, locationID, new Position(posX, posY), board);
		player.setFacing(facing);
		player.setLoggedIn(loggedIn);

		require(",", s);
		require("Inventory", s);
		require("\\(", s);
		while (!checkFor("\\)", s)) {
			player.pickUpItem(parseItem(s));
			require(",", s);
		}
		require("\\}", s);

		return player;
	}

	private static Direction parseDirection(Scanner s) {
		if (checkFor("NORTH", s)) {
			return Direction.NORTH;
		} else if (checkFor("WEST", s)) {
			return Direction.WEST;
		} else if (checkFor("SOUTH", s)) {
			return Direction.SOUTH;
		} else if (checkFor("EAST", s)) {
			return Direction.EAST;
		} else {
			fail("Not a valid direction", s);
		}
		return null;
	}

	private static Location parseLocation(Scanner s, Board board) {
		require("\\{", s);
		// Parse ID
		require("id:", s);
		int id = s.nextInt();

		// Parse Name
		require("name:", s);
		String name = s.next();

		// Parse Width and Height
		require("w:", s);
		int w = s.nextInt();
		require("h:", s);
		int h = s.nextInt();

		// Parse Neighbours
		Map<Direction, Integer> neighbours = new HashMap<Direction, Integer>();
		if (checkFor("NORTH:", s)) {
			neighbours.put(Direction.NORTH, s.nextInt());
		}
		if (checkFor("EAST:", s)) {
			neighbours.put(Direction.EAST, s.nextInt());
		}
		if (checkFor("WEST:", s)) {
			neighbours.put(Direction.WEST, s.nextInt());
		}
		if (checkFor("SOUTH:", s)) {
			neighbours.put(Direction.SOUTH, s.nextInt());
		}

		// Parse Tiles
		Tile[][] tiles = new Tile[w][h];
		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {
				Tile tile = parseTile(s, i, j, board);
				tile.setLocationID(id);
				tiles[i][j] = tile;
			}
		}
		require("\\}", s);
		Location location = new Location(id, name, tiles, board);
		location.setNeighbours(neighbours);
		return location;
	}

	private static Tile parseTile(Scanner s, int i, int j, Board board) {
		require("\\(", s);
		Tile tile = null;

		// Parse tile type
		if (checkFor("Grass", s)) {
			tile = new GrassTile(new Position(i, j), null);
		} else if (checkFor("Grass", s)) {
			tile = new GrassTile(new Position(i, j), null);
		} else if (checkFor("Sand", s)) {
			tile = new SandTile(new Position(i, j), null);
		} else if (checkFor("Stone", s)) {
			tile = new StoneTile(new Position(i, j), null);
		} else if (checkFor("Wood", s)) {
			tile = new WoodTile(new Position(i, j), null);
		} else if (checkFor("Water", s)) {
			tile = new WaterTile(new Position(i, j), null);
		} else {
			fail("Not a valid tile type", s);
		}

		// Parse game object
		if (checkFor("\\(", s)) {
			tile.setGameObject(parseGameObject(s, board));
			require("\\)", s);
		}

		require("\\)", s);
		return tile;
	}

	private static GameObject parseGameObject(Scanner s, Board board) {
		if (checkFor("Tree", s)) {
			return new Tree();
		} else if (checkFor("Fence", s)) {
			return new Fence();
		} else if (checkFor("Wall", s)) {
			return new Wall();
		} else if (checkFor("Player", s)) {
			return parsePlayerOnBoard(s, board);
		} else if (checkFor("Building", s)) {
			return new Building();
		} else if (checkFor("Chest", s)) {
			return parseChest(s, board);
		} else if (checkFor("Key", s)) {
			return parseKey(s);
		} else {
			fail("Not a GameObject", s);
		}
		return null;
	}

	private static Player parsePlayerOnBoard(Scanner s, Board board) {
		require("\\(", s);
		String name = s.next();
		require("\\)", s);
		return board.getPlayer(name);
	}

	private static Chest parseChest(Scanner s, Board board) {
		Chest chest = new Chest();
		if (checkFor("\\(", s)) {
			chest.setContents(parseItem(s));
			require("\\)", s);
		}
		return chest;
	}

	private static Item parseItem(Scanner s) {
		if (checkFor("Key", s)) {
			return parseKey(s);
		} else {
			fail("Not an Item", s);
		}
		return null;
	}

	private static Key parseKey(Scanner s) {
		require("\\(", s);
		String name = s.next();
		require(",", s);
		int code = s.nextInt();
		require("\\)", s);
		return new Key(name, code);
	}

	private static String require(String p, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(s.next() + "Did not match " + p, s);
		return null;
	}

	static boolean checkFor(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Report a failure in the parser.
	 */
	private static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg);
	}
}

@SuppressWarnings("serial")
class ParserFailureException extends RuntimeException {
	public ParserFailureException(String msg) {
		super(msg);
	}
}
