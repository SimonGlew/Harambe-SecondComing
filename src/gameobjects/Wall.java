package gameobjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.GameSystem.Direction;
import core.Location;
import tile.Tile;
import util.Position;

public class Wall extends GameObject {
	
	final String IMG_PRE = "assets/game_objects/wall/wall";
	final String IMG_POST = ".png";
	
	public Wall(){

	}

	public BufferedImage getImage(Location loc, Position pos, Direction viewing){
		String fname = IMG_PRE;
		Tile n = loc.getTileInDirection(pos, Direction.NORTH);
		if(n != null){
			if(n.getGameObject() instanceof Wall){
				fname += "N";
			}
		}
		Tile e = loc.getTileInDirection(pos, Direction.EAST);
		if(e != null){
			if(e.getGameObject() instanceof Wall){
				fname += "E";
			}
		}
		Tile s = loc.getTileInDirection(pos, Direction.SOUTH);
		if(s != null){
			if(s.getGameObject() instanceof Wall){
				fname += "S";
			}
		}
		
		Tile w = loc.getTileInDirection(pos, Direction.WEST.EAST);
		if(w != null){
			if(w.getGameObject() instanceof Wall){
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
	
	public String getDescription(){
		return "Just a Wall";
	}
	
	public String toString() {
		return "Wall";
	}
}
