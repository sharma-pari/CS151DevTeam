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
import javafx.stage.Stage;

/**
 * General AttributeView that Abstracts the 
 * Location and Category Views
 */
public class AttributeView extends VBox {

    private Label defLabel;
    private final TextField defTextField = new TextField();
    private final TextArea desc = new TextArea();
    private Label messageLabel = new Label();
    private Stage primaryStage;
    
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
    	defLabel = new Label("Enter " + this.attr.getClass().getName() + " name:");
    	defTextField.setMaxWidth(300);
    	desc.setMaxWidth(300);
    	desc.setPromptText("Enter Asset's description");

    	defTextField.setPromptText("E.g. Spoon, Phone, Medicine ...");

    	Button saveBtn = new Button("Save");
        Button backBtn = new Button("Back");

        HBox btnPanel = new HBox(10);
        btnPanel.getChildren().addAll(saveBtn, backBtn);
        btnPanel.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
        		defLabel, defTextField,
        		desc, btnPanel, messageLabel
        );

        saveBtn.setOnAction(event -> {
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
        primaryStage.setScene(new Scene(this, 500, 500));
        primaryStage.show();
    }
    
    //get the user entered values from Nodes to the modal - asset
    private void updateModal() {
    	attr.setName(defTextField.getText());
    	attr.setDesc(desc.getText());
    }
    
    //updates the nodes based on the Modal ( values in asset)
    private void updateView() {
    	clearMessage();
    	defTextField.setText(attr.getName());
    	desc.setText(attr.getDesc());
    }
    
    //clear the contents of the View
    private void clearNodes() {
        
    	defTextField.clear();
    	desc.clear();
    }
    
    private void showMessage(String message) {
    	messageLabel.setText(message);
    }
    
    private void clearMessage() {
    	messageLabel.setText(null);
    }
}
