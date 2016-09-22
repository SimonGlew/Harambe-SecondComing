package clientserver;
import java.io.*;
/*
 * Current class holding the things needed to get send from the server to client and vice versa\
 * 
 * 
 * This is gonna turn into something with changing board state
 * 
 */

public class ChatMessage implements Serializable {

	protected static final long serialVersionUID = 1112122200L;

	static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;
	private int type;
	private String message;
	
	// constructor
	ChatMessage(int type, String message) {
		this.type = type;
		this.message = message;
	}
	
	// getters
	int getType() {
		return type;
	}
	String getMessage() {
		return message;
	}
}

