package application;

public class Category extends Attribute {
	
	public Category() {
		super();
		super.setFilePath("categories.csv");
	}
	
	public Category(String name, String descr) {
		super(name,descr);
		super.setFilePath("categories.csv");
	}
	
	public Category(String csv) {
		super(csv);
		super.setFilePath("categories.csv");
	}

	@Override
	public String save() {
		return super.save();
	}
}
