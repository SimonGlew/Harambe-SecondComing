package gameobjects;


public class Item extends GameObject {

	private String name;
	private String description;

	public Item(String name, String description) {
		this.name = name;
		this.description = description;
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
