package widgets;

import java.util.stream.Stream;

import application.AttributeType;
import application.Category;
import application.Location;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends VBox {
	
	public static final int MAIN_WINDOW_WIDTH = 1000;
	public static final int MAIN_WINDOW_HEIGHT = 550;
	
	private Stage primaryStage;
	private Label label;
	private Button assetBtn;
	private Button defCatBtn;
	private Button defLocBtn;
	private Button defAssBtn;
	private Button searchBtn;
	private Button exit;
	
	public MainView(Stage primaryStage) {
		
		super(10);
		this.primaryStage = primaryStage;
		
	}
	
	public void show() {
        primaryStage.setTitle("Welcome to Locate My Loot!");

        // Create label
        label = new Label("What would you like to do?");

        // Button for viewing current assets, or view by category or location
        assetBtn = new Button("View All Assets");
        assetBtn.setOnAction(event -> viewAssets());

        // Button for defining new category
        defCatBtn = new Button("Add New Category");
        defCatBtn.setOnAction(event -> showAttributeView(AttributeType.CATEGORY));

        // Button for defining new location
        defLocBtn = new Button("Add New Location");
        defLocBtn.setOnAction(event -> showAttributeView(AttributeType.LOCATION));
        
        // Button for defining new asset
        defAssBtn = new Button("Add New Asset");
        defAssBtn.setOnAction(event -> showAssetView("Asset"));
        
        // Button for defining new asset
        searchBtn = new Button("Search Asset");
        searchBtn.setOnAction(event -> showSearchView());
        
        //Button for exiting application
        exit = new Button("Exit");
		exit.setOnAction(e -> {
			System.exit(0);
		});

        // Create UI layout
        this.getChildren().addAll(label, assetBtn, searchBtn, defCatBtn, defLocBtn, defAssBtn, exit);
        this.setAlignment(Pos.CENTER);
        

        // Set the preferred width for each Button to the maximum width
        Stream.of(assetBtn, defCatBtn, defLocBtn, defAssBtn, searchBtn, exit )
              .forEach(button -> button.setPrefWidth(200));

        // Set the scene
        primaryStage.setScene(new Scene(this, MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));

        // Show the stage
        primaryStage.show();

	}
	
	
    // Shows assets by attributes
    private void viewAssets() {

    	AssetsListView assetView = new AssetsListView(primaryStage);
    	assetView.show();
    }
    
    private void showAttributeView(AttributeType atrType) {
        if(atrType == AttributeType.CATEGORY) {
        	AttributeView categoryView = new AttributeView(primaryStage, new Category());
        	categoryView.show("Add Category");
        }
        else if(atrType == AttributeType.LOCATION) {
           	AttributeView locationView = new AttributeView(primaryStage, new Location());
           	locationView.show("Add Location");
        }
    }
    
    //Show AssetView
    private void showAssetView(String attribute) {
        AssetView assetView = new AssetView(primaryStage, true);
        assetView.show();
    }
    
    // Search asset
    private void showSearchView() {
    	SearchView search = new SearchView(primaryStage);
    	search.show();
    }    
    
   
}
