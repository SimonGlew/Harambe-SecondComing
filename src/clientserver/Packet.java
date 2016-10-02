package clientserver;

import java.io.Serializable;

import core.Board;

public class Packet implements Serializable{

	private static final long serialVersionUID = 7677212855723082352L;
	
	private String type;
	private String board;
	private String message;
	private int time;
	
	public Packet(String type, String board, String message, int time){
		this.type = type;
		this.board = board;
		this.message = message;
		this.time = time;
	}
	
	public int getTime(){
		return this.time;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getBoard(){
		return this.board;
	}
	
	public String getMessage(){
		return this.message;
	}
}
