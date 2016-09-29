package iohandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import core.Board;
import core.GameSystem;
import core.GameSystem.Direction;
import core.Location;
import gameobjects.Chest;
import gameobjects.GameObject;
import gameobjects.Player;
import gameobjects.Tree;
import gameobjects.Wall;
import gameobjects.Fence;
import tile.GrassTile;
import tile.SandTile;
import tile.StoneTile;
import tile.Tile;
import tile.WaterTile;
import tile.WoodTile;
import util.Position;

public class BoardCreator {

	public static Board loadBoard(String fname) {
		Board board = new Board(new HashMap<Integer, Location>());
		try {
			Scanner scan = new Scanner(new File(fname));
			while (scan.hasNext()) {
				if (scan.hasNext("Player")) {
					Player player = parsePlayer(scan, board);
					board.addPlayer(player.getUserName(), player);
				}
				if (scan.hasNext("Location")) {
					Location loc = parseLocation(scan, board);
					board.addLocation(loc.getId(), loc);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return board;
	}

	private static Player parsePlayer(Scanner scan, Board board) {
		scan.next();
		String line = scan.nextLine().trim();
		String[] split = line.split(",");
		String username = split[0];
		int locationID = Integer.parseInt(split[1]);
		Position pos = new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
		Direction d = parseDirection(split[4]);
		boolean b = Boolean.parseBoolean(split[5]);
		Player player = new Player(username, locationID, pos, board);
		player.setLoggedIn(b);
		player.setFacing(d);
		return player;
	}

	private static Location parseLocation(Scanner scan, Board board) {
		scan.next();
		String idLine = scan.next();
		int id = Integer.parseInt(idLine.split(":")[1]);
		String nameLine = scan.next();
		String name = nameLine.substring(5);
		String wLine = scan.next();
		int w = Integer.parseInt(wLine.split(":")[1]);

		String hLine = scan.next();
		int h = Integer.parseInt(hLine.split(":")[1]);

		Map<Direction, Integer> neighbours = new HashMap<Direction, Integer>();
		String nextLine;
		while (true) {
			nextLine = scan.next();
			if (nextLine.startsWith("NORTH")) {
				neighbours.put(Direction.NORTH, Integer.parseInt(nextLine.split(":")[1]));
			} else if (nextLine.startsWith("SOUTH")) {
				neighbours.put(Direction.SOUTH, Integer.parseInt(nextLine.split(":")[1]));
			} else if (nextLine.startsWith("EAST")) {
				neighbours.put(Direction.EAST, Integer.parseInt(nextLine.split(":")[1]));
			} else if (nextLine.startsWith("WEST")) {
				neighbours.put(Direction.WEST, Integer.parseInt(nextLine.split(":")[1]));
			} else {
				break;
			}
		}

		Tile[][] tiles = new Tile[w][h];
		for (int j = 0; j < h; j++) {
			if (j > 0) {
				nextLine = scan.next();
			}
			String[] split = nextLine.split(",");
			for (int i = 0; i < w; i++) {
				String tile = split[i];
				String[] tilesplit = tile.split(":");
				GameObject gameObject = null;
				if (tilesplit.length > 1) {
					String object = tilesplit[1];
					if (object.startsWith("Player|")) {
						String username = object.substring(7);
						gameObject = board.getPlayer(username);
						board.getPlayer(username).setPosition(new Position(i, j));
						if(!board.getPlayer(username).isLoggedIn()){
							gameObject = null;
						}
					} else {
						switch (object) {
						case "Tree":
							gameObject = new Tree();
							break;
						case "Chest":
							gameObject = new Chest();
							break;
						case "Fence":
							gameObject = new Fence();
							break;
						case "Wall":
							gameObject = new Wall();
							break;
						}
					}
				}

				switch (tilesplit[0]) {
				case "Grass":
					tiles[i][j] = new GrassTile(new Position(i, j), gameObject);
					break;
				case "Sand":
					tiles[i][j] = new SandTile(new Position(i, j), gameObject);
					break;
				case "Stone":
					tiles[i][j] = new StoneTile(new Position(i, j), gameObject);
					break;
				case "Water":
					tiles[i][j] = new WaterTile(new Position(i, j), gameObject);
					break;
				case "Wood":
					tiles[i][j] = new WoodTile(new Position(i, j), gameObject);
					break;
				}
			}
		}
		for(int i = 0; i < w; i++){
			for(int j = 0; j < h; j++){
				tiles[i][j].setLocationID(id);
			}
		}
		Location loc = new Location(id, name, tiles, board);
		loc.setNeighbours(neighbours);
		return loc;
	}

	public static Direction parseDirection(String s) {
		switch (s) {
		case "NORTH":
			return Direction.NORTH;
		case "EAST":
			return Direction.EAST;
		case "SOUTH":
			return Direction.SOUTH;
		case "WEST":
			return Direction.WEST;
		}
		return null;

	}

	public static Board loadBoardFromString(String s) {
		Board board = new Board(new HashMap<Integer, Location>());

		Scanner scan = new Scanner(s);
		while (scan.hasNext()) {
			if (scan.hasNext("Player")) {
				Player player = parsePlayer(scan, board);
				board.addPlayer(player.getUserName(), player);
			}
			if (scan.hasNext("Location")) {
				Location loc = parseLocation(scan, board);
				board.addLocation(loc.getId(), loc);
			}
		}

		return board;
	}

}
