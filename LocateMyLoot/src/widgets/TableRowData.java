package widgets;

import application.Asset;

public class TableRowData {
	
	private Asset asset;
	
    public TableRowData(Asset asset) {
        this.asset = asset;
    }

	public Asset getAsset() {
		return asset;
	}

	public String getAssetName() {
		return asset.getName();
	}
	
	public String getAssetDescr() {
		return asset.getDescr();
	}
	
	public String getAssetLocation() {
		return asset.getLocation().getName();
	}
	
	public String getAssetLocDescr() {
		return asset.getLocation().getDesc();
	}
	
	public String getAssetCategory() {
		return asset.getCategory().getName();
	}
	
	public String getCatDescr() {
		return asset.getCategory().getDesc();
	}
	
	public String getPurchDate() {
		return asset.getPurchaseDate();
	}
	
	public String getExpDate() {
		return asset.getWarExDate();
	}
	
	public String getPurchasePrice() {
		return asset.getPurChaseValue();
	}

}
