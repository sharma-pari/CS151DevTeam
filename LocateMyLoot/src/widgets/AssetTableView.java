package widgets;

import java.util.List;
import application.Asset;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AssetTableView extends TableView<TableRowData> {

	private ObservableList<TableRowData> tableData = FXCollections.observableArrayList();
	
	private List<Asset> assets;

	public AssetTableView() {
		
        // Create and populate first column
        TableColumn<TableRowData, String> assetName = new TableColumn<>("Asset");
        assetName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAssetName()));

        // Create and populate first column
        TableColumn<TableRowData, String> assetDescr = new TableColumn<>("Asset Description");
        assetDescr.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAssetDescr()));

        // Category Columns
        TableColumn<TableRowData, String> categoryName = new TableColumn<>("Category");
        categoryName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAssetCategory())); 
        
        TableColumn<TableRowData, String> categoryDescr = new TableColumn<>("Category Description");
        categoryDescr.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCatDescr()));

        
        // Location columns
        TableColumn<TableRowData, String> locationName = new TableColumn<>("Location");
        locationName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAssetLocation()));
 
        TableColumn<TableRowData, String> locationDescr = new TableColumn<>("Location Description");
        locationDescr.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAssetLocDescr()));

        TableColumn<TableRowData, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPurchasePrice()));
        
        TableColumn<TableRowData, String> purchDateColumn = new TableColumn<>("Purchase Date");
        purchDateColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPurchDate()));
        
        TableColumn<TableRowData, String> expDateColumn = new TableColumn<>("Expiry Date");
        expDateColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getExpDate()));
        
        // Add both columns to table
        this.getColumns().addAll(assetName, assetDescr, categoryName, categoryDescr, 
        		locationName, locationDescr, priceColumn, 
        		purchDateColumn, expDateColumn);
	}
	
	public void setAssets(List<Asset> assets) {
		if(this.assets != null) {	
			//remove previous data
			for(int i=0; i < tableData.size(); i++) {
				tableData.remove(i);
			}
		}
		this.assets = assets;
	}
	
	public void refreshView() {
		tableData.clear();
        if(assets != null) {
            for (Asset asset : assets) {
                tableData.add(new TableRowData(asset));
            }        	
        }
        this.setItems(tableData);
	}

	public ObservableList<TableRowData> getTableData() {
		return tableData;
	}
	
	

}
