package View.model;

import javax.swing.*;
import java.awt.*;
import View.controller.ViewController;
import Controller.GameController;
import Model.Grid;
import Model.HumanPlayer;

public class OverallView extends AbstractView {
    private GridPane gridPane;
    private PlayerPanel playerPanelLeft;
    private PlayerPanel playerPanelRight;

    public OverallView(Grid grid, GameController gameController, HumanPlayer player1, HumanPlayer player2) {
        setTitle("Overall View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gridPane = new GridPane(grid, gameController, new ViewController());

        playerPanelLeft = new PlayerPanel(grid, gameController, "Player 1", player1);
        playerPanelRight = new PlayerPanel(grid, gameController, "Player 2", player2);
        // buttonPanel = new

        // Create container panel for the west side
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BorderLayout());

        westPanel.add(playerPanelLeft, BorderLayout.CENTER);
        // westPanel.add(buttonPanel, BorderLayout.SOUTH);  for later

        setLayout(new BorderLayout());

        add(gridPane, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
        add(playerPanelRight, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Grid game = new Grid();
        GameController controller = new GameController(game);
        HumanPlayer player1 = new HumanPlayer();
        HumanPlayer player2 = new HumanPlayer();
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.setUp();
        new OverallView(game, controller, player1, player2);
    }
}
