package clientserver;

import java.util.Scanner;

import core.Board;
import core.GameSystem;
import core.GameSystem.Direction;
import exceptions.ParserException;
import gameobjects.Player;

public class ServerController {
	private GameSystem gameSystem;
	private Server server;

	public ServerController(Server server, GameSystem gameSystem) {
		this.server = server;
		this.gameSystem = gameSystem;
	}
	
	public Board requestBoard(){
		return gameSystem.getBoard();
	}

	public boolean parseInput(PlayerCommand message) {
		Scanner s = new Scanner(message.getMessage());

		if (s.hasNext()) {
			if (s.next().equals("move")) {
				return parseMoveCommand(s);
			}else if(s.next().equals("login")){
				return parseLoginCommand(s);
			}

			s.close();
			return false;
		}

		s.close();
		return false;
	}
	
	public boolean parseLoginCommand(Scanner s){
		try{
			if(getPlayerByUserName(s.next()) != null){
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			return false;
		}
	}

	public boolean parseMoveCommand(Scanner s){
		try{
		 Player player = getPlayerByUserName(s.next());
		 Direction direction = convertToDirection(s.next());
		 
		 if(direction == null)return false;
		 if(player == null)return false;		 
		 		 
		 gameSystem.movePlayer(player, direction);
		 return true;
		 
		}catch(Exception e){
			return false;
		}
	}
	
	public Direction convertToDirection(String s){
		if(s.toLowerCase().equals("north")){
			return Direction.NORTH;
		}
		if(s.toLowerCase().equals("south")){
			return Direction.SOUTH;
		}
		if(s.toLowerCase().equals("east")){
			return Direction.EAST;
		}
		if(s.toLowerCase().equals("west")){
			return Direction.WEST;
		}
		return null;
	}
	
	public Player getPlayerByUserName(String name){
		for(String s : gameSystem.getPlayers().keySet()){
			if(s.equals(name)){
				return gameSystem.getPlayers().get(s);
			}
		}
		return null;
	}
}
