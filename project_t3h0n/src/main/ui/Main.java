package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

// Run the app
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                initializeFinancialTrackerGUI();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to run application: file not found");
            }
        });
    }

        //Helper method
        //EFFECTS: create FinancialTrackerGUI()
    private static void initializeFinancialTrackerGUI() throws FileNotFoundException {
        new FinancialTrackerGUI();
    }

}


