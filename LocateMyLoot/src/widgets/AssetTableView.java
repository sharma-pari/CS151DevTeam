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

        // Create and populate second column
        TableColumn<TableRowData, String> categoryName = new TableColumn<>("Category");
        categoryName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAssetCategory()));

        // Create and populate second column
        TableColumn<TableRowData, String> locationName = new TableColumn<>("Location");
        locationName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAssetLocation()));

        // Add both columns to table
        this.getColumns().addAll(assetName, assetDescr, categoryName, locationName);
	}
	
	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}
	
	public void refreshView() {
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
