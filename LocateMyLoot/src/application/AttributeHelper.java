package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AttributeHelper {
	
    // Reads categories from CSV file
    public static List<Attribute> readCategoriesFromCSV(String fileName) {
        List<Attribute> attributes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
            	attributes.add(new Category(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attributes;
    }
    
    // Writes an attribute to CSV file
    public static String writeAttributeToCSV(String fileName, Attribute attr) {
    	if(!searchAtt(fileName, attr)) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
	            writer.println(attr.toString());
	            return attr.getName() + " saved successfully";
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "Something went Horribly wrong";
	        }
    	}
    	else {
    		return attr.getName() + "already Exists";
    	}
    }

    // Searches attribute (categories or locations) and return if exists
    public static boolean searchAtt(String csvFile, Attribute attr) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(attr.getName())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }    


}
