package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import items.Item;

public class Chest extends GameObject{
	
	private int code;
	private Item contents;
	
	public Chest(){
		try {
			image = ImageIO.read(new File("assets/game_objects/chest/chest.png"));
		} catch (IOException e) {
			System.out.println("Could not load Chest image");
			e.printStackTrace();
		}
		this.contents = null;
	}
	
	public Chest(Item contents){
		try {
			image = ImageIO.read(new File("assets/game_objects/chest/chest.png"));
		} catch (IOException e) {
			System.out.println("Could not load Chest image");
			e.printStackTrace();
		}
		this.contents = contents;
	}	
	
	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public Item getContents() {
		return contents;
	}



	public void setContents(Item contents) {
		this.contents = contents;
	}



	public String getDescription(){
		return "Just a Chest";
	}
	
	public String toString() {
		return "Chest";
	}
}
