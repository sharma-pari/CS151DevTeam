package testing;

import java.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestFX extends Application {
	
	private Scene previousScene;
	
	private static int catCount = 0;
	public static HashMap<Integer, Group> categories = new HashMap<>();
	
	private static int locCount = 0;
	public static HashMap<Integer, Group> locations = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Welcome to Locate My Loot!");
    	
    	// Create label
    	Label label = new Label("What would you like to do?");
    	
    	// Button for viewing current assets, or view by category or location
    	Button assetBtn = new Button("View All Assets");
    	
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
        primaryStage.setScene(new Scene(root, 300, 200));

        // Show the stage
    	primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    // Private helper methods
    
    // Method for defining asset type
    private void define(Stage primaryStage, String functName) {
		// Create prompt label
		Label defLabel = new Label("Enter " + functName + " name:");
		
		// Create warning labels
		Label empDefWarnLabel = new Label(functName + "'s name is required!");
		Label repDefWarnLabel = new Label(functName + " already exist! Please enter a unique " + functName + ".");
		empDefWarnLabel.setVisible(false);
		repDefWarnLabel.setVisible(false);
		
		// Text fiels for entering name of category or location
		TextField defTextField = new TextField();
		
		// Create buttons
		Button saveBtn = new Button("Submit");
		Button backBtn = new Button("Back");
		
		// Create UI layout 
		VBox root = new VBox(10);
		root.getChildren().addAll(defLabel, defTextField, saveBtn, backBtn);
		root.setAlignment(Pos.CENTER);
		
		// Set buttons' actions
		saveBtn.setOnAction(event -> save(primaryStage, root, functName, defTextField, empDefWarnLabel, repDefWarnLabel));
		Scene previousScene = primaryStage.getScene();
		backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
		
        // Set the scene
        primaryStage.setScene(new Scene(root, 300, 200));

        // Show the stage
    	primaryStage.show();
	}
    
    // Save button action, shows warning
    private void save(Stage primaryStage, VBox box, String functType, TextField functName, Label emptyWarning, Label repeatedWarning) {
    	
    	// If nothing was entered into the textfield
    	if (functName.getText().isEmpty()) {
    		emptyWarning.setVisible(true);
    		box.getChildren().add(emptyWarning);
    		return;
    	}
    	emptyWarning.setVisible(false);
    	String groupName = functName.getText();
       
        // I'll turn this into a private method in the future
        if (functType.equals("Category")) {
            // Add category if search not found
            if (search(categories, groupName) == -1) {
                Group group = new Group(groupName, functType);
                categories.put(catCount, group);
                
                repeatedWarning.setVisible(false);
                return;
            }
        }
        if (functType.equals("Location")) {
        	// Add location if search not found
            if (search(locations, groupName) == -1) {
                Group group = new Group(groupName, functType);
                locations.put(locCount, group);
                emptyWarning.setVisible(false);
                repeatedWarning.setVisible(false);
                return;
            }
        }
        
        repeatedWarning.setVisible(true);
        box.getChildren().add(repeatedWarning);
    }
    
    // Search hashmap and return id
    private int search (HashMap<Integer, Group> hashmap, String name) {
        for (Map.Entry<Integer, Group> entry : hashmap.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                int key = entry.getKey();
                return key;
            }
        }
    	return -1;
    } 

}
