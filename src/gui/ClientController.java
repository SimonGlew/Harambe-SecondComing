package gui;

import clientserver.Client;

public class ClientController {
	Client client;
	GUI gui;
	
	public ClientController(Client c){
		//System.out.println("BANANA");
		this.client = c;
		gui = new GUI();
		System.out.println("BANANA");
	}

	public String getName(){
		//Return username from client
		return "";
	}
}
