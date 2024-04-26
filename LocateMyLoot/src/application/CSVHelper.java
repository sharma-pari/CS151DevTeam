package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static List<String> CSVtoString(String fileName) {
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Location temp = new Location(line);
                names.add(temp.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }
}
