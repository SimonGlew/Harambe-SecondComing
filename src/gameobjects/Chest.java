package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Chest extends GameObject{
	
	public Chest(){
		try {
			image = ImageIO.read(new File("src/chest.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
