package items;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Key extends Item {
	
	int code;
	
	public Key(String name, String description, int code) {
		super(name, description);
		this.code = code;
		try {
			image = ImageIO.read(new File("assets/game_objects/key/key.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String toString(){
		return "Key|"+code;
	}

}
