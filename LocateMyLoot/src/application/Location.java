package application;

public class Location extends Attribute {
	
	public Location() {
		super.setFilePath("locations.csv");
	}
	
	public Location(String name, String descr) {
		super(name,descr);
		super.setFilePath("locations.csv");
	}
	
	public Location(String csv) {
		super(csv);
		super.setFilePath("locations.csv");
	}

	@Override
	public String save() {
		return super.save();
	}
	
	
}
