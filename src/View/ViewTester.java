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
        OfferHistoryPane offerHistoryPane = new OfferHistoryPane(viewController, game, firstPlayer);
        viewController.setOfferHistoryPane(offerHistoryPane);
        OfferPane offerPane = new OfferPane(game, controller, viewController, offerHistoryPane);

        //0 - no map loaded, x - patches_map_x will be loaded
        int loadMap = 3;
        game.setUp(loadMap);

        PlayerPanel playerPanel1 = new PlayerPanel(game, controller, "Csenge", firstPlayer, new ViewController());
        PlayerPanel playerPanel2 = new PlayerPanel(game, controller, "Lukasz", secondPlayer, new ViewController());

        frame.setLayout(new BorderLayout());
        frame.add(offerPane, BorderLayout.SOUTH);
        frame.add(gridPane, BorderLayout.CENTER);
        frame.add(playerPanel1, BorderLayout.WEST);
        frame.add(playerPanel2, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        JFrame frameForOffers = new JFrame("Offers");
        frameForOffers.setLayout(new BorderLayout());
        frameForOffers.setSize(1400, 400);
        frameForOffers.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frameForOffers.add(offerHistoryPane);
        frameForOffers.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            boolean saveMap = true;
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
