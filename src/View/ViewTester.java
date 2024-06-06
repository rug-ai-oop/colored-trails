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

        //0 - no map loaded, x - patches_map_x will be loaded
        int loadMap = 0;
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
            boolean saveMap = false;
            int [] finishArray = game.start(saveMap);
            if (finishArray[0] == 1) {
                JOptionPane.showMessageDialog(null, "Agreement reached!\nPlayer 1 score: " + finishArray[1] + "\nPlayer 2 score: " + finishArray[2]);
            } else {
                JOptionPane.showMessageDialog(null, "Agreement not reached!\nPlayer 1 score: " + finishArray[1] + "\nPlayer 2 score: " + finishArray[2]);
            }
            System.exit(0);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
