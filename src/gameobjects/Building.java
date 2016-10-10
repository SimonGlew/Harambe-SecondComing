package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Building extends GameObject {

	public Building() {
		fname = "assets/game_objects/building/building.png";
	}

	public String getDescription() {
		return "Just a building";
	}

	public String toString() {
		return "Building";
	}
}
