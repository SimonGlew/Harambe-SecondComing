package items;

public class Fish extends Item{

	public Fish(String name) {
		super(name, "Wet and slimey, the temptation to eat is high, maybe somebody else would like it more", false);
		fname = "assets/game_objects/fish/fish.png";
	}
	
	public String toString(){
		return "Fish";
	}

}
