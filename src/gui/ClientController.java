package gui;

import clientserver.Client;

public class ClientController {
	Client client;
	GUI gui;
	
	public ClientController(Client c){
		this.client = c;
		gui = new GUI();
	}

	public String getName(){
		//Return username from client
		return "";
	}
	
	public int getBananaCount(){
		return 0;
	}
}
