package gameobjects;

import java.awt.image.BufferedImage;

import core.Location;
import tile.Tile;
import util.Position;

public abstract class GameObject {
	protected BufferedImage image;

	public BufferedImage getImage(Location loc, Position pos){
		return image;
	}
}
