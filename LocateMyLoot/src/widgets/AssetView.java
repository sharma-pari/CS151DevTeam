package widgets;

import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AssetView extends VBox {

    private Label defLabel = new Label("Enter Asset's name:");
    private List<Label> warningLabels = new ArrayList<Label>();
    private final TextField txtName = new TextField();
    private final ComboBox<String> locComboBox = new ComboBox<>();
    private final ComboBox<String> catComboBox = new ComboBox<>();
    private final DatePicker purDateDP = new DatePicker();
    private final TextArea desc = new TextArea();
    private final TextField purVal = new TextField();
    private final DatePicker warExpDP = new DatePicker();
    private Label messageLabel = new Label();
    private Button saveBtn = new Button("Save");
    private Button backBtn = new Button("Back");
    
    List<Location> locations;
    List<Category> categories;
    
    private Stage primaryStage;
    
    //Modal for the view
    private Asset asset = null;
    private Asset prevAsset = null;

    public AssetView(Stage primaryStage, Boolean editable) {
        super(10);
        this.primaryStage = primaryStage;
        
        txtName.setEditable(editable);
    	desc.setEditable(editable);
        locComboBox.getEditor().setEditable(editable);
        catComboBox.getEditor().setEditable(editable);
        purDateDP.setEditable(editable);
        purVal.setEditable(editable);
        warExpDP.setEditable(editable);
        
        if(!editable) {
        	defLabel = new Label("Asset's information:");
        	locComboBox.setOnShown(event -> locComboBox.hide());
        	catComboBox.setOnShown(event -> catComboBox.hide());
        	purDateDP.setOnShown(event -> purDateDP.hide());
        	warExpDP.setOnShown(event -> warExpDP.hide());
        }
        createView();
    }
    
    public void setAsset(Asset asset) {
    	this.asset = asset;
    	updateView(); //update the view with the new asset
    }
    
    private void createWarningMarkers() {
    	for(int i = 0; i < 3; i++) {
	    	Label warning1 = new Label("*");
	    	warning1.setTextFill(Color.RED); // Set text color to red
	    	warning1.setFont(new Font(20)); // Set font size to 16
	    	warning1.setVisible(false); //initially no warning markers are shown
	    	warningLabels.add(warning1);
    	}
    }

    private void createView() {
    	createWarningMarkers();
    	
    	txtName.setMaxWidth(300);
    	txtName.setPromptText("E.g. Spoon, Phone, Medicine ...");
    	
    	HBox box1 = new HBox(10);
    	box1.setAlignment(Pos.CENTER);
    	box1.getChildren().addAll(txtName,warningLabels.get(0));
    	
    	desc.setMaxWidth(300);
    	desc.setPromptText("Enter Asset's description");
    	
        locComboBox.setMaxWidth(300);
        catComboBox.setMaxWidth(300);
        
        locations = CSVHelper.CSVtoLocations(Location.LOCATIONS_FILENAME);
        categories = CSVHelper.CSVtoCategories(Category.CATEGORIES_FILENAME);
        
        locComboBox.getItems().addAll(getLocationNames());
        locComboBox.setPromptText("Select a location");
        
    	HBox box2 = new HBox(10);
    	box2.setAlignment(Pos.CENTER);
    	box2.getChildren().addAll(locComboBox,warningLabels.get(1));
        
        catComboBox.getItems().addAll(getCategoryNames());
        catComboBox.setPromptText("Select a category");
       
    	HBox box3 = new HBox(10);
    	box3.setAlignment(Pos.CENTER);
    	box3.getChildren().addAll(catComboBox,warningLabels.get(2));
        
        purDateDP.setMaxWidth(300);
        purDateDP.setPromptText("Choose a purchase date");

        purVal.setMaxWidth(300);
        purVal.setPromptText("Enter purchase value");
        
        warExpDP.setMaxWidth(300);
        warExpDP.setPromptText("Choose an expiration date");
        
        HBox btnPanel = new HBox(10);
        btnPanel.getChildren().addAll(saveBtn, backBtn);
        btnPanel.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
        		defLabel, box1,
        		desc, box2,
        		box3, purDateDP,
        		purVal, warExpDP,
        		btnPanel, messageLabel
        );

        saveBtn.setOnAction(event -> {
        	clearMessaagesAndWarnings(); //clear previous messages or warnings if any
        	if(!checkMandatoryFields()) {
        		return;
        	}
        	updateModal();
        	if(prevAsset == null) {
        		AssetHelper.save(asset);
        		asset = null;
        	}
        	else {
        		AssetHelper.updateAsset(prevAsset, asset);
        	}
        	clearNodes();
        	showMessage("Asset saved sucessfully");
        });
        
    	Scene previousScene = primaryStage.getScene();
    	backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
    }
    
    public void show() {
        this.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(this, MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT));
        primaryStage.setTitle("Add Asset");
        primaryStage.show();
    }
    
    private boolean checkMandatoryFields() {
    	
    	boolean mndatoryFields = true;
        if(txtName.getText().isEmpty()) {
        	warningLabels.get(0).setVisible(true);
        	mndatoryFields = false;
        }
        if(locComboBox.getValue() == null || locComboBox.getValue().toString().isEmpty()) {
        	warningLabels.get(1).setVisible(true);
        	mndatoryFields = false;
        }
        if(catComboBox.getValue() == null || catComboBox.getValue().toString().isEmpty()) {
        	warningLabels.get(2).setVisible(true);
        	mndatoryFields = false;
        }
        showWarning("Fields marked with * are Manadatory");
        return mndatoryFields;
    }
    
    //get the user entered values from Nodes to the modal - asset
    private void updateModal() {
    	
    	if(asset == null) {
    		prevAsset = asset;
    		this.asset = new Asset();
    	}
    	else {
    		prevAsset = new Asset(asset);
    	}
		
        asset.setName(txtName.getText().replace(",", " "));  //strip off commas
        asset.setDescr(desc.getText().replace(",", " ")); //strip off commas
        asset.setLocation(getUserSelectedLocation());
        asset.setCategory(getUserSelectedCategory());
        if(purDateDP.getValue() != null) {
        	asset.setPurchaseDate(purDateDP.getValue().toString());
        }
        else {
        	asset.setPurchaseDate("");
        }
        asset.setPurChaseValue((purVal.getText()));
        if(warExpDP.getValue() != null) {
        	asset.setWarExDate(warExpDP.getValue().toString());
        }
        else {
        	asset.setWarExDate("");
        }
    }
    
    //updates the nodes based on the Modal ( values in asset)
    private void updateView() {
    	clearMessaagesAndWarnings();
    	txtName.setText(asset.getName());
    	desc.setText(asset.getDescr());
    	locComboBox.setValue(asset.getLocation().getName());
    	catComboBox.setValue(asset.getCategory().getName());
    	
    	if(!asset.getPurchaseDate().isEmpty()) {
	    	LocalDate purDate = LocalDate.parse(asset.getPurchaseDate()); // Parse the string into a LocalDate object
	    	purDateDP.setValue(purDate); // Set the date in the DatePicker
    	}

    	purVal.setText(asset.getPurChaseValue());
    	
    	String warrantyExpDateStr = asset.getWarExDate();
        if (warrantyExpDateStr != null && !warrantyExpDateStr.isEmpty()) {
            LocalDate warExpDate = LocalDate.parse(warrantyExpDateStr);
            warExpDP.setValue(warExpDate);
        }
    }
    
    //clear the contents of the View
    private void clearNodes() {
        
    	txtName.clear();
    	desc.clear();
        //locComboBox.setValue(null);
        locComboBox.setPromptText("Select a location");
        catComboBox.setPromptText("Select a category");
        //catComboBox.setValue(null);
        purDateDP.setValue(null);
        purVal.clear();
        warExpDP.setValue(null);
    }
    
    private void showWarning(String message) {
    	messageLabel.setText(message);
    	messageLabel.setTextFill(Color.RED);
    	messageLabel.setFont(new Font(16)); // Set font size to 16
    }
    
    private void showMessage(String message) {
    	messageLabel.setText(message);
    	messageLabel.setTextFill(Color.BLACK);
    	messageLabel.setFont(new Font(16)); // Set font size to 16
    }
    
    private void clearMessaagesAndWarnings() {
    	messageLabel.setText(null);
    	for(Label lbl : warningLabels) {
    		lbl.setVisible(false);
    	}
    }

    private List<String> getLocationNames() {
    	List<String> locNames = new ArrayList<>();
    	
    	for(Location loc : locations) {
    		locNames.add(loc.getName());
    	}
    	
    	return locNames;
    }
    
    private List<String> getCategoryNames() {
    	List<String> catNames = new ArrayList<>();
    	
    	for(Category cat : categories) {
    		catNames.add(cat.getName());
    	}
    	
    	return catNames;
    }
    
    private Location getUserSelectedLocation() {
    	
    	String locSelected = locComboBox.getValue().toString();
    	
    	for( Location loc : locations) {
    		if(locSelected.equals(loc.getName())) {
    			return loc;
    		}
    	}
    	return null;
    }
    
    private Category getUserSelectedCategory() {
    	String catSelected = catComboBox.getValue().toString();
    	
    	for( Category cat : categories) {
    		if(catSelected.equals(cat.getName())) {
    			return cat;
    		}
    	}
    	return null;
    }
}
