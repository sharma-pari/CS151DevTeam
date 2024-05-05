package application;

public class Category extends Attribute {
	
	public static final String CATEGORIES_FILENAME = "categories.csv";
	
	public Category() {
		super();
		super.setFilePath(CATEGORIES_FILENAME);
	}
	
	public Category(String name, String descr) {
		super(name,descr);
		super.setFilePath(CATEGORIES_FILENAME);
	}
	
	public Category(String csv) {
		super(csv);
		super.setFilePath(CATEGORIES_FILENAME);
	}

	@Override
	public String save() {
		return super.save();
	}
}
