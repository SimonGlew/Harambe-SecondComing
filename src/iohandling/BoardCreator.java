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
import core.GameSystem.Direction;
import core.Location;
import gameobjects.Chest;
import gameobjects.GameObject;
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
			while(scan.hasNext()){
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
				if(tilesplit.length > 1){
					String object = tilesplit[1];
					switch (object){
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
				
				switch (tilesplit[0]){
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
		Location loc = new Location(id, name, tiles, board);
		loc.setNeighbours(neighbours);
		return loc;
	}

}
