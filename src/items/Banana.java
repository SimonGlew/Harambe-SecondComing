package items;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Banana extends Item{
	
	public static String siphonMessage = "You siphon the power from the radiating member, Haramabe is within your grasp! Carry on Soldier!";

	public Banana(String name) {
		super(name, "A sentient being taking form in a material object, behold the force of thy Harambe!" ,false);
			fname = "assets/game_objects/banana/banana.png";

	}
	
	public String toString(){
		
		return "Banana";
	}
}
