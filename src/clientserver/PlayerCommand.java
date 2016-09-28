package clientserver;
import java.io.*;
/*
 * Current class holding the things needed to get send from the server to client and vice versa\
 * 
 * 
 * This is gonna turn into something with changing board state
 * 
 */

public class PlayerCommand implements Serializable {

	protected static final long serialVersionUID = 1112122200L;
	private String message;
	
	// constructor
	PlayerCommand(String message) {
		this.message = message;
	}
	
	String getMessage() {
		return message;
	}
}

