package widgets;

import java.util.List;
import java.util.Optional;

import application.Asset;
import application.AssetHelper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AssetsListView extends VBox{
	
	private AssetTableView assetTable;
	private Button backBtn;
	private Button editBtn;
	private Button delBtn;
	
	Stage primaryStage;
	
	List<Asset> assets;
	
    public AssetsListView(Stage primaryStage) {
    	super(10);
    	this.primaryStage = primaryStage;
    }
    
    public void show() {
    	
        this.setAlignment(Pos.CENTER);
        
    	assetTable = new AssetTableView();
    	List<Asset> assets = AssetHelper.readAssetsFromCSV();
    	assetTable.setAssets(assets);
    	assetTable.refreshView();

        
    	backBtn = new Button("Back");
        // Store scene and create back button to go back
        Scene previousScene = primaryStage.getScene();
        backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
        
		editBtn = new Button("Edit");
    	editBtn.setOnAction(event -> editAction());
    	
    	delBtn = new Button("Delete");
    	delBtn.setOnAction(event -> deleteAction());
    	
		HBox btnBox = new HBox(10);
		btnBox.getChildren().addAll(editBtn, delBtn, backBtn);
		btnBox.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(assetTable,btnBox );

		Scene scene = new Scene(this, MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Assets");
        primaryStage.show();
    }
    
    
	private void editAction() {
		TableRowData selectedRowData = assetTable.getSelectionModel().getSelectedItem();
		Asset selectedAsset = selectedRowData.getAsset();
		if (selectedAsset != null) {
	        AssetView defRoot = new AssetView(primaryStage);
	        defRoot.setAsset(selectedAsset);
	        defRoot.setAlignment(Pos.CENTER);
	        primaryStage.setScene(new Scene(defRoot, MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT));
	        primaryStage.setTitle("Edit Asset");
	        primaryStage.show();
        }
	}
	
	private void deleteAction() {
		TableRowData selectedRowData = assetTable.getSelectionModel().getSelectedItem();
		Asset selectedAsset = selectedRowData.getAsset();
		if (selectedAsset != null) {
			Optional<ButtonType> result = deleteAssetConfirmation();
			// Process user response
	        result.ifPresent(buttonType -> {
	            if (buttonType == ButtonType.OK) {
	            	AssetHelper.deleteAsset(selectedAsset);
	            	// Remove the selected asset from the data list
	            	assetTable.getTableData().remove(selectedRowData);
	            	assetTable.refresh();
	            } 
	        });
            
        }
	}

    private Optional<ButtonType> deleteAssetConfirmation() {
        // Create confirmation dialog
        Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Delete Asset");
        confirmationDialog.setContentText("Are you sure you want to delete this asset?");

        // Get the dialog pane and apply any styling if needed
        DialogPane dialogPane = confirmationDialog.getDialogPane();
        // dialogPane.getStylesheets().add(getClass().getResource("your-stylesheet.css").toExternalForm());

        // Show the confirmation dialog and wait for user response
        Optional<ButtonType> result = confirmationDialog.showAndWait();

        return result;
    }

}
