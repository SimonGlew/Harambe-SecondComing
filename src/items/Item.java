package items;

import gameobjects.GameObject;

public class Item extends GameObject {

	protected String name;
	private String description;
	
	private boolean usable;

	public Item(String name, String description, Boolean usable) {
		this.name = name;
		this.description = description;
		this.usable = usable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
