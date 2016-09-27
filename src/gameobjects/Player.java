package gameobjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.GameSystem;
import core.GameSystem.Direction;
import core.Location;
import tile.Tile;
import util.Position;

public class Player extends GameObject {
	
	private String name;	
	private ArrayList<Item> inventory;		
	private Location location;	
	private Tile tile;
	private Direction facing = Direction.NORTH;
	
	private int numOfBananas;
	
	private final String IMG_PRE = "assets/game_objects/player/player";
	private final String IMG_POST = ".png";
	
	public Player(String name, Location location, Tile tile){
		this.location = location;
		this.inventory = new ArrayList<Item>();
		this.tile = tile;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public int getNumOfBananas() {
		return numOfBananas;
	}
	
	public void increaseBananaCount(int i){
		numOfBananas += i;
	}
	
	public boolean pickUpItem(Item item){
		return inventory.add(item);	
	}
	
	public BufferedImage getImage(Location loc, Position pos){
		String fname = IMG_PRE + facing.toString() + IMG_POST;
		try {
			image = ImageIO.read(new File(fname));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
