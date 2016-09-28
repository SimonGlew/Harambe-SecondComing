package clientserver;

import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;

import gui.ClientController;
import gui.Menu;

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

	/*
	 * Constructor called by console mode server: the server address port: the
	 * port number
	 */

	public Client(String server, int port, JFrame menu) {
		this.menu = menu;
		this.server = server;
		this.port = port;
		
		if (!this.start()){
			new Menu();
			return;
		}

		// wait for messages from user
		//TODO: Where sending OBject to server (Object currently ChatMessage)
		//Scanner scan = new Scanner(System.in);
		// loop forever for message from the user
		while(true){
			
		}
//		while (true) {
//			System.out.print("> ");
//			// read message from user
//			String msg = "Banana";
//			// logout if message is LOGOUT
//			if (msg.equalsIgnoreCase("LOGOUT")) {
//				this.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
//				// break to do the disconnect
//				break;
//			}
//			// message WhoIsIn
//			else if (msg.equalsIgnoreCase("WHOISIN")) {
//				this.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
//			} else { // default to ordinary message
//				this.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
//			}
//		}
		//scan.close();
		// done disconnect
		//this.disconnect();
	}

	/*
	 * To start the dialog
	 */
	public boolean start() {
		menu.dispose();
		
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		}
		// if it failed not much I can so
		catch (Exception e) {
			display("Error connectiong to server:" + e);
			return false;
		}

		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);

		/* Creating both Data Stream */
		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			display("Exception creating new Input/output Streams: " + e);
			return false;
		}

		// creates the Thread to listen from the server
		new ListenFromServer().start();
		clientController = new ClientController(this);
		return true;
	}

	/*
	 * To send a message to the console
	 */
	private void display(String msg) {
		System.out.println(msg);
	}

	/*
	 * To send a message to the server, push the object here
	 */
	void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
		} catch (IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	/*
	 * When something goes wrong Close the Input/Output streams and disconnect
	 * not much to do in the catch clause
	 */
	private void disconnect() {
		try {
			if (sInput != null)
				sInput.close();
		} catch (Exception e) {
		} // not much else I can do
		try {
			if (sOutput != null)
				sOutput.close();
		} catch (Exception e) {
		} // not much else I can do
		try {
			if (socket != null)
				socket.close();
		} catch (Exception e) {
		}
	}

	/*
	 * a class that waits for the message and prints it out to the console
	 * mode
	 */
	class ListenFromServer extends Thread {

		public void run() {
			while (true) {
				try {
					//TODO: This is where the client listens to server, need to add object here
					String msg = (String) sInput.readObject();
					System.out.println(msg);
					System.out.print("> ");
				} catch (IOException e) {
					display("Server has close the connection: " + e);
					break;
				}
				catch (ClassNotFoundException e) {
				}
			}
		}
	}
}
