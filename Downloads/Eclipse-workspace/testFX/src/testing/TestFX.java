package testing;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Example");

        // Create label
        Label label = new Label("Enter your name:");

        // Create text field
        TextField textField = new TextField();

        // Create button
        Button btn = new Button();
        btn.setText("Submit");

        // Set action for button
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = textField.getText();
                label.setText("Hello, " + text + "!");
                textField.clear();
            }
        });

        // Create layout and add components
        VBox root = new VBox();
        root.getChildren().addAll(label, textField, btn);

        // Set the scene
        primaryStage.setScene(new Scene(root, 300, 200));

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
