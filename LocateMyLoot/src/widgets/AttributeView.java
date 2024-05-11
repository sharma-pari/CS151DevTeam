package widgets;


import application.Attribute;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * General AttributeView that Abstracts the 
 * Location and Category Views
 */
public class AttributeView extends VBox {

    private Label defLabel;
    private final TextField nameTextField = new TextField();
    private final TextArea desc = new TextArea();
    private Label messageLabel = new Label();
	private Button saveBtn = new Button("Save");
    private Button backBtn = new Button("Back");
    
    private Stage primaryStage;
    
    private Label warningLabel;
    
    //Modal for the view
    private Attribute attr = null;

    public AttributeView(Stage primaryStage, Attribute attr) {
        super(10);
        this.primaryStage = primaryStage;
        this.attr = attr;
        createView();
    }
    
    public void setAttribute(Attribute attr) {
    	this.attr = attr;
    	updateView(); //update the view with the new asset
    }

    private void createView() {
    	createWarningMarkers();
    	
    	defLabel = new Label("Enter " + this.attr.getClass().getSimpleName().toLowerCase() + " name:");
    	
    	nameTextField.setMaxWidth(300);
    	HBox box1 = new HBox(10);
    	box1.setAlignment(Pos.CENTER);
    	box1.getChildren().addAll(nameTextField,warningLabel);
    	
    	desc.setMaxWidth(300);
    	desc.setPromptText("Enter " + this.attr.getClass().getSimpleName().toLowerCase() + "'s description");

    	nameTextField.setPromptText("E.g. Spoon, Phone, Medicine ...");

        HBox btnPanel = new HBox(10);
        btnPanel.getChildren().addAll(saveBtn, backBtn);
        btnPanel.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
        		defLabel, box1,
        		desc, btnPanel, messageLabel
        );

        saveBtn.setOnAction(event -> {
        	clearMessaagesAndWarnings(); //clear previous messages or warnings if any
        	if(!checkMandatoryFields())
        	{
        		return;
        	}
        	updateModal();
        	String message = attr.save();
        	clearNodes();
        	showMessage(message);
        });
        
    	Scene previousScene = primaryStage.getScene();
    	backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
    }
    
    public void show(String title) {
    	primaryStage.setTitle(title);
        this.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(this, MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT));
        primaryStage.show();
    }
    
    //get the user entered values from Nodes to the modal - asset
    private void updateModal() {
    	attr.setName(nameTextField.getText().replace(",", " ")); //strip off commas if any
    	attr.setDesc(desc.getText().replace(",", " ")); //strip off commas if any
    }
    
    //updates the nodes based on the Modal ( values in asset)
    private void updateView() {
    	clearMessaagesAndWarnings();
    	nameTextField.setText(attr.getName());
    	desc.setText(attr.getDesc());
    }
    
    private void createWarningMarkers() {
    	warningLabel = new Label("*");
    	warningLabel.setTextFill(Color.RED); // Set text color to red
    	warningLabel.setFont(new Font(20)); // Set font size to 16
    	warningLabel.setVisible(false); 
    }
    
    private boolean checkMandatoryFields() {
    	
    	boolean mndatoryFields = true;
        if(nameTextField.getText().isEmpty()) {
        	warningLabel.setVisible(true);
        	mndatoryFields = false;
        }
        showWarning("Fields marked with * are Manadatory");
        return mndatoryFields;
    }

    
    //clear the contents of the View
    private void clearNodes() {
        
    	nameTextField.clear();
    	desc.clear();
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
		warningLabel.setVisible(false);
    }
}
