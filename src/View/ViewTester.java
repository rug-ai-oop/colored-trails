package View;

import Controller.GameController;
import Model.Grid;
import Model.HumanPlayer;

import javax.swing.*;
import java.awt.*;

public class ViewTester {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1200, 1000);
        Grid game = new Grid();
        // PropertyChange needs to be implemented in the grid, so that the listeners can be added after
        game.addPlayer(new HumanPlayer());
        game.addPlayer(new HumanPlayer());
        game.setUp();
        OfferPane offerPane = new OfferPane(game, new GameController(game));
        GridPane gridPane = new GridPane(game, new GameController(game));
        frame.setLayout(new BorderLayout());
        frame.add(offerPane, BorderLayout.SOUTH);
        frame.add(gridPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
