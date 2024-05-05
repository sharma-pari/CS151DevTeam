package application;

public class Location extends Attribute {
	
	public static final String LOCATIONS_FILENAME = "locations.csv";
	
	public Location() {
		super.setFilePath(LOCATIONS_FILENAME);
	}
	
	public Location(String name, String descr) {
		super(name,descr);
		super.setFilePath(LOCATIONS_FILENAME);
	}
	
	public Location(String csv) {
		super(csv);
		super.setFilePath(LOCATIONS_FILENAME);
	}

	@Override
	public String save() {
		return super.save();
	}
	
	
}
