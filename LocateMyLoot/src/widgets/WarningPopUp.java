package widgets;

import application.Asset;
import application.AssetHelper;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

public class WarningPopUp extends VBox {
	Stage primaryStage;
	
	public WarningPopUp(Stage primaryStage) {
		super(10);
		this.primaryStage = primaryStage;
	}

    public void show() {
    	primaryStage.setTitle("Warning");
    	
    	// Message label that will change based on expired assets
        Label messageLabel = new Label();
        updateMessage(messageLabel);

        // "OK" button navigates to main page
        Button mainButton = new Button("OK");
        mainButton.setOnAction(event -> {
        	MainView mainView = new MainView(primaryStage);
        	mainView.show();
        });
        
        // "Show Me" button navigates to expired warranties page
        Button expiredButton = new Button("Show Me");
        expiredButton.setOnAction(event -> {
            //popupStage.close();
            showExpiredView();
        });

        // Create layout
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(mainButton, expiredButton);

	    this.setAlignment(Pos.CENTER);
	    this.setPrefWidth(300);
	    this.setPadding(new Insets(20));
	    this.getChildren().addAll(messageLabel, buttonBox);
	    
	    Scene scene = new Scene(this);
	    primaryStage.setScene(scene);
	    primaryStage.show();
    }
    
    // Show ExpiredView
    public void showExpiredView() {
    	AssetsListView expiredView = new AssetsListView(primaryStage, true);
        expiredView.show();
    }
    
    // Update warning message (based on whether or not there are expired warranties)
    private void updateMessage(Label messageLabel) {

        // Update message based on whether expired warranties are found
        Platform.runLater(() -> {
            if (checkForExpiredWarranties().size() != 0) {
                messageLabel.setText("You have expired warranties.");
            } else {
                messageLabel.setText("No expired warranties found.");
            }
        });
    }
    
    // Checks for expired assets
    private List<Asset> checkForExpiredWarranties() {
    	List<Asset> assets = AssetHelper.readAssetsFromCSV();
    	List<Asset> expAssets = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (Asset asset : assets) {
        	if (asset.getWarExDate() != null && !asset.getWarExDate().isEmpty()) {
                LocalDate warrantyExpiryDate = LocalDate.parse(asset.getWarExDate());
                if (currentDate.isAfter(warrantyExpiryDate)) {
                	expAssets.add(asset);

                }
            }
        }
        return expAssets;
    }
    
}