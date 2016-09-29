package clientserver;

import java.io.Serializable;

import core.Board;

public class Packet implements Serializable{

	private static final long serialVersionUID = 7677212855723082352L;
	
	String type;
	String board;
	String message;
	
	public Packet(String type, String board, String message){
		this.type = type;
		this.board = board;
		this.message = message;
	}

}
