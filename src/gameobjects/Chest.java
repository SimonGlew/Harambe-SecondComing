package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Chest extends GameObject{
	
	public Chest(){
		try {
			image = ImageIO.read(new File("assets/game_objects/chest/chest.png"));
		} catch (IOException e) {
			System.out.println("Could not load Chest image");
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "Chest";
	}
}
