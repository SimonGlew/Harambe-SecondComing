package clientserver;

import java.net.*;
import java.io.*;
import javax.swing.JFrame;

import gui.ClientController;
import gui.Menu;
import iohandling.BoardParser;

/*
 * The Client running through console
 */
public class Client {

	// for I/O
	private ObjectInputStream sInput; // to read from the socket
	private ObjectOutputStream sOutput; // to write on the socket
	private Socket socket;

	// the server, the port
	private String server;
	private int port;
	private ClientController clientController;
	private JFrame menu;
	private String username;
	private boolean loggedIn = false;

	/*
	 * Constructor called by console mode server: the server address port: the
	 * port number
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

	/*
	 * To start the dialog
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		}
		// if it failed not much I can so
		catch (Exception e) {
			System.out.println("Error connecting to server:" + e);
			return false;
		}

		/* Creating both Data Stream */
		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Exception creating new Input/output Streams: " + e);
			return false;
		}

		// creates the Thread to listen from the server
		clientController = new ClientController(this);
		new ListenFromServer().start();
		sendMessage(new PlayerCommand("login " + this.username));
		return true;
	}

	/*
	 * To send a message to the server, push the object here
	 */
	public void sendMessage(PlayerCommand msg) {
		try {
			sOutput.writeObject(msg);
		} catch (IOException e) {
			System.out.println("Exception writing to server: " + e);
		}
	}

	public String getUsername() {
		return this.username;
	}

	/*
	 * a class that waits for the message and prints it out to the console mode
	 */
	class ListenFromServer extends Thread {

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
