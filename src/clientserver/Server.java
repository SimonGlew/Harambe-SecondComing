package clientserver;

import java.io.*;
import java.net.*;
import java.util.*;

import core.GameSystem;
import gameobjects.Player;
import iohandling.BoardWriter;

/*
 * The server that can be run both as a console application
 */
public class Server {
	private Map<Integer, String> IDtoUsername;
	private ArrayList<ClientThread> al;
	
	private ServerController serverController;
	private TimeThread time;
	
	private static int uniqueId;	
	private int port;
	private boolean keepGoing;
	

	/*
	 * Constructor that gets passed a port when starting the java file
	 *
	 */
	public Server(int port) {
		// the port
		this.port = port;
		// ArrayList for the Client list
		al = new ArrayList<ClientThread>();
		IDtoUsername = new HashMap<Integer, String>();
	}

	public void start() {

		keepGoing = true;
		/* create socket server and wait for connection requests */
		try {
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);
			serverController = new ServerController(this, new GameSystem());
			time = new TimeThread();
			time.start();
			// infinite loop to wait for connections
			while (keepGoing) {
					Socket socket = serverSocket.accept(); // accept connection
					if (!keepGoing)
						break;
					ClientThread t = new ClientThread(socket); // Make new thread
																// and add to list
					al.add(t);
					t.start();
			}
			// Close all connections to the server socket
			try {
				serverSocket.close();
				for (int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					} catch (IOException e) {

					}
				}
			} catch (Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
			display("Exception on new ServerSocket: " + e + "\n");
		}
	}

	/*
	 * Method that has the ability to stop the server by making a client socket
	 * on the server socket
	 */
	@SuppressWarnings("resource")
	protected void stop() {
		keepGoing = false;
		try {
			new Socket("localhost", port);
		} catch (Exception e) {

		}
	}

	/*
	 * Display an event (not a message) to the console
	 */
	private void display(String msg) {
		System.out.println(msg);
	}

	/*
	 * to broadcast a message to all Clients
	 */
	private synchronized void broadcast(Packet packet, int id) {
		for (int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the
			// list
			if (packet.getMessage() == "fail login") {
				if (ct.id == id) {
					ct.writeToClient(packet);
				}
			} else {
				if (!ct.writeToClient(packet)) {
					al.remove(i);
					display("Disconnected Client " + ct.id + " removed from list.");
				}
			}
		}
	}

	/*
	 * Method that removes the client from the list, due to client being
	 * disconnected
	 */
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for (int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			// found it
			if (ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}

	/*
	 * > java Server > java Server portNumber If the port number is not
	 * specified 1500 is used
	 */
	public static void main(String[] args) {
		// start server on port 1500 unless a PortNumber is specified
		int portNumber = 5025;
		switch (args.length) {
		case 1:
			try {
				portNumber = Integer.parseInt(args[0]);
			} catch (Exception e) {
				return;
			}
		case 0:
			break;
		default:
			return;

		}
		// create a server object and start it
		Server server = new Server(portNumber);
		server.start();
	}
	
	class TimeThread extends Thread {
		int count;
		
		TimeThread(){
			count = 0;
		}
		
		public void run(){
			count++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		
		public int getTime(){
			return count;
		}
	}
	

	/** One instance of this thread will run for each client */
	class ClientThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for disconnection)
		int id;
		// the only type of message a will receive
		PlayerCommand cm;

		// Constructor
		ClientThread(Socket socket) {
			// a unique id
			id = ++uniqueId;
			this.socket = socket;
			/* Creating both Data Stream */
			try {
				// create output first
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
		}

		// what will run forever
		public void run() {
			// to loop until LOGOUT
			boolean keepGoing = true;
			while (keepGoing) {
				// read a String (which is an object)
				try {
					cm = (PlayerCommand) sInput.readObject();
				} catch (IOException e) {
					display(id + " Exception reading Streams: " + e);
					break;
				} catch (ClassNotFoundException e) {
					break;
				}
				if (serverController.parseInput(cm).equals("true")) {
					// Switch on the type of message receive
					// TODO: Some way of sending a board back, change broadcast
					// method
					if(cm.getMessage().contains("login")){
						if(al.size() < 4){
							IDtoUsername.put(id, cm.getMessage().substring(6));
							broadcast(new Packet("board", BoardWriter.writeBoardToString(serverController.requestBoard()), null, time.getTime()), id);
						}else{
							broadcast(new Packet("string", null, "fail login", 0), id);
							remove(id);
							this.close();
						}
					}else{
						broadcast(new Packet("board", BoardWriter.writeBoardToString(serverController.requestBoard()), null, time.getTime()), id);
					}
				} else if (serverController.parseInput(cm).equals("fail login")) {
					broadcast(new Packet("string", null, "fail login", 0), id);
					remove(id);
					this.close();
				} else if(serverController.parseInput(cm).equals("false") && cm.getMessage().contains("move")) {
					broadcast(new Packet("board", BoardWriter.writeBoardToString(serverController.requestBoard()), null, time.getTime()), id);
				}else{
					System.out.println("fail");
				}
			}
			// remove myself from the arrayList containing the list of the
			// connected Clients
			Player p = serverController.getPlayerByUserName(IDtoUsername.get(id));
			if (p != null) {
				p.setLoggedIn(false);
				IDtoUsername.remove(id);
				p.getTile().setGameObject(null);
			}
			remove(id);
		}

		// try to close everything
		private void close() {
			// try to close the connection
			try {
				if (sOutput != null)
					sOutput.close();
			} catch (Exception e) {

			}
			try {
				if (sInput != null)
					sInput.close();
			} catch (Exception e) {

			}
			try {
				if (socket != null)
					socket.close();
			} catch (Exception e) {

			}
		}

		/*
		 * Write a String to the Client output stream
		 */
		private boolean writeToClient(Packet packet) {
			// if Client is still connected send the message to it
			if (!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(packet);
			}
			// if an error occurs, do not abort just inform the user
			catch (IOException e) {
				display("Error sending message to " + id);
				display(e.toString());
			}
			return true;
		}
	}
}
