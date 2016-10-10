package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tree extends GameObject{

	public Tree(){
			fname = "assets/game_objects/tree/tree.png";
	}
	
	public String getDescription(){
		return "Just a Tree";
	}
	
	public String toString() {
		return "Tree";
	}
}
