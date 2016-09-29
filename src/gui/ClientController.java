package gui;

import clientserver.*;
import core.Board;
import iohandling.BoardCreator;
import renderer.Renderer;

public class ClientController {
	Client client;
	GUI gui;
	Renderer renderer;
	Board temp = BoardCreator.loadBoard("map.txt");
	
	public ClientController(Client c){
		this.client = c;
		gui = new GUI(this);
		renderer = new Renderer();
		drawBoard();
	}
	
	public void hideGUI(){
		gui.hideGUI();
	}

	public String getName(){
		return client.getUsername();
	}
	
	public int getBananaCount(){
		//get player banana count
		return 0;
	}
	
	public void rotateLeft(){
		//rotate camera left
		renderer.rotateClockwise();
		System.out.println("rotate left");
		drawBoard();
	}
	
	public void rotateRight(){
		renderer.rotateCounterClockwise();

		//rotate camera right
		System.out.println("rotate right");
		drawBoard();
	}
	
	public void sendMessage(PlayerCommand msg){
		client.sendMessage(msg);
	}
	
	public void drawBoard(){
		gui.showBoard(renderer.paintLocation(temp.getLocationById(0), 1000, 800));
	}
	
	public void sendBoard(Board board){
		
	}
}
