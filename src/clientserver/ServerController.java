package clientserver;

import java.util.Scanner;

import core.Board;
import core.GameSystem;
import core.GameSystem.Direction;
import exceptions.ParserException;
import gameobjects.Player;
import util.Position;

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

	public String parseInput(PlayerCommand message) {
		Scanner s = new Scanner(message.getMessage());

		if (s.hasNext()) {
			String action = s.next();
			if (action.equals("move")) {
				return parseMoveCommand(s);
			}else if(action.equals("login")){
				return parseLoginCommand(s);
			}

			s.close();
			return "false";
		}

		s.close();
		return "false";
	}
	
	public String parseLoginCommand(Scanner s){
		try{
			String name = s.next();
			Player p = gameSystem.getBoard().getPlayer(name);
			
			if(p != null && p.isLoggedIn()){
				return "fail login";
			}else if(p != null && !p.isLoggedIn()){
				gameSystem.getBoard().getLocationById(p.getLocation().getId()).getTileAtPosition(p.getPosition()).setGameObject(p);
				p.setLoggedIn(true);
				return "true";
			}
			else{
				p = new Player(name,0, new Position(5,5), gameSystem.getBoard());
				gameSystem.getBoard().addPlayer(name, p);
				gameSystem.getBoard().getLocationById(p.getLocation().getId()).getTileAtPosition(p.getPosition()).setGameObject(p);
				p.setLoggedIn(true);
				return "true";
			}
		}catch(Exception e){
			return "false";
		}
	}

	public String parseMoveCommand(Scanner s){
		try{
		 Player player = getPlayerByUserName(s.next());
		 Direction direction = convertToDirection(s.next());
		 
		 if(direction == null)return "false";
		 if(player == null)return "false";		
		 
		 
		 gameSystem.movePlayer(player, direction);
		 return "true";
		 
		}catch(Exception e){
			return "false";
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
		return gameSystem.getBoard().getPlayer(name);
	}
}
