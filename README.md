# SWEN222_Harambe, The Second Coming
![Version](https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000)

A project completed through the course of SWEN222 at Victoria University. The aim was create an Java Application that was a isometric arcade game. This game uses a client/server framework allow multiple players within it with the ability of saving and loading custom maps with an inbuilt MapEditor for changing the map.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Java installed on your system
Java IDE installed on your system
```

### Installing

A step by step series of examples that tell you how to get a development env running

Import the project within JavaIDE
Run server
```
run Server.java found within ClientServer package, this will print out the port to listen into
```
and to run the GUI (Game/Client) of the application
```
run Client.java within ClientServer package. 

Input the required fields such as a name and an address
Note: if running of the same machine the address will be localhost, otherwise the ip of the server machine. 
Finally enter the port number of the server.
```
### Instructions on the game
To Move your Penguin you can use W,A,S,D or click the map within your current or neighbouring location to move by mouse click.

Walking over items will pick them up (floating device, key, teleporter, fish, banana, fishing rod)
Right click items in the inventry to drop them or use/examine them.
You may also right click items/objects on the map to examine the object or the ground beneath them.

Equipping a floaty in the inventory will allow you to swim through water.
Examine a key while its in your inventory to see its code, and right click a chest on the ground to see it's code, if you walk into a chest while holding the key with the same code
you will recieve the contents of the chest (a banana).

If you use a fishing rod will facing a water tile, you will fish and have a chance of catching a fish, if you catch a fish and then take it to an npc you will trade your fish for a banana

Teleporters when used will teleport you back to the spawn location.

Once you have recieved a banana in your inventory, you may right click it and use the siphon option, this will increase you banana count featured below the inventory on the UI.
Once a player has reached 5 bananas the game will end and the name of the winning player will be displayed at the top of the newly created window.

If you walk into the door of a house, you will be transported within the house, if you the walk back onto the doortile (visible from within the house) you will be transported back out of the house.

NPCs will walk around with two possible strategies, talking to them during the day will either result in you getting a banana or no action, at night time the NPCs will become uncontactable and no longer move.

Happy Exploring!!

## License

This project is licensed under the MIT License
