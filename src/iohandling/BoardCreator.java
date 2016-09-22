package iohandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import core.Location;
import gameobjects.Wall;
import tile.Tile;
import util.Position;

public class BoardCreator {

	private Scanner sc;

	public BoardCreator(String fileName) {
		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

//	public Location[] loadBoard() {
//		ArrayList<Location> locations = new ArrayList<Location>();
//
//		while (sc.hasNext()) {
//			Tile[][] tiles = new Tile[10][10];
//			int count = 0;
//			while (count < 100) {
//				for (int y = 0; y < 10; y++) {
//					for (int x = 0; x < 10; x++) {
//						while (count > 0) {
//							if (sc.next().equals("w")) {
//								tiles[x][y] = new Tile(new Position(x,y),new Wall(), "grass");
//							}
//						}
//					}
//				}
//			}
//			locations.add(new Location("tralala", tiles));
//		}
//		
//		return (Location[]) locations.toArray();
//	}
}
