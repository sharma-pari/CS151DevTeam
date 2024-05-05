package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AssetHelper {
	
	private static final String assetFileName = "asset.csv";
	
	
    //parse the asset line and create and asset
    private static Asset parseAsset(String assetLine) {
    	String[] items = assetLine.split(",", -1);
    	if(items.length > 0) {
	    	Asset asset = new Asset();
	    	asset.setName((items[0] != null ? items[0] : "")); 
	    	asset.setDescr((items[1] != null ? items[1] : ""));
	    	asset.setLocation(new Location((items[2] != null ? items[2] : ""), (items[3] != null ? items[3] : "")));
	    	asset.setCategory(new Category((items[4] != null ? items[4] : ""), (items[5] != null ? items[5] : "")));
	    	asset.setPurchaseDate((items[6] != null ? items[6] : ""));
	    	asset.setPurChaseValue((items[7] != null ? items[7] : ""));
	    	asset.setWarExDate((items[8] != null ? items[8] : ""));
	    	return asset;
    	}
    	return null;
    }
    
    public static void save(Asset asset) {
    	
    	FileWriter writer = null;
    	try {
    		writer = new FileWriter(assetFileName, true); // Append mode
            writer.write(asset.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
	        if (writer != null) {
	            try {
	                writer.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
        }
    }
	
	public static boolean deleteAsset(Asset asset) {
		
		boolean statusDelete = true;
		
        try {
            // Read the original file
            File inputFile = new File(assetFileName);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            // Copy all lines except the one to be deleted to a temporary file
            while ((currentLine = reader.readLine()) != null) {
                Asset assetRead = parseAsset(currentLine);
                
                if (asset.equals(assetRead)) { //calls the equals method in Asset class
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();

            // Rename the temporary file to the original file name
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            } else {
            	statusDelete = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    	return statusDelete;
    }
	
	public static boolean updateAsset(Asset oldAsset, Asset updatedAsset) {
		
		boolean statusUpdate = true;
		
        try {
            // Read the original file
            File inputFile = new File(assetFileName);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            // Copy all lines except the one to be deleted to a temporary file
            while ((currentLine = reader.readLine()) != null) {
                Asset assetRead = parseAsset(currentLine);
                
                if (oldAsset.equals(assetRead)) { //calls the equals method in Asset class
                	currentLine = updatedAsset.toString();
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();

            // Rename the temporary file to the original file name
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            } else {
            	statusUpdate = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    	return statusUpdate;
    }
	
    // Reads categories from CSV file
    public static List<Asset> readAssetsFromCSV() {
        List<Asset> assets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(assetFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
            	Asset asset = parseAsset(line);
            	if(asset != null) {
            		assets.add(parseAsset(line));
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assets;
    }
    
    public static List<Asset> searchAsset(String name) {
    	List<Asset> searchResults = new ArrayList<Asset>();
    	
        try (BufferedReader br = new BufferedReader(new FileReader(assetFileName))) {
        	
            String line;
            while ((line = br.readLine()) != null) {
            	String assetName = line.split(",", -1)[0];
                if (assetName.toLowerCase().contains(name.toLowerCase())) { //case insensitive search
                    Asset asset = parseAsset(line);
                    searchResults.add(asset);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
