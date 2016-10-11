package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import clientserver.PlayerCommand;
import clientserver.Server;
import clientserver.ServerController;
import core.Board;
import core.GameSystem.Direction;
import exceptions.GameLogicException;
import gameobjects.Chest;
import gameobjects.NPC;
import gameobjects.Player;
import iohandling.BoardParser;
import items.*;
import util.Position;

public class GameLogicTesting {

	public @Test void makePlayerCorrect(){
		Player p = makePlayerHelper("Simon", 0, new Position(5, 5));
		assertEquals(p.getUserName(), "Simon");
		assertFalse(p.inventoryIsFull());
	}

	public @Test void checkBananaCountGood(){
		Player p = makePlayerHelper("Simon", 0, new Position(5,5));
		p.increaseBananaCount(1);
		assertEquals(p.getNumOfBananas(), 1);
		p.increaseBananaCount(2);
		assertEquals(p.getNumOfBananas(), 3);
	}

	public @Test void checkBananaCountBad1(){
		try{
			Player p = makePlayerHelper("Simon", 0, new Position(5,5));
			p.increaseBananaCount(6);
		}catch(GameLogicException e){
			fail();
		}
	}

	public @Test void checkBananaCountBad2(){
		try{
			Player p = makePlayerHelper("Simon", 0,new Position(5,5));
			p.increaseBananaCount(10);
		}catch(GameLogicException e){
			fail();
		}
	}

	public @Test void checkAmountOfBananasGood(){
		try{
			Player p = makePlayerHelper("Simon", 0, new Position(5,5));
			p.increaseBananaCount(4);
			assertEquals(p.getNumOfBananas(), 4);
			p.increaseBananaCount(2);
		}catch(GameLogicException e){
			fail();
		}
	}

	public @Test void checkMovePos(){
		Player p = makePlayerHelper("Simon", 0, new Position(5,5));
		p.setPosition(new Position(6,6));
		assertEquals(p.getPosition().getX(), 6);
		assertEquals(p.getPosition().getY(), 6);
	}

	public @Test void checkFloatingDevice(){
		Player p = makePlayerHelper("Simon", 0, new Position(5,5));
		assertFalse(p.getHasFloatingDevice());
		p.setHasFloatingDevice(true);
		assertTrue(p.getHasFloatingDevice());
	}

	public @Test void checkFullInventory(){
		Player p = makePlayerHelper("Simon", 0, new Position(5,5));
		for(int i = 0; i < 10; i ++){
			p.pickUpItem(new Fish("Fish"));
		}
		assertTrue(p.inventoryIsFull());
	}

	public @Test void checkUsableItem(){
		Item floaty = new FloatingDevice("float");
		Item banana = new Banana("banana");

		assertTrue(floaty.isUsable());
		assertFalse(banana.isUsable());
	}

	public @Test void checkParsingFalseMovingNoLogin(){
		ServerController s = new ServerController(new Server(1000));
		String parse = s.parseInput(new PlayerCommand("move Simon north"));
		assertEquals(parse, "false");
	}

	public @Test void checkParsingLoginTrue(){
		ServerController s = new ServerController(new Server(1000));
		String parsep1 = s.parseInput(new PlayerCommand("login Simon"));
		assertEquals(parsep1, "true");
		assertFalse(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,5)).getGameObject() == null);
		assertTrue(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(4,5)).getGameObject() == null);
		assertTrue(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,4)).getGameObject() == null);
		assertTrue(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(4,4)).getGameObject() == null);
	}

	public @Test void checkParsingLoginFalseDoubleUsername(){
		ServerController s = new ServerController(new Server(1000));
		String parsep1 = s.parseInput(new PlayerCommand("login Simon"));
		assertEquals(parsep1, "true");
		String parsep2 = s.parseInput(new PlayerCommand("login Simon"));
		assertEquals(parsep2, "fail login");
		String parsep3 = s.parseInput(new PlayerCommand("login Jack"));
		assertEquals(parsep3, "true");
	}

	public @Test void checkParsingLoginFalsePlayerLimit(){
		ServerController s = new ServerController(new Server(1000));
		String parsep1 = s.parseInput(new PlayerCommand("login Simon"));
		assertEquals(parsep1, "true");
		String parsep2 = s.parseInput(new PlayerCommand("login Jack"));
		assertEquals(parsep2, "true");
		String parsep3 = s.parseInput(new PlayerCommand("login Jonathan"));
		assertEquals(parsep3, "true");
		String parsep4 = s.parseInput(new PlayerCommand("login Kyal"));
		assertEquals(parsep4, "true");
		String parsep5 = s.parseInput(new PlayerCommand("login Tutor"));
		assertEquals(parsep5, "fail login");
	}

	public @Test void checkParsingMovingCorrect(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		String parseMove = s.parseInput(new PlayerCommand("move Simon north"));
		assertEquals(parseMove, "true");
		assertTrue(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,5)).getGameObject() == null);
	}

	public @Test void checkParsingMovingFalseOtherPlayer(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		s.parseInput(new PlayerCommand("login Jack"));
		s.parseInput(new PlayerCommand("move Simon west"));
		assertTrue(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,5)).getGameObject() != null);
	}

	public @Test void checkParsingMovingFalseTerrian(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		s.parseInput(new PlayerCommand("move Simon south"));
		s.parseInput(new PlayerCommand("move Simon east"));
		assertTrue(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,6)).getGameObject() != null);
	}

	public @Test void checkDroppingItemCorrect(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		s.getPlayerByUserName("Simon").getInventory().add(new Banana("Banana"));
		assertFalse(s.getPlayerByUserName("Simon").getInventory().isEmpty());
		s.parseInput(new PlayerCommand("drop Simon 0"));
		assertTrue(s.getPlayerByUserName("Simon").getInventory().isEmpty());
		assertTrue(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,6)).getGameObject() != null);
	}

	public @Test void checkDroppingItemFalseObjectInWay(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		s.parseInput(new PlayerCommand("login Jack"));
		s.parseInput(new PlayerCommand("login Jonathan"));
		s.parseInput(new PlayerCommand("login Kyal"));
		s.getPlayerByUserName("Kyal").getInventory().add(new Banana("Banana"));
		assertFalse(s.getPlayerByUserName("Kyal").getInventory().isEmpty());
		s.parseInput(new PlayerCommand("drop Kyal 0"));
		assertFalse(s.getPlayerByUserName("Kyal").getInventory().isEmpty());
	}

	public @Test void checkSiphonBananaCorrect(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		s.getPlayerByUserName("Simon").getInventory().add(new Banana("Banana"));
		s.parseInput(new PlayerCommand("siphon Simon 0"));
		assertTrue(s.getPlayerByUserName("Simon").getInventory().isEmpty());
		assertTrue(s.getPlayerByUserName("Simon").getNumOfBananas() == 1);
	}

	public @Test void checkUseFloatDevice(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		s.getPlayerByUserName("Simon").getInventory().add(new FloatingDevice("floaty"));
		s.parseInput(new PlayerCommand("use Simon 0"));
		assertTrue(s.getPlayerByUserName("Simon").getHasFloatingDevice());
		s.parseInput(new PlayerCommand("use Simon 0"));
		assertFalse(s.getPlayerByUserName("Simon").getHasFloatingDevice());
	}

	public @Test void checkUseTeleporter(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		s.getPlayerByUserName("Simon").getInventory().add(new Teleporter("tele"));
		s.parseInput(new PlayerCommand("move Simon south"));
		s.parseInput(new PlayerCommand("move Simon south"));
		s.parseInput(new PlayerCommand("use Simon 0"));
		assertTrue(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,5)).getGameObject() != null);
		assertTrue(s.getPlayerByUserName("Simon").getInventory().isEmpty());
	}

	public @Test void checkUseFishingRod(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		s.getPlayerByUserName("Simon").getInventory().add(new FishingRod("fishy stick"));
		s.parseInput(new PlayerCommand("use Simon 0"));
		assertFalse(s.getPlayerByUserName("Simon").getInventory().isEmpty());
	}

	public @Test void checkTradingWithNPC(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		s.getPlayerByUserName("Simon").getInventory().add(new Fish("fishy"));
		NPC npc = new NPC("random", Direction.SOUTH);
		s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,4)).setGameObject(npc);
		s.parseInput(new PlayerCommand("move Simon north"));
		assertTrue(s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,5)).getGameObject() != null);
		s.parseInput(new PlayerCommand("siphon Simon 0"));
		assertTrue(s.getPlayerByUserName("Simon").getInventory().isEmpty());
	}

	public @Test void checkChestCorrect(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		Chest chest = new Chest(new Banana("Banana"));
		chest.setCode(0);
		s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,4)).setGameObject(chest);
		s.getPlayerByUserName("Simon").getInventory().add(new Key("key", 0));
		s.parseInput(new PlayerCommand("move Simon north"));
		assertEquals(s.getPlayerByUserName("Simon").getInventory().get(0).getName(), "Banana");
	}

	public @Test void checkChestFalse(){
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		Chest chest = new Chest(new Banana("Banana"));
		chest.setCode(1);
		s.requestBoard().getLocationById(0).getTileAtPosition(new Position(5,4)).setGameObject(chest);
		s.getPlayerByUserName("Simon").getInventory().add(new Key("key", 0));
		s.parseInput(new PlayerCommand("move Simon north"));
		assertNotEquals(s.getPlayerByUserName("Simon").getInventory().get(0).getName(), "Banana");
	}

	public @Test void checkWinning(){
		String parse = "true";
		ServerController s = new ServerController(new Server(1000));
		s.parseInput(new PlayerCommand("login Simon"));
		for(int i = 0; i < 5; i ++){
			s.getPlayerByUserName("Simon").getInventory().add(new Banana("Banana"));
		}
		for(int i = 0; i < 5; i ++){
			parse = s.parseInput(new PlayerCommand("siphon Simon 0"));
		}
		assertTrue(s.getPlayerByUserName("Simon").getInventory().isEmpty());
		assertEquals(parse, "endgame");
	}

	public Player makePlayerHelper(String name, int locationID, Position pos){
		return new Player(name, locationID, pos, BoardParser.parseBoardFName("map-new.txt"));
	}
}
