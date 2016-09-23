package gameobjects;

import java.awt.image.BufferedImage;

import tile.Tile;

public abstract class GameObject {
	protected BufferedImage image;

	public BufferedImage getImage(){
		return image;
	}
}
