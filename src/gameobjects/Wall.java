package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends GameObject {
	
	public Wall(){
		try {
			image = ImageIO.read(new File("src/wall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
