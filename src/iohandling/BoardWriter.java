package iohandling;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import core.Board;
import core.GameSystem.Direction;
import core.Location;

public class BoardWriter {

	public static void writeBoard(Board b, String fname) {
		try {
			PrintWriter print = new PrintWriter(new File(fname));
			for (Location loc : b.getLocations().values()) {
				print.println("Location");
				print.println("id:" + loc.getId());
				print.println("name:" + loc.getName());
				print.println("w:" + loc.getTiles().length);
				print.println("h:" + loc.getTiles()[0].length);
				for (Direction dir : loc.getNeighbours().keySet()) {
					print.println(dir + ":" + loc.getNeighbours().get(dir));
				}
				for (int i = 0; i < loc.getTiles().length; i++) {
					for (int j = 0; j < loc.getTiles()[0].length; j++) {
						print.print(loc.getTiles()[j][i].toString());
						if (loc.getTiles()[j][i].getGameObject() != null) {
							print.print(":");
							print.print(loc.getTiles()[j][i].getGameObject().toString());
						}
						print.print(",");

					}
					print.println("\n");
				}
			}
			print.close();
		} catch (IOException e) {

		}
	}

	public static String writeBoardToString(Board b) {

		StringBuilder print = new StringBuilder();
		for (Location loc : b.getLocations().values()) {
			print.append("Location" + "\n");
			print.append("id:" + loc.getId() + "\n");
			print.append("name:" + loc.getName() + "\n");
			print.append("w:" + loc.getTiles().length + "\n");
			print.append("h:" + loc.getTiles()[0].length + "\n");
			for (Direction dir : loc.getNeighbours().keySet()) {
				print.append(dir + ":" + loc.getNeighbours().get(dir) + "\n");
			}
			for (int i = 0; i < loc.getTiles().length; i++) {
				for (int j = 0; j < loc.getTiles()[0].length; j++) {
					print.append(loc.getTiles()[j][i].toString());
					if (loc.getTiles()[j][i].getGameObject() != null) {
						print.append(":");
						print.append(loc.getTiles()[j][i].getGameObject().toString());
					}
					print.append(",");

				}
				print.append("\n");
			}
		}
		return print.toString();

	}
}
