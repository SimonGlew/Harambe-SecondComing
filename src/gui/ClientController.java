package gui;

import clientserver.Client;

public class ClientController {
	Client client;
	GUI gui;
	
	public ClientController(Client c){
		this.client = c;
		gui = new GUI();
	}

}
