package gameobjects;

import java.awt.image.BufferedImage;

import tile.Tile;

public abstract class GameObject {
	protected Tile tile;
	protected BufferedImage image;

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

}
