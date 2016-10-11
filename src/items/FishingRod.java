package items;

public class FishingRod extends Item{

	public FishingRod(String name) {
		super(name, "Not exactly new, however if we're lucky the fish might not be on their fins today", true);
		fname = "assets/game_objects/fishingRod/fishingRod.png";
	}
	
	public String toString(){
		return "FishingRod";
	}

}
