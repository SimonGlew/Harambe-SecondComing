package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Building extends GameObject{

	public Building(){
		try {
			image = ImageIO.read(new File("assets/game_objects/building/building.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getDescription(){
		return "Just a building";
	}
	
	public String toString() {
		return "Building";
	}
}
