package items;

public class Key extends Item {
	
	int code;
	
	public Key(String name, String description, int code) {
		super(name, description);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	

}
