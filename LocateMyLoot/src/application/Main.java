package application;

import java.io.*;
import java.io.File;
import java.util.*;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

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
        
        // Button for defining new asset
        Button defAssBtn = new Button("Add New Asset");
        defAssBtn.setOnAction(event -> define(primaryStage, "Asset"));
        
        // Button for defining new asset
        Button searchBtn = new Button("Search Asset");
        searchBtn.setOnAction(event -> search(primaryStage));
        
        //Button for exiting application
        Button exit = new Button("Exit");
		exit.setOnAction(e -> {
			System.exit(0);
		});

        // Create UI layout
        VBox root = new VBox(10);
        root.getChildren().addAll(label, assetBtn, defCatBtn, defLocBtn, defAssBtn, searchBtn, exit);
        root.setAlignment(Pos.CENTER);

        // Set the scene
        primaryStage.setScene(new Scene(root, 400, 300));

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
        List<Category> categories = readCategoriesFromCSV("categories.csv");
        List<Location> locations = readLocationsFromCSV("locations.csv");

        int maxCount = Math.max(categories.size(), locations.size());

        for (int i = 0; i < maxCount; i++) {
            String categoryName = i < categories.size() ? categories.get(i).getName() : "";
            String locationName = i < locations.size() ? locations.get(i).getName() : "";
            tableData.add(new TableRowData(categoryName, locationName));
        }

        // Create and populate first column
        TableColumn<TableRowData, String> firstColumn = new TableColumn<>("Category");
        firstColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCategory()));

        // Create and populate second column
        TableColumn<TableRowData, String> secondColumn = new TableColumn<>("Location");
        secondColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getLocation()));

        // Add both columns to table
        TableView<TableRowData> tableView = new TableView<>();
        tableView.getColumns().addAll(firstColumn, secondColumn);
        tableView.setItems(tableData);

        // Store scene and create back button to go back
        Scene previousScene = primaryStage.getScene();
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> primaryStage.setScene(previousScene));

        VBox root = new VBox(tableView, backBtn);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Assets");
        primaryStage.show();
    }
    
    // Search asset
    private void search(Stage primaryStage) {
    	Label searchLabel = new Label("Enter asset name");
    	Label warning = new Label("Asset does not exist!");
    	warning.setVisible(false);
    	
    	// Text field for entering asset name
    	TextField text = new TextField();
    	text.setMaxWidth(300);
    	text.setPromptText("E.g. Spoon, Phone, Medicine ...");
  
    	Button edit = new Button("Edit");
    	Button del = new Button("Delete");
    	
    	// Button to enter search
    	Button enter = new Button("Enter");
    	enter.setOnAction(event -> {
    		if (searchAsset("asset.csv", text.getText())) {
    			warning.setVisible(false);
    			
    			HBox btnFoundRoot = new HBox(10);
    			btnFoundRoot.getChildren().addAll(edit, del);
    			btnFoundRoot.setAlignment(Pos.CENTER);
    			
    	    	// Delete button function
    			
    			// TODO: Edit button function
    			
    		}
    		else {
    			warning.setVisible(true);
    		}
    		
    		
    		text.clear();
    	});
    	
		del.setOnAction(event -> {
			deleteAsset("/LocateMyLoot/asset.csv", text.getText());
		});
		text.clear();
    	
    	// This is the button to go back
    	Scene previousScene = primaryStage.getScene();
    	Button backBtn = new Button("Back");
    	backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
    	
    	HBox btnRoot = new HBox(10);
    	btnRoot.getChildren().addAll(enter, backBtn);
    	btnRoot.setAlignment(Pos.CENTER);
    	
    	VBox searchRoot = new VBox(10);
    	searchRoot.getChildren().addAll(searchLabel, text, btnRoot, warning, del);
        searchRoot.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(searchRoot, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search Asset");
        primaryStage.show();
    }
    
    // Deleting asset
    private void deleteAsset(String filePath, String assetName) {
        File file = new File(filePath);
        File tempFile = new File("temp.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter wr = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.contains(assetName)) {
                    continue;
                }
                wr.write(currentLine + System.lineSeparator());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Delete the original file
        if (!file.delete()) {
            System.out.println("Could not delete original file");
            return;
        }

        // Rename the temp file to the original file
        if (!tempFile.renameTo(new File(filePath))) {
            System.out.println("Could not rename temp file to the original file name");
        }
    }

    // Defines asset type
    private void define(Stage primaryStage, String attribute) {
        // Create prompt label
        Label defLabel = new Label("Enter " + attribute.toLowerCase() + " name:");

        // Text field for entering name of category or location
        TextField defTextField = new TextField();
        defTextField.setMaxWidth(300);
        
        // Create dropdowns for assets
        ComboBox<String> locComboBox = new ComboBox<>();
    	ComboBox<String> catComboBox = new ComboBox<>();
    	DatePicker purDateDP = new DatePicker();
        TextArea desc = new TextArea();
        TextField purVal = new TextField();
        DatePicker warExpDP = new DatePicker();
        
        
        desc.setMaxWidth(300);
        desc.setPromptText("Enter " + attribute.toLowerCase() + "'s description");
        
        // Prompt field for category name
        if (attribute.equals("Category")) {
            defTextField.setPromptText("E.g. Stationary, Entertainment, Food ...");
        } 
        // Prompt field for location name
        else if (attribute.equals("Location")) {
            defTextField.setPromptText("E.g. Living Room, Basement, Kitchen ...");
        } 
        else {
        	defTextField.setPromptText("E.g. Spoon, Phone, Medicine ...");
        	
            locComboBox.setMaxWidth(300);
            catComboBox.setMaxWidth(300);
            
            List<String> locations = CSVtoString("locations.csv");
            List<String> categories = CSVtoString("categories.csv");
            
            locComboBox.getItems().addAll(locations);
            locComboBox.setPromptText("Select a location");
            
            catComboBox.getItems().addAll(categories);
            catComboBox.setPromptText("Select a category");
            
            purDateDP.setMaxWidth(300);
            purDateDP.setPromptText("Choose a purchase date");

            purVal.setMaxWidth(300);
            purVal.setPromptText("Enter purchase value");
            
            warExpDP.setMaxWidth(300);
            warExpDP.setPromptText("Choose an expiration date");
            
        }

        // Create buttons
        Button saveBtn = new Button("Submit");
        Button backBtn = new Button("Back");

        // Create UI layout
        HBox btnRoot = new HBox(10);
        btnRoot.getChildren().addAll(saveBtn, backBtn);
        btnRoot.setAlignment(Pos.CENTER);

        VBox defRoot = new VBox(10);
        defRoot.getChildren().addAll(defLabel, defTextField, desc);
        if (attribute.equals("Asset")) {
        	defRoot.getChildren().addAll(locComboBox, catComboBox, purDateDP, purVal, warExpDP);
        }
        defRoot.getChildren().addAll(btnRoot);
        
        
        defRoot.setAlignment(Pos.CENTER);

        // Set buttons' actions
        saveBtn.setOnAction(event ->
        {
        	if (attribute.equals("Asset")) {
        		save(primaryStage, defRoot, defTextField, desc, locComboBox, catComboBox, purDateDP, purVal, warExpDP);
        	}
        	else {
        		save(primaryStage, defRoot, attribute, defTextField, desc);
        	}
            
        });
        Scene previousScene = primaryStage.getScene();
        backBtn.setOnAction(event -> primaryStage.setScene(previousScene));

        // Set the scene and show
        primaryStage.setScene(new Scene(defRoot, 500, 500));
        primaryStage.setTitle("Add " + attribute);
        primaryStage.show();
    }
    
    // Saves button action for asset
    private void save(Stage primaryStage, VBox box, TextField name, TextArea description, ComboBox locComboBox,
    		          ComboBox catComboBox, DatePicker purDateDP, TextField purVal, DatePicker warExpDP) {
    	box.getChildren().removeIf(node -> node instanceof Label && !((Label) node).getText().startsWith("Enter"));
        // Create warning labels
        Label emptyWarning = new Label("Asset's name is required!");
        Label repeatedWarning = new Label("Asset already exists! Please enter a unique asset.");
        emptyWarning.setVisible(false);
        repeatedWarning.setVisible(false);

        // If nothing was entered into the textfield
        if (name.getText().isEmpty()) {
            emptyWarning.setVisible(true);
            return;
        }
        emptyWarning.setVisible(false);
        String attName = name.getText();
        String desc = description.getText();
        String loc = locComboBox.getValue().toString();
        String cat = catComboBox.getValue().toString();
        String purDate = purDateDP.getValue().toString();
        String purValue = purVal.getText();
        String warExp = warExpDP.getValue().toString();
        
        name.clear();
        description.clear();
        locComboBox.setValue(null);
        catComboBox.setValue(null);
        purDateDP.setValue(null);
        purVal.clear();
        warExpDP.setValue(null);
        
        if (!searchAtt("asset.csv", attName)) {
            writeAttributeToCSV("asset.csv", attName + "," + desc + "," + loc + "," + cat + "," + purDate + "," + purValue + "," + warExp);
            repeatedWarning.setVisible(false);
            return;
        }

        // Tells user that attribute has already been defined
        repeatedWarning.setVisible(true);
        box.getChildren().add(repeatedWarning);
    }

    // Saves button action, shows warning 
    private void save(Stage primaryStage, VBox box, String attribute, TextField name, TextArea description) {
    	box.getChildren().removeIf(node -> node instanceof Label && !((Label) node).getText().startsWith("Enter"));
        // Create warning labels
        Label emptyWarning = new Label(attribute + "'s name is required!");
        Label repeatedWarning = new Label(attribute + " already exists! Please enter a unique " + attribute.toLowerCase() + ".");
        emptyWarning.setVisible(false);
        repeatedWarning.setVisible(false);

        // If nothing was entered into the textfield
        if (name.getText().isEmpty()) {
            emptyWarning.setVisible(true);
            return;
        }
        emptyWarning.setVisible(false);
        String attName = name.getText();
        String desc = description.getText();
        if (desc.isEmpty()) {
        	desc = "no description was added";
        }
        name.clear();
        description.clear();

        if (attribute.equals("Category")) {
            // Add category if search not found
            if (!searchAtt("categories.csv", attName)) {
                writeAttributeToCSV("categories.csv", attName + "," + desc);
                repeatedWarning.setVisible(false);
                return;
            }
        }
        if (attribute.equals("Location")) {
            // Add location if search not found
            if (!searchAtt("locations.csv", attName)) {
                writeAttributeToCSV("locations.csv", attName + "," + desc);
                repeatedWarning.setVisible(false);
                return;
            }
        }

        // Tells user that attribute has already been defined
        repeatedWarning.setVisible(true);
        box.getChildren().add(repeatedWarning);
    }

    // Searches attribute (categories or locations) and return if exists
    private boolean searchAtt(String csvFile, String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean searchAsset(String csvFile, String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[0].contains(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Reads categories from CSV file
    private List<Category> readCategoriesFromCSV(String fileName) {
        List<Category> categories = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                categories.add(new Category(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Reads locations from CSV file
    private List<Location> readLocationsFromCSV(String fileName) {
        List<Location> locations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                locations.add(new Location(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }
    
    private List<String> CSVtoString(String fileName) {
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Location temp = new Location(line);
                names.add(temp.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    // Writes an attribute to CSV file
    private void writeAttributeToCSV(String fileName, String attributeName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(attributeName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
