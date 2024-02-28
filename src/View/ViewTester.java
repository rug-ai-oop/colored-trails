package View;

import Controller.GameController;
import Model.Grid;
import Model.HumanPlayer;

import javax.swing.*;
import java.awt.*;

public class ViewTester {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 400);
        Grid game = new Grid();
        // PropertyChange needs to be implemented in the grid, so that the listeners can be added after
        game.addPlayer(new HumanPlayer());
        game.addPlayer(new HumanPlayer());
        GameController controller = new GameController(game);
        GridPane gridPane = new GridPane(game, controller);
        OfferPane offerPane = new OfferPane(game, controller);
        game.setUp();
        frame.setLayout(new BorderLayout());
        frame.add(offerPane, BorderLayout.SOUTH);
        frame.add(gridPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
