package ClientServer;

import java.net.*;
import java.io.*;
import java.util.*;

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


	/*
	 * Constructor called by console mode server: the server address port: the
	 * port number
	 */

	Client(String server, int port) {
		this.server = server;
		this.port = port;
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
		return true;
	}

	/*
	 * To send a message to the console
	 */
	private void display(String msg) {
		System.out.println(msg);
	}

	/*
	 * To send a message to the server
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
	 * To start the Client in console mode use one of the following command >
	 * java Client > java Client portNumber >java Client portNumber serverAddress 
	 * at the console prompt If
	 * the portNumber is not specified 1500 is used If the serverAddress is not
	 * specified "localHost" is used 
	 *
	 */
	public static void main(String[] args) {
		// default values
		int portNumber = 1500;
		String serverAddress = "localhost";

		// depending of the number of arguments provided we fall through
		switch (args.length) {
		// > javac Client portNumber serverAddr
		case 2:
			serverAddress = args[1];
			// > javac Client portNumber
		case 1:
			try {
				portNumber = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Invalid port number.");
				System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
				return;
			}
			// > java Client
		case 0:
			break;
		// invalid number of arguments
		default:
			System.out.println("Usage is: > java Client [portNumber] {serverAddress]");
			return;
		}
		// create the Client object
		Client client = new Client(serverAddress, portNumber);
		// test if we can start the connection to the Server
		// if it failed nothing we can do
		if (!client.start())
			return;

		// wait for messages from user
		//TODO: Where sending OBject to server (Object currently ChatMessage)
		Scanner scan = new Scanner(System.in);
		// loop forever for message from the user
		while (true) {
			System.out.print("> ");
			// read message from user
			String msg = scan.nextLine();
			// logout if message is LOGOUT
			if (msg.equalsIgnoreCase("LOGOUT")) {
				client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
				// break to do the disconnect
				break;
			}
			// message WhoIsIn
			else if (msg.equalsIgnoreCase("WHOISIN")) {
				client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
			} else { // default to ordinary message
				client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
			}
		}
		scan.close();
		// done disconnect
		client.disconnect();
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
