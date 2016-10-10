package gameobjects;

import java.awt.image.BufferedImage;

import core.GameSystem.Direction;
import core.Location;
import util.Position;

public abstract class GameObject {
	protected String fname;
	private String description;

	public String getImage(Location loc, Position pos, Direction viewingDir){
		return fname;
	}
	
	public String getDescription(){
		return description;
	}
}
