package application;

import java.util.*;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Scene previousScene;
	
	public static int catId = 0;
	public static HashMap<Integer, Category> categories = new HashMap<>();
	public static int locId = 0;
	public static HashMap<Integer, Location> locations = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Welcome to Locate My Loot!");
    	
    	// Create label
    	Label label = new Label("What would you like to do?");
    	
    	// Button for viewing current assets, or view by category or location
    	Button assetBtn = new Button("View All Assets");
    	assetBtn.setOnAction(event -> viewAssets(primaryStage));
    	
    	// Button for defining new category
    	Button defCatBtn = new Button("Add New Category");
    	defCatBtn.setOnAction(event -> define(primaryStage, "Category"));
    	
    	// Button for defining new location
    	Button defLocBtn = new Button("Add New Location");
    	defLocBtn.setOnAction(event -> define(primaryStage, "Location"));
    	
    	// Create UI layout
        VBox root = new VBox(10);
        root.getChildren().addAll(label, assetBtn, defCatBtn, defLocBtn);
        root.setAlignment(Pos.CENTER);

        // Set the scene
        primaryStage.setScene(new Scene(root, 400, 200));

        // Show the stage
    	primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    // Private helper methods
    
    // Shows assets by attributes
    private void viewAssets(Stage primaryStage) {
        ObservableList<TableRowData> tableData = FXCollections.observableArrayList();

        // Stores categories and location in rows
        if (catId > locId) {
	        categories.forEach((catId, category) -> {
	            Location location = locations.get(catId);
	            String locationName = "";
	            if (location != null) {
	            	locationName = location.getName();
	            }
	  
	            tableData.add(new TableRowData(category.getName(), locationName));
	        });
        }
        else {
        	locations.forEach((locId, location) -> {
	            Category category = categories.get(locId);
	            String categoryName = "";
	            if (category != null) {
	            	categoryName = category.getName();
	            }
	  
	            tableData.add(new TableRowData(categoryName, location.getName()));
	        });
        }

        // Create and populates first column
        TableColumn<TableRowData, String> firstColumn = new TableColumn<>("Category");
        firstColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCategory()));
        
        // Create and populates second column
        TableColumn<TableRowData, String> secondColumn = new TableColumn<>("Location");
        secondColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getLocation()));

        // Adds both columns to table
        TableView<TableRowData> tableView = new TableView<>();
        tableView.getColumns().addAll(firstColumn, secondColumn);
        tableView.setItems(tableData);
        
        // Stores scene and create back button to go back
        Scene previousScene = primaryStage.getScene();
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> primaryStage.setScene(previousScene));

        VBox root = new VBox(tableView, backBtn);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Assets");
        primaryStage.show();
        
    }

    
    // Defines asset type
    private void define(Stage primaryStage, String attribute) {
		// Create prompt label
		Label defLabel = new Label("Enter " + attribute.toLowerCase() + " name:");
		
		// Text field for entering name of category or location
		TextField defTextField = new TextField();
		defTextField.setMaxWidth(300);
		if (attribute.equals("Category")) {
			defTextField.setPromptText("E.g. Stationary, Enterntainment, Food ...");
		}
		else {
			defTextField.setPromptText("E.g. Living Room, Basement, Computer ...");
		}
		
		// Create buttons
		Button saveBtn = new Button("Submit");
		Button backBtn = new Button("Back");
		
		// Create UI layout 
		HBox btnRoot = new HBox(10);
		btnRoot.getChildren().addAll(saveBtn, backBtn);
		btnRoot.setAlignment(Pos.CENTER);
		
		VBox defRoot = new VBox(10);
		defRoot.getChildren().addAll(defLabel, defTextField, btnRoot);
		defRoot.setAlignment(Pos.CENTER);
		
		// Set buttons' actions
		saveBtn.setOnAction(event -> 
			{save(primaryStage, defRoot, attribute, defTextField);
			defTextField.clear();
			});
		Scene previousScene = primaryStage.getScene();
		backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
		
        // Set the scene and show
        primaryStage.setScene(new Scene(defRoot, 400, 200));
        primaryStage.setTitle("Add " + attribute);
    	primaryStage.show();
	}
    
    // Saves button action, shows warning
    private void save(Stage primaryStage, VBox box, String attribute, TextField textfield) {
    	// Removes labels everytime it is saved, except for the prompt label
    	box.getChildren().removeIf(node -> node instanceof Label && !((Label) node).getText().startsWith("Enter"));
    	
    	// Create warning labels
		Label emptyWarning = new Label(attribute + "'s name is required!");
		Label repeatedWarning = new Label(attribute + " already exist! Please enter a unique " + attribute.toLowerCase() + ".");
		emptyWarning.setVisible(false);
		repeatedWarning.setVisible(false);
    	
    	
    	// If nothing was entered into the textfield
    	if (textfield.getText().isEmpty()) {
    		emptyWarning.setVisible(true);
    		box.getChildren().add(emptyWarning);
    		return;
    	}
    	emptyWarning.setVisible(false);
    	String attName = textfield.getText();
    	textfield.clear();
       
        if (attribute.equals("Category")) {
            // Add category if search not found
            if (!searchAtt(categories, attName)) {
                categories.put(catId, new Category(attName));
                catId++;
                repeatedWarning.setVisible(false);
                return;
            }
        }
        if (attribute.equals("Location")) {
        	// Add location if search not found
            if (!searchAtt(locations, attName)) {
                locations.put(locId, new Location(attName));
                locId++;
                repeatedWarning.setVisible(false);
                return;
            }
        }
        
        // Tells user that attribute has already been define
        repeatedWarning.setVisible(true);
        box.getChildren().add(repeatedWarning);
    }
    
    // Searches attribute (categories or locations) and return id
    private <T extends Attribute> boolean searchAtt (HashMap<Integer, T> hashmap, String name) {
    	for (T value : hashmap.values()) {
    		if (value.getName().equals(name)) {
    			return true;
    		}
    	}
    	return false;
    } 

}
