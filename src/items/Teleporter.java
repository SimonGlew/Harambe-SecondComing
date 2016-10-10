package items;

public class Teleporter extends Item{

	public Teleporter(String name) {
		super(name, "A magical orb eminating with power, rumour has it this will teleport you somewhere..", true);
		fname = "assets/game_objects/teleporter/teleporter.png";
	}
	
	public String toString(){
		return "Teleporter";
	}

}
