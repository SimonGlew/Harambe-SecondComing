package gameobjects;

import java.awt.image.BufferedImage;

import core.GameSystem.Direction;
import core.Location;
import util.Position;

public abstract class GameObject {
	protected BufferedImage image;
	private String description;

	public BufferedImage getImage(Location loc, Position pos, Direction viewingDir){
		return image;
	}
	
	public String getDescription(){
		return description;
	}
}
