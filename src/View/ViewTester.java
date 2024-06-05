package View;


import View.model.MainPanel;

import javax.swing.*;
import java.awt.*;

public class ViewTester {
    public static void main(String[] args) {

        JFrame optionFrame = new JFrame("Colored Trails");
        optionFrame.setSize(250, 250);
        optionFrame.setLocationRelativeTo(null);

        MainPanel mainPanel = new MainPanel(optionFrame);

        optionFrame.add(mainPanel);
        optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionFrame.setVisible(true);
    }
}
