package items;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FloatingDevice extends Item {

	public FloatingDevice(String name) {
		super(name, "A floating device, perhaps this will help you swim.. not like you're a penguin or anything", true);
		fname = "assets/game_objects/floatingDevice/floatingDevice.png";
	}
	
	public String toString(){
		return "FloatingDevice";
	}

}
