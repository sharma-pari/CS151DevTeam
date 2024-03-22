package testing;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestFX extends Application {
	private Scene previousScene;

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Welcome to Locate My Loot!");
    	
    	// Create label
    	Label label = new Label("What would you like to do?");
    	
    	// Button for viewing current assets, or view by category or location
    	Button assetBtn = new Button("View All Assets");
    	
    	// Button for defining new category
    	Button defCatBtn = new Button("Define New Category");
    	defCatBtn.setOnAction(event -> define(primaryStage, "category"));
    	
    	// Button for defining new category
    	Button defLocBtn = new Button("Define New Location");
    	defLocBtn.setOnAction(event -> define(primaryStage, "location"));
    	
    	// Create UI layout
        VBox root = new VBox(10);
        root.getChildren().addAll(label, assetBtn, defCatBtn, defLocBtn);
        root.setAlignment(Pos.CENTER);

        // Set the scene
        primaryStage.setScene(new Scene(root, 300, 200));

        // Show the stage
    	primaryStage.show();
    }
    
    // Method for defining asset type
    public void define(Stage primaryStage, String funct) {
		// Create label and text field
		Label label = new Label("Enter " + funct + " name:");
		TextField textField = new TextField();
		
		// Create buttons for saving and going back
		Button saveBtn = new Button("Save");
		Button backBtn = new Button("Back");
		Scene previousScene = primaryStage.getScene();
		backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
		
		// Create UI layout 
		VBox catRoot = new VBox(10);
		catRoot.getChildren().addAll(label, textField, saveBtn, backBtn);
		catRoot.setAlignment(Pos.CENTER);
		
        // Set the scene
        primaryStage.setScene(new Scene(catRoot, 300, 200));

        // Show the stage
    	primaryStage.show();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
