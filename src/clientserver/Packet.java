package clientserver;

import core.Board;

public class Packet {
	String type;
	Board board;
	String message;
	
	public Packet(String type, Board board){
		this.type = type;
		this.board = board;
	}
	
	public Packet(String type, String message){
		this.type = type;
		this.message = message;
	}
}
