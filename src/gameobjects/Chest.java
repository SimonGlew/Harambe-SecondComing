package gameobjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import items.Item;

public class Chest extends GameObject {

	private int code;
	private Item contents;

	public Chest() {
		fname = "assets/game_objects/chest/chest.png";
		this.contents = null;
	}

	public Chest(Item contents) {

		fname = "assets/game_objects/chest/chest.png";

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

	public String getDescription() {
		return "A locked Chest, " + "Code: " + code;
	}

	public String toString() {
		String s = "Chest(" + code;
		if (contents != null) {
			s += "," + contents.toString();
		}
		s += ")";
		return s;
	}
}
