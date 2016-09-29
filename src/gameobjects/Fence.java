package gameobjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.GameSystem.Direction;
import core.Location;
import tile.Tile;
import util.Position;

public class Fence extends GameObject {
	
	final String IMG_PRE = "assets/game_objects/fence/fence";
	final String IMG_POST = ".png";
	
	public Fence(){

	}

	public BufferedImage getImage(Location loc, Position pos, Direction viewing){
		
		String fname = IMG_PRE;

		Tile n = loc.getTileInDirection(pos, Location.getRelativeDirection(Direction.NORTH, viewing));
		if(n != null){
			if(n.getGameObject() instanceof Fence){
				fname += "N";
			}
		}
		Tile e = loc.getTileInDirection(pos, Location.getRelativeDirection(Direction.EAST, viewing));
		if(e != null){
			if(e.getGameObject() instanceof Fence){
				fname += "E";
			}
		}
		Tile s = loc.getTileInDirection(pos, Location.getRelativeDirection(Direction.SOUTH, viewing));
		if(s != null){
			if(s.getGameObject() instanceof Fence){
				fname += "S";
			}
		}
		
		Tile w = loc.getTileInDirection(pos, Location.getRelativeDirection(Direction.WEST, viewing));
		if(w != null){
			if(w.getGameObject() instanceof Fence){
				fname += "W";
			}
		}
		fname+=IMG_POST;
		try {
			image = ImageIO.read(new File(fname));
		} catch (IOException error) {
			error.printStackTrace();
		}
		return image;
	}
	
	public String toString() {
		return "Fence";
	}
}
