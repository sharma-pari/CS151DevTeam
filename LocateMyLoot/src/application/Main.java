package application;

import javafx.application.Application;
import javafx.stage.Stage;
import widgets.MainView;
import widgets.WarningPopUp;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
    	WarningPopUp warning = new WarningPopUp(primaryStage);
    	warning.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
