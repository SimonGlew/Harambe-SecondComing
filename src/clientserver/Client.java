package clientserver;

import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;

import core.Board;
import gui.ClientController;
import gui.GUI;
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
			System.out.println("Error connectiong to server:" + e);
			return false;
		}

		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		System.out.println(msg);

		/* Creating both Data Stream */
		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Exception creating new Input/output Streams: " + e);
			return false;
		}

		// creates the Thread to listen from the server
		new ListenFromServer().start();
		menu.dispose();
		clientController = new ClientController(this);
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
					Board board = (Board) sInput.readObject();
					clientController.sendBoard(board);
				} catch (IOException e) {
					System.out.println("Server has close the connection: " + e);
					break;
				}
				catch (ClassNotFoundException e) {
				}
			}
		}
	}
}
