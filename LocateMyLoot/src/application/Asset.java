package application;

public class Asset implements Attribute {
	private String name;
	private Category cat;
	private Location loc;
	private String purDate;
	private String desc;
	private String purVal;
	private String warExpDate;
	
	public Asset(String name, Category cat, Location loc) {
		this.name = name;
		this.cat = cat;
		this.loc = loc;
		purDate = "";
		desc = "";
		purVal = "";
		warExpDate = "";
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

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public String getPurDate() {
		return purDate;
	}

	public void setPurDate(String purDate) {
		this.purDate = purDate;
	}

	public String getPurVal() {
		return purVal;
	}

	public void setPurVal(String purVal) {
		this.purVal = purVal;
	}

	public String getWarExpDate() {
		return warExpDate;
	}

	public void setWarExpDate(String warExpDate) {
		this.warExpDate = warExpDate;
	}
	
	
	
}
