package Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MapSaver {
    private Grid gridToSave;
    public MapSaver(Grid grid) {
        this.gridToSave = grid;
    }
    /**
     * Gets the next file number for the maps
     * @return file number
     */
    private int getNextFileNumber() {
        // Get a list of existing files with the format "patches_map_<number>.ser"
        File directory = new File(".");
        File[] files = directory.listFiles((dir, name) -> name.matches("grid_\\d+\\.ser"));
        // Extract the file numbers
        List<Integer> fileNumbers = new ArrayList<>();
        for (File file : files) {
            String fileName = file.getName();
            int number = Integer.parseInt(fileName.replaceAll("\\D", ""));
            fileNumbers.add(number);
        }
        // Find the next available file number
        int maxNumber = fileNumbers.isEmpty() ? 0 : Collections.max(fileNumbers);
        return maxNumber + 1;
    }

    /**
     * Saves the current map of patches in a file called patches_map_x.ser
     */
    public void saveMap() {
        int nextFileNumber = getNextFileNumber();
        String fileName = "grid_" + nextFileNumber + ".ser";
        // Save the map to the file
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.gridToSave);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + fileName);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

}
