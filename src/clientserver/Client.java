package clientserver;

import java.net.*;
import java.util.Scanner;
import java.io.*;
import javax.swing.JFrame;

import gui.Menu;
import iohandling.BoardParser;

/**
 * This is the class that holds all the information for the client, for the client server exchange, this holds connection and the streams used for 
 * communicating with the server
 * 
 * @author Simon Glew
 *
 */
public class Client {
	private ObjectInputStream sInput; 
	private ObjectOutputStream sOutput; 
	private Socket socket;

	private String server;
	private int port;
	private ClientController clientController;
	private JFrame menu;
	private String username;
	private boolean loggedIn = false;

	/**
	 * Constructor for the client, that gets called when a method within the menu class is called
	 * 
	 * @param server - The server that the client is trying to connect to
	 * @param port - The port that the client is connecting to the server through
	 * @param username - The username that the user has entered that is going to correspond to the client
	 * @param menu - The menu frame that the client is using
	 */
	public Client(String server, int port, String username, JFrame menu) {
		this.menu = menu;
		this.server = server;
		this.port = port;
		this.username = username;

		if (!this.start()) {
			menu.dispose();
			new Menu();
			return;
		}
	}

	/**
	 * This method is the method that initially gets called when the client tries and connects to the server, it sets up a new socket, sets up the data 
	 * streams for the server and client to use and sends through an initial connect message
	 *  
	 * @return successful login boolean
	 */
	public boolean start() {
		try {
			socket = new Socket(server, port);
		}
		catch (Exception e) {
			System.out.println("Error connecting to server:" + e);
			return false;
		}
		
		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Exception creating new Input/output Streams: " + e);
			return false;
		}

		clientController = new ClientController(this);
		new ListenFromServer().start();
		sendMessage(new PlayerCommand("login " + this.username));
		return true;
	}

	/**
	 * Helper method that sends a player command through to the server
	 * 
	 * @param msg - message getting sent through to the server
	 */
	public void sendMessage(PlayerCommand msg) {
		try {
			sOutput.writeObject(msg);
		} catch (IOException e) {
			System.out.println("Exception writing to server: " + e);
		}
	}
	
	/**
	 * Helper method to get the username of the player/client
	 * 
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Class that creates a thread that listens through the input stream waiting for a packet to come back from the server and do things to depending on 
	 * what is send through the packet to the client
	 * 
	 * @author Simon Glew
	 */
	class ListenFromServer extends Thread {
		
		/**
		 * Method that gets called that listens to the input stream for messages coming in from the server
		 * 
		 * See class javadoc for more information 
		 */
		public void run() {
			while (true) {
				try {
					Packet packet = (Packet) sInput.readObject();
					if (packet.getType().equals("board")) {
						if (!loggedIn) {
							menu.dispose();			
							clientController.showGUI();
							loggedIn = true;
						}
						clientController.sendBoard(BoardParser.parseBoardString(packet.getBoard()), packet.getTime());
					} else if (packet.getType().equals("string")) {
						if (packet.getMessage().equals("fail login")) {
							menu.dispose();
							new Menu();
						}else if(packet.getMessage().equals("endgame")){
							String playerName = null;
							try{
								Scanner s = new Scanner(packet.getMessage());
								s.next();
								playerName = s.next();
							}catch(Exception e){
								System.out.println(e);
							}
							if(playerName != null){
								clientController.showEndGameScreen(playerName);
							}
						}
					}
				} catch (IOException e) {
					clientController.hideGUI();
					System.out.println("Server has close the connection: " + e);
					break;
				} catch (ClassNotFoundException e) {
					
				}
			}
		}
	}
}
