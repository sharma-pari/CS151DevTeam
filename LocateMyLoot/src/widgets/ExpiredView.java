package widgets;

import java.util.List;
import java.util.stream.Collectors;

import application.Asset;
import application.AssetHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExpiredView extends VBox {
	
	private AssetTableView expiredAssetTable;
	Stage primaryStage;
	List<Asset> expAssets;
	
    public ExpiredView(Stage primaryStage, List<Asset> expAssets) {
    	super(10);
    	this.primaryStage = primaryStage;
    	this.expAssets = expAssets;
    	expiredAssetTable = new AssetTableView();
    }

    public void show() {

        //"OK" button navigates to main page
        Button mainBtn = new Button("OK");
        mainBtn.setOnAction(event -> {
        	MainView mainView = new MainView(primaryStage);
        	mainView.show();
        });
        
        populateTable();
        
        // A button to go back
    	Button backBtn = new Button("Back");
        Scene previousScene = primaryStage.getScene();
        backBtn.setOnAction(event -> primaryStage.setScene(previousScene));
        
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(mainBtn, backBtn);
        
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
    	this.getChildren().addAll(expiredAssetTable, hbox);

		Scene scene = new Scene(this, MainView.MAIN_WINDOW_WIDTH, MainView.MAIN_WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Assets");
        primaryStage.show();
    }
    
    // Populates expired asset's table
    private void populateTable() {    	
    	expiredAssetTable.setAssets(expAssets);
    	expiredAssetTable.refreshView();
    }
}