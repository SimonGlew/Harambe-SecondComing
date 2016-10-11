package NPCStrategies;

import core.GameSystem.Direction;
import gameobjects.NPC;

public class CircleStrategy implements NPC.Strategy {
	

	@Override
	public Direction getNextDirection(NPC npc) {
		if(npc.getFacing() == Direction.NORTH){
			return Direction.EAST;
		}
		if(npc.getFacing() == Direction.EAST){
			return Direction.SOUTH;
		}
		if(npc.getFacing() == Direction.SOUTH){
			return Direction.WEST;
		}
		if(npc.getFacing() == Direction.WEST){
			return Direction.NORTH;
		}
		return null;
	}

}
