package application;

public abstract class Attribute {
	
	private String name;
	private String desc;
	private String filePath; //the path to the CSV file to stroe the attribute
	
	
	public Attribute() {
		
	}
	
	public Attribute(String name, String descr) {
		this.name = name;
		this.desc = descr;
	}
	
	public Attribute(String csv) {
		String[] parts = csv.split(",", -1);
        if (parts.length >= 2) {
            this.setName(parts[0]);
            this.setDesc(parts[1]);
        }
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return this.getName() + "," +
				this.getDesc();
	}
	
	

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	//Save the attribute to the file path
	public String save() {
		return AttributeHelper.writeAttributeToCSV(this.filePath, this);
	}
	
}
