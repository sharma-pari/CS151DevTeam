package widgets;

import java.time.LocalDate;
import java.util.List;

import application.Asset;
import application.AssetHelper;
import application.CSVHelper;
import application.Category;
import application.Location;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AssetView extends VBox {

    private Label defLabel = new Label("Enter Asset's name:");
    private final TextField defTextField = new TextField();
    private final ComboBox<String> locComboBox = new ComboBox<>();
    private final ComboBox<String> catComboBox = new ComboBox<>();
    private final DatePicker purDateDP = new DatePicker();
    private final TextArea desc = new TextArea();
    private final TextField purVal = new TextField();
    private final DatePicker warExpDP = new DatePicker();
    private Label messageLabel = new Label();
    private Stage primaryStage;
    
    //Modal for the view
    private Asset asset = null;

    public AssetView(Stage primaryStage) {
        super(10);
        this.primaryStage = primaryStage;
        
        createView();
    }
    
    public void setAsset(Asset asset) {
    	this.asset = asset;
    	updateView(); //update the view with the new asset
    }

    private void createView() {
    	defTextField.setMaxWidth(300);
    	desc.setMaxWidth(300);
    	desc.setPromptText("Enter Asset's description");

    	defTextField.setPromptText("E.g. Spoon, Phone, Medicine ...");
    	
        locComboBox.setMaxWidth(300);
        catComboBox.setMaxWidth(300);
        
        List<String> locations = CSVHelper.CSVtoString("locations.csv");
        List<String> categories = CSVHelper.CSVtoString("categories.csv");
        
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
        
 
        Button saveBtn = new Button("Save");
        Button backBtn = new Button("Back");

        HBox btnPanel = new HBox(10);
        btnPanel.getChildren().addAll(saveBtn, backBtn);
        btnPanel.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
        		defLabel, defTextField,
        		desc, locComboBox,
        		catComboBox, purDateDP,
        		purVal, warExpDP,
        		btnPanel, messageLabel
        );

        saveBtn.setOnAction(event -> {
        	updateModal();
        	AssetHelper.save(asset);
        	clearNodes();
        	showMessage("Asset saved sucessfully");
        });
        
    	Scene previousScene = primaryStage.getScene();
    	backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
    }
    
    public void show() {
        this.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(this, 500, 500));
        primaryStage.setTitle("Add Asset");
        primaryStage.show();
    }
    
    //get the user entered values from Nodes to the modal - asset
    private void updateModal() {
    	if(asset == null) {
    		this.asset = new Asset();
    	}
        asset.setName(defTextField.getText());
        asset.setDescr(desc.getText());
        asset.setLocation(new Location(locComboBox.getValue().toString(),""));
        asset.setCategory(new Category(catComboBox.getValue().toString(),""));
        asset.setPurchaseDate(purDateDP.getValue().toString());
        asset.setPurChaseValue(purVal.getText());
        asset.setWarExDate(warExpDP.getValue().toString());
    }
    
    //updates the nodes based on the Modal ( values in asset)
    private void updateView() {
    	clearMessage();
    	defTextField.setText(asset.getName());
    	desc.setText(asset.getDescr());
    	locComboBox.setValue(asset.getLocation().getName());
    	catComboBox.setValue(asset.getCategory().getName());
    	
    	LocalDate purDate = LocalDate.parse(asset.getPurchaseDate()); // Parse the string into a LocalDate object
    	purDateDP.setValue(purDate); // Set the date in the DatePicker

    	purVal.setText(asset.getPurChaseValue());
    	
    	LocalDate warExpDate = LocalDate.parse(asset.getPurchaseDate()); // Parse the string into a LocalDate object
    	warExpDP.setValue(warExpDate);
    }
    
    //clear the contents of the View
    private void clearNodes() {
        
    	defTextField.clear();
    	desc.clear();
        locComboBox.setValue(null);
        catComboBox.setValue(null);
        purDateDP.setValue(null);
        purVal.clear();
        warExpDP.setValue(null);
    }
    
    private void showMessage(String message) {
    	messageLabel.setText(message);
    }
    
    private void clearMessage() {
    	messageLabel.setText(null);
    }
}
