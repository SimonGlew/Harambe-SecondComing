package gameobjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.Board;
import core.GameSystem;
import core.GameSystem.Direction;
import core.Location;
import tile.Tile;
import util.Position;

public class Player extends GameObject {

	private String userName;
	private ArrayList<Item> inventory;
	private int locationID;
	private Position pos;
	private Board board;
	private Direction facing = Direction.SOUTH;

	private int numOfBananas;

	private final String IMG_PRE = "assets/game_objects/player/player";
	private final String IMG_POST = ".png";

	public Player(String name, int locationID, Position pos, Board board) {
		this.locationID = locationID;
		this.inventory = new ArrayList<Item>();
		this.pos = pos;
		this.board = board;
		this.userName = name;
	}

	public String getUserName() {
		return userName;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public Location getLocation() {
		return board.getLocationById(locationID);
	}

	public void setLocation(Location location) {
		this.locationID = location.getId();
	}

	public Tile getTile() {
		return getLocation().getTileAtPosition(pos);
	}

	public void setTile(Tile tile) {
		this.pos = tile.getPos();
	}

	public int getNumOfBananas() {
		return numOfBananas;
	}

	public void increaseBananaCount(int i) {
		numOfBananas += i;
	}

	public boolean pickUpItem(Item item) {
		return inventory.add(item);
	}

	public BufferedImage getImage(Location loc, Position pos) {
		System.out.println("Draw me?!?!");
		String fname = IMG_PRE + facing.toString() + IMG_POST;
		try {
			image = ImageIO.read(new File(fname));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public String toString() {
		return "Player(" + userName + ")";
	}

	public Position getPosition(){
		return pos;
	}
	
	public String toSaveString() {
		return "Player " + userName + "," + locationID + "," + pos.getX() + "," + pos.getY() + "," + facing.toString();
	}
}
