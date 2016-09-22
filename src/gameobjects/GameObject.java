package gameObjects;

import core.Tile;

public abstract class GameObject {
	protected Tile tile;

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

}
