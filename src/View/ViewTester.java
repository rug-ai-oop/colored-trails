package View;

import Controller.GameController;
import Model.Grid;
import Model.HumanPlayer;
import View.controller.ViewController;
import View.model.GridPane;
import View.model.OfferHistoryPane;
import View.model.OfferPane;

import javax.swing.*;
import java.awt.*;

public class ViewTester {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 400);
        Grid game = new Grid();
        HumanPlayer firstPlayer = new HumanPlayer();
        HumanPlayer secondPlayer = new HumanPlayer();
        game.addPlayer(firstPlayer);
        game.addPlayer(secondPlayer);
        GameController controller = new GameController(game);
        ViewController viewController = new ViewController();
        GridPane gridPane = new GridPane(game, controller, viewController);
        OfferPane offerPane = new OfferPane(game, controller, viewController);
        game.setUp();
        frame.setLayout(new BorderLayout());
        frame.add(offerPane, BorderLayout.SOUTH);
        frame.add(gridPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        JFrame frameForOffers = new JFrame("Offers");
        frameForOffers.setLayout(new BorderLayout());
        frameForOffers.setSize(800, 400);
        frameForOffers.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        OfferHistoryPane offerHistoryPane = new OfferHistoryPane(game, firstPlayer);
        frameForOffers.add(offerHistoryPane);
        frameForOffers.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            game.start();
            //print score
            System.out.println("player1:");
            System.out.println(game.calculateFinalScore(firstPlayer));
            System.out.println("player2:");
            System.out.println(game.calculateFinalScore(secondPlayer));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
