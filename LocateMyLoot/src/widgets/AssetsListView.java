package widgets;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import application.Asset;
import application.AssetHelper;
import application.CSVHelper;
import application.Category;
import application.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AssetsListView extends VBox{
	
	private AssetTableView assetTable;
	private Button backBtn;
	private Button editBtn;
	private Button delBtn;
	
	private ComboBox<String> comboCategory = new ComboBox<>();
	private ComboBox<String> comboLocation = new ComboBox<>();
	
	private Label categoryLabel = new Label("Category");
	private Label locationLabel = new Label("Location");
	
	Stage primaryStage;
	
	List<Asset> assets;
	
    public AssetsListView(Stage primaryStage) {
    	super(10);
    	this.primaryStage = primaryStage;
    	
//TRY refreshing the page to show edits made when the Stage gets focus back
//        primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
//        	assetTable.refreshView();
//        	assetTable.refresh();
//        });

//        primaryStage.setOnShown(event -> {
//        	assetTable.refreshView();
//        	assetTable.refresh();
//        });
    }
    
    public void show() {
    	
        this.setAlignment(Pos.CENTER);
        

    	// initialize filtering combo boxes
    	initializeComboBoxes();
    	HBox filterBox = new HBox(10);
    	filterBox.getChildren().addAll(categoryLabel,comboCategory,locationLabel,comboLocation);
    	filterBox.setAlignment(Pos.CENTER);
        
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
		
		//now create the TabelView and Populate the Values
    	assetTable = new AssetTableView();
    	populateAseetsInTableView();
    	
    	this.getChildren().addAll(filterBox, assetTable,btnBox );

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
    
    private void initializeComboBoxes() {
    	
    	//Populate the Category ComboBox
    	// Get the Categories from the csv file
    	List<Category> categories = CSVHelper.CSVtoCategories(Category.CATEGORIES_FILENAME);
    	// Create a list of items
        ObservableList<String> categoryItems = FXCollections.observableArrayList();
        categoryItems.add("All");
        for(Category cat : categories) {
        	categoryItems.add(cat.getName());
        }
        comboCategory.setItems(categoryItems);
        // Set a default selection (optional)
        comboCategory.getSelectionModel().selectFirst();
        
        comboCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	populateAseetsInTableView();
        });
        
    	//Populate the Location ComboBox
    	// Get the Categories from the csv file
    	List<Location> locations = CSVHelper.CSVtoLocations(Location.LOCATIONS_FILENAME);
    	// Create a list of items
        ObservableList<String> locationItems = FXCollections.observableArrayList();
        locationItems.add("All");
        for(Location loc : locations) {
        	locationItems.add(loc.getName());
        }
        comboLocation.setItems(locationItems);
        // Set a default selection (optional)
        comboLocation.getSelectionModel().selectFirst();
        
        comboLocation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	populateAseetsInTableView();
        });
    }
    
    private void populateAseetsInTableView() {
    	List<Asset> assets = AssetHelper.readAssetsFromCSV();
    	
    	//filter assets based on Filer selection on Location and Category
    	String selectedCategory = comboCategory.getValue();
    	String selectedLocation = comboLocation.getValue();
    	List<Asset> filteredAssets = assets.stream()
                .filter(asset -> isCategoryMatch(asset, selectedCategory))
                .filter(asset -> isLocationMatch(asset, selectedLocation))
                .collect(Collectors.toList());
    	
    	assetTable.setAssets(filteredAssets);
    	assetTable.refreshView();
    }
    
    private boolean isCategoryMatch(Asset asset, String selectedCategory) {
        // If "All" is selected, return true for all assets
        if ("All".equals(selectedCategory)) {
            return true;
        }
        // Otherwise, match the category name
        String categoryName = asset.getCategory().getName();
        return categoryName.equals(selectedCategory);
    }

    private boolean isLocationMatch(Asset asset, String selectedLocation) {
        // If "All" is selected, return true for all assets
        if ("All".equals(selectedLocation)) {
            return true;
        }
        // Otherwise, match the location name
        String locationName= asset.getLocation().getName();
        return locationName.equals(selectedLocation);
    }
}
