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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewTester {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Colored Trails");
        frame.setSize(1200, 800);
        Grid game = new Grid();

        HumanPlayer firstPlayer = new HumanPlayer("Csenge");
        HumanPlayer secondPlayer = new HumanPlayer("Lukasz");
        game.addPlayer(firstPlayer);
        game.addPlayer(secondPlayer);

        GameController controller = new GameController(game);
        ViewController viewController = new ViewController();

        GridPane gridPane = new GridPane(game, controller, viewController);
        OfferPane offerPane = new OfferPane(game, controller, viewController);

        //0 - new map generated, x - grid_x will be loaded from savefile
        // ------------------------------------------------------------------------------
        int loadMap = 0;
        // ------------------------------------------------------------------------------
        // IMPORTANT !!!!!!!! ALWAYS SET AT 0 WHEN SAVING NEW MAPS
        game.setUp(loadMap);

        PlayerPanel playerPanel1 = new PlayerPanel(game, controller, firstPlayer, viewController);
        PlayerPanel playerPanel2 = new PlayerPanel(game, controller, secondPlayer, viewController);

        frame.setLayout(new BorderLayout());
        frame.add(offerPane, BorderLayout.SOUTH);
        frame.add(gridPane, BorderLayout.CENTER);
        frame.add(playerPanel1, BorderLayout.WEST);
        frame.add(playerPanel2, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            //true - save map, false - don't save map
            // -----------------------------------------------------------------------------
            boolean saveMap = false;
            // -----------------------------------------------------------------------------
            //IMPORTANT: SET TO FALSE WHENEVER READING AN OLD MAP

            int [] finishArray = game.start(saveMap);
            //get player names to print out score
            ArrayList <String> playerNames = game.getPlayerNames();
            String player1name = playerNames.get(0);
            String player2name = playerNames.get(1);
            //print out score
            if (finishArray[0] == 1) {
                JOptionPane.showMessageDialog(null, "Agreement reached! :)\n" + player1name + " score: " + finishArray[1] + "\n" + player2name + " score: " + finishArray[2]);
            } else {
                JOptionPane.showMessageDialog(null, "Agreement not reached! :(\n" + player1name + " score: " + finishArray[1] + "\n" + player2name + " score: " + finishArray[2]);
            }
            System.exit(0);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
