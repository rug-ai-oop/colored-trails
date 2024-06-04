package View;

import Controller.GameController;
import Model.Grid;
import Model.HumanPlayer;
import View.controller.ViewController;
import View.model.GridPane;
import View.model.OfferHistoryPane;
import View.model.OfferPane;
import View.model.PlayerPanel;
import javax.swing.JOptionPane;
import View.model.MainPanel;

import javax.swing.*;
import java.awt.*;

public class ViewTester {
    public static void main(String[] args) {

        JFrame optionFrame = new JFrame("Colored Trails");
        optionFrame.setSize(250, 250);
        optionFrame.setLocationRelativeTo(null);

        Grid game = new Grid();

        GameController controller = new GameController(game, new MainPanel(new JFrame()));

        MainPanel mainPanel = new MainPanel(optionFrame);

        optionFrame.add(mainPanel);
        optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionFrame.setVisible(true);
    }
}
