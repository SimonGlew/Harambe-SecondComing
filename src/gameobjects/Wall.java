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
	
	final String IMG_PRE = "src/wall/wall";
	final String IMG_POST = ".png";
	
	public Wall(){
		try {
			image = ImageIO.read(new File("src/wall/wall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage(Location loc, Position pos){
		String fname = IMG_PRE;
		Tile n = Location.getTileInDirection(loc, pos, Direction.NORTH);
		if(n != null){
			if(n.getGameObject() instanceof Wall){
				fname += "N";
			}
		}
		Tile e = Location.getTileInDirection(loc, pos, Direction.EAST);
		if(e != null){
			if(e.getGameObject() instanceof Wall){
				fname += "E";
			}
		}
		Tile s = Location.getTileInDirection(loc, pos, Direction.SOUTH);
		if(s != null){
			if(s.getGameObject() instanceof Wall){
				fname += "S";
			}
		}
		
		Tile w = Location.getTileInDirection(loc, pos, Direction.WEST);
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
}
