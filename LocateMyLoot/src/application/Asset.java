package application;

public class Asset {
    private String name;
    private Location location;
    private Category category;
    private String descr;
    private String purchaseDate;
    private String warExDate;
    private String purChaseValue;

    
    public Asset() {
    	
    }
    
    public Asset(String name, Location location, Category category, String descr, String purchaseDate, String warExDate,
			String purChaseValue) {
		super();
		this.name = name;
		this.location = location;
		this.category = category;
		this.descr = descr;
		this.purchaseDate = purchaseDate;
		this.warExDate = warExDate;
		this.purChaseValue = purChaseValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getWarExDate() {
		return warExDate;
	}

	public void setWarExDate(String warExDate) {
		this.warExDate = warExDate;
	}

	public String getPurChaseValue() {
		return purChaseValue;
	}

	public void setPurChaseValue(String purChaseValue) {
		this.purChaseValue = purChaseValue;
	}

	
	//Red Lipstick,Red and Glossy,Bathroom,Makeup Cabinets in Bathroom,Make Up,make up Items,2024-04-10,12.29,2024-05-15
	@Override
	public String toString() {
		return this.getName() + "," +
				this.getDescr() + "," +
				this.getLocation().getName() + "," +
				this.getLocation().getDesc() + "," +
				this.getCategory().getName() + "," +
				this.getCategory().getDesc() + "," +
				this.getPurchaseDate() + "," +
				this.getPurChaseValue() + "," +
				this.getWarExDate();
	}
}
