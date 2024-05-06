package widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExpiredView {

    public void show() {
        // Create a new stage for the warning popup
        Stage view = new Stage();
        view.setTitle("Expired Warranties");

        Label messageLabel = new Label("Empty, add stuff for expired view");

        //"OK" button navigates to main page
        Button mainButton = new Button("OK");
        mainButton.setOnAction(event -> {
            view.close();
        });
        

        // Create layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(messageLabel, mainButton);

        // Set the scene
        Scene scene = new Scene(layout);
        view.setScene(scene);

        // Show the stage
        view.showAndWait();
    }
}