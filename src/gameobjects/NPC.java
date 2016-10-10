package gameobjects;

import NPCStrategies.RandomStrategy;
import core.GameSystem;
import core.Location;
import core.GameSystem.Direction;
import tile.WaterTile;
import util.Position;

public class NPC extends GameObject {

	String strategyType;
	Direction facing = Direction.NORTH;
	NPC.Strategy strategy;
	
	private final String IMG_PRE = "assets/game_objects/npc/npc";
	private final String IMG_POST = ".png";

	public NPC(String s, Direction d) {
		this.strategyType = s;
		facing = d;
		this.strategy = new RandomStrategy();
	}
	
	public GameSystem.Direction getDirection(){
		return strategy.getNextDirection();
	}
	

	public Direction getFacing() {
		return facing;
	}

	public void setFacing(Direction facing) {
		this.facing = facing;
	}

	public String toString() {
		return "NPC(" + strategyType + "," + facing.toString() + ")";
	}

	
	public String getImage(Location loc, Position pos, Direction viewingDir) {
		String fname = IMG_PRE;
		fname += Location.getOtherRelativeDirection(facing, viewingDir).toString() + IMG_POST;

		return fname;
	}
	
	public interface Strategy {		
		public GameSystem.Direction getNextDirection();
	}
}


