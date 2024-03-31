package application;

import java.io.*;
import java.util.HashMap;

public class DataHandler {

		
    static final String CATEGORIES_FILE = "categories.csv";
    static final String LOCATIONS_FILE = "locations.csv";
    private static final String CAT_ID_FILE = "cat_ids.txt";
    private static final String LOC_ID_FILE = "loc_ids.txt";

    
    public static int loadCatId() {
        int catId;
        try (BufferedReader reader = new BufferedReader(new FileReader(CAT_ID_FILE))) {
        	catId = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            // This is for when the file doesn't exist 
        	// (like running the program for the first time ever)
        	// Or anything other error. 
        	catId = 0; // Set catId to default value
        }
        
        return catId;
    }
    
    public static int loadLocId() {
        int locId;
        try (BufferedReader reader = new BufferedReader(new FileReader(LOC_ID_FILE))) {
        	locId = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            // This is for when the file doesn't exist 
        	// (like running the program for the first time ever)
        	// Or anything other error. 
        	locId = 0; // Set catId to default value
        }
        
        return locId;
    }
    
    public static HashMap<Integer, Category> loadCategoriesFromCSV() {
        HashMap<Integer, Category> categories = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CATEGORIES_FILE))) {
            String line;
            
            // Skips CSV header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int categoryId = Integer.parseInt(parts[0]);
                String categoryName = parts[1];
                
                // Add category back into categories hashmap
                categories.put(categoryId, new Category(categoryName));
            }
        } catch (Exception e) {
            // Handle the case where the file doesn't exist or any other error
            System.out.println("Categories file not found.");
            return new HashMap<>();
        } 
        return categories;
    }

    public static HashMap<Integer, Location> loadLocationsFromCSV() {
        HashMap<Integer, Location> locations = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOCATIONS_FILE))) {
            String line;
            
            // Skip CSV header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int locationId = Integer.parseInt(parts[0]);
                String locationName = parts[1];
                
                // Add location back into locations hashmap
                locations.put(locationId, new Location(locationName));
            }
        } catch (Exception e) {
            // Handle the case where the file doesn't exist
            System.out.println("Locations file not found.");
            return new HashMap<>();
        }
        return locations;
    }


    public static void saveData(HashMap<Integer, Category> categories, HashMap<Integer, Location> locations, Integer catId, Integer locId) {
        saveCategoriesToCSV(categories);
        saveLocationsToCSV(locations);
        saveIds(catId, locId);
    }

    private static void saveIds(int catId, int locId) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CAT_ID_FILE))) {
        	// Write catId
            writer.println(catId);
        } catch (Exception e) {
            System.out.print(e);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOC_ID_FILE))) {
        	// Write locId
        	writer.println(locId);
        } catch (Exception e) {
            System.out.print(e);
        }
    } 
    
    private static void saveCategoriesToCSV(HashMap<Integer, Category> categories) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CATEGORIES_FILE))) {
            // Write header
            writer.println("CategoryID,CategoryName");

            // Write category data
            for (HashMap.Entry<Integer, Category> entry : categories.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue().getName());
            }
        } catch (Exception e) {
        }
    }

    private static void saveLocationsToCSV(HashMap<Integer, Location> locations) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOCATIONS_FILE))) {
            // Write header
            writer.println("LocationID,LocationName");

            // Write location data
            for (HashMap.Entry<Integer, Location> entry : locations.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue().getName());
            }
        } catch (Exception e) {
        }
    }

}