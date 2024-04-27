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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchView extends VBox {
	
	Stage primaryStage;
	
	private Label searchLabel;
	private Label warning;
	private TextField searchtext;
	private AssetTableView searchResultsView;
	private Button backBtn;
	private Button edit;
	private Button del;
	private Button search;
	
	public SearchView(Stage primaryStage) {
		
		super(10);
		this.primaryStage = primaryStage;
	}
	
	public void show() {
		
    	searchLabel = new Label("Enter asset name");
    	warning = new Label("Asset does not exist!");
    	
    	// Text field for entering asset name
    	searchtext = new TextField();
    	searchtext.setMaxWidth(300);
    	searchtext.setPromptText("E.g. Spoon, Phone, Medicine ...");

    	//Back Button
    	Scene previousScene = primaryStage.getScene();
    	backBtn = new Button("Back");
    	backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
    	
    	// Button to enter search
    	search = new Button("Search");
    	search.setOnAction(event -> searchAction());
    	
    	HBox searchBar = new HBox(10);
    	searchBar.getChildren().addAll(searchLabel,searchtext,search);
    	searchBar.setAlignment(Pos.CENTER);
    	
    	this.getChildren().addAll(searchBar);
    	
		searchResultsView = new AssetTableView();
		
		edit = new Button("Edit");
    	edit.setOnAction(event -> editAction());
    	
    	del = new Button("Delete");
    	del.setOnAction(event -> deleteAction());
		
		
		HBox btnBox = new HBox(10);
		btnBox.getChildren().addAll(searchResultsView,edit, del, backBtn);
		btnBox.setAlignment(Pos.CENTER);

    	
    	this.getChildren().addAll(searchResultsView, btnBox);
        this.setAlignment(Pos.CENTER);

		Scene scene = new Scene(this, MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search results");
        primaryStage.show();

	}
	
	private void searchAction() {
		List<Asset> searchResults = AssetHelper.searchAsset(searchtext.getText());
		
		if (searchResults.size() > 0) {
			
			searchResultsView.setAssets(searchResults);
			searchResultsView.refreshView();

		}
	}
	
	private void editAction() {
		TableRowData selectedRowData = searchResultsView.getSelectionModel().getSelectedItem();
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
		TableRowData selectedRowData = searchResultsView.getSelectionModel().getSelectedItem();
		Asset selectedAsset = selectedRowData.getAsset();
		if (selectedAsset != null) {
			Optional<ButtonType> result = deleteAssetConfirmation();
			// Process user response
	        result.ifPresent(buttonType -> {
	            if (buttonType == ButtonType.OK) {
	            	AssetHelper.deleteAsset(selectedAsset);
	            	// Remove the selected asset from the data list
    				searchResultsView.getTableData().remove(selectedRowData);
    				searchResultsView.refresh();
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
