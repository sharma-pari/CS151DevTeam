package application;

import javafx.application.Application;
import javafx.stage.Stage;
import widgets.MainView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

    	MainView mainView = new MainView(primaryStage);
    	mainView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
