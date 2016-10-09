package iohandling;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import core.Board;
import core.GameSystem.Direction;
import core.Location;
import gameobjects.Player;

public class BoardWriter {

	public static void writeBoard(Board b, String fname) {
		try {
			PrintWriter print = new PrintWriter(new File(fname));
			for (Player player : b.getPlayers().values()) {
				System.out.println(player.toSaveString());
				print.println(player.toSaveString());
			}
			for (Location loc : b.getLocations().values()) {
				print.println("Location{");
				print.println("id: " + loc.getId());
				print.println("name: ?");// + loc.getName());
				print.println("w: " + loc.getTiles().length);
				print.println("h: " + loc.getTiles()[0].length);
				if(loc.getNeighbours().get(Direction.NORTH) != null){
					print.println("NORTH: " + loc.getNeighbours().get(Direction.NORTH));
				}
				if(loc.getNeighbours().get(Direction.EAST) != null){
					print.println("EAST: " + loc.getNeighbours().get(Direction.EAST));
				}
				if(loc.getNeighbours().get(Direction.WEST) != null){
					print.println("WEST: " + loc.getNeighbours().get(Direction.WEST));
				}
				if(loc.getNeighbours().get(Direction.SOUTH) != null){
					print.println("SOUTH: " + loc.getNeighbours().get(Direction.SOUTH));
				}
				for (int i = 0; i < loc.getTiles().length; i++) {
					for (int j = 0; j < loc.getTiles()[0].length; j++) {
						print.print("(");
						print.print(loc.getTiles()[j][i].toString());
						print.print(")");
					}
					print.print("\n");
				}
				print.println("}");
			}
			print.close();
		} catch (IOException e) {

		}
	}

	public static String writeBoardToString(Board b) {

		StringBuilder print = new StringBuilder();
		for (Player player : b.getPlayers().values()) {
			print.append(player.toSaveString());
			print.append("\n");
		}
		
		for (Location loc : b.getLocations().values()) {
			print.append("Location{" + "\n");
			print.append("id: " + loc.getId() + "\n");
			print.append("name: ?" + "\n");
			print.append("w: " + loc.getTiles().length + "\n");
			print.append("h: " + loc.getTiles()[0].length + "\n");
			if(loc.getNeighbours().get(Direction.NORTH) != null){
				print.append("NORTH: " + loc.getNeighbours().get(Direction.NORTH) + "\n");
			}
			if(loc.getNeighbours().get(Direction.EAST) != null){
				print.append("EAST: " + loc.getNeighbours().get(Direction.EAST) + "\n");
			}
			if(loc.getNeighbours().get(Direction.WEST) != null){
				print.append("WEST: " + loc.getNeighbours().get(Direction.WEST) + "\n");
			}
			if(loc.getNeighbours().get(Direction.SOUTH) != null){
				print.append("SOUTH: " + loc.getNeighbours().get(Direction.SOUTH) + "\n");
			}
			for (int i = 0; i < loc.getTiles().length; i++) {
				for (int j = 0; j < loc.getTiles()[0].length; j++) {
					print.append("(");
					print.append(loc.getTiles()[j][i].toString());
					print.append(")");
				}
				print.append("\n");
			}
			print.append("}\n");

		}

		return print.toString();

	}
}
