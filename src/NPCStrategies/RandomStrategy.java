package NPCStrategies;

import core.GameSystem;
import core.GameSystem.Direction;
import gameobjects.NPC;

public class RandomStrategy implements NPC.Strategy{
	
	@Override
	public Direction getNextDirection(NPC npc) {
		int randy = (int)(Math.random()*4);
		
		if(randy == 0){
			return GameSystem.Direction.NORTH;
		}
		else if(randy == 1){
			return GameSystem.Direction.SOUTH;
		}
		else if(randy == 2){
			return GameSystem.Direction.EAST;
		}
		else {
			return GameSystem.Direction.WEST;
		}
	}


}
