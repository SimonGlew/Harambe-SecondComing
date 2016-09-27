package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tree extends GameObject{

	public Tree(){
		try {
			image = ImageIO.read(new File("assets/game_objects/tree/tree.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "Tree";
	}
}
