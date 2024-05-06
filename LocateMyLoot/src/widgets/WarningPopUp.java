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
import java.util.List;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

public class WarningPopUp {

    public void show() {
        // Create a new stage for the warning popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Warning");

        Label messageLabel = new Label();
        updateMessage(messageLabel);

        //"OK" button navigates to main page
        Button mainButton = new Button("OK");
        mainButton.setOnAction(event -> {
            popupStage.close();
        });
        
        //"Show Me" button navigates to expired warranties page
        Button expiredButton = new Button("Show Me");
        expiredButton.setOnAction(event -> {
            popupStage.close();
            showExpiredView();
        });

        // Create layout
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(mainButton, expiredButton);
        
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(300);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(messageLabel, buttonBox);

        // Set the scene
        Scene scene = new Scene(layout);
        popupStage.setScene(scene);

        // Show the stage
        popupStage.showAndWait();
    }
    
    //Show ExpiredView
    private void showExpiredView() {
        ExpiredView expiredView = new ExpiredView();
        expiredView.show();
    }
    
    //Update warning message (based on whether or not there are expired warranties)
    private void updateMessage(Label messageLabel) {
        // Check for expired warranties
        boolean expiredWarrantiesFound = checkForExpiredWarranties();

        // Update message based on whether expired warranties are found
        Platform.runLater(() -> {
            if (expiredWarrantiesFound) {
                messageLabel.setText("You have expired warranties.");
            } else {
                messageLabel.setText("No expired warranties found.");
            }
        });
    }
    
    private boolean checkForExpiredWarranties() {
    	List<Asset> assets = AssetHelper.readAssetsFromCSV();
        LocalDate currentDate = LocalDate.now();
        boolean expiredWarrantiesFound = false;

        for (Asset asset : assets) {
        	if (asset.getWarExDate() != null && !asset.getWarExDate().isEmpty()) {
                LocalDate warrantyExpiryDate = LocalDate.parse(asset.getWarExDate());
                if (currentDate.isAfter(warrantyExpiryDate)) {
                    expiredWarrantiesFound = true;
                    break; // exit once expired warranty is found
                }
            }
        }
        return expiredWarrantiesFound;
    }
    
}