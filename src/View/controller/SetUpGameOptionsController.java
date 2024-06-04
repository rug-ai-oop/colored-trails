package View.controller;

import Controller.GameController;
import Model.ColoredTrailsPlayer;
import Model.Grid;
import Model.HumanPlayer;
import View.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetUpGameOptionsController implements ActionListener {
    private MainPanel mainPanel;
    private String selectedOption; // Store the selected game option
    private int selectedTom; // Store the selected tom level

    public SetUpGameOptionsController(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private void startGame(String gameOption, int tomLevel) {

        JFrame frame = new JFrame("Colored Trails");
        frame.setSize(1200, 800);

        Grid game = new Grid();
        ColoredTrailsPlayer firstPlayer;
        ColoredTrailsPlayer secondPlayer;

        if (gameOption.equals("Human vs. Human")) {
            firstPlayer = new HumanPlayer("Csenge");
            secondPlayer = new HumanPlayer("Lukasz");
        } else if (gameOption.equals("Human vs. Agent") ) {
            firstPlayer = new HumanPlayer("Matei");
            switch (tomLevel) {
                default -> secondPlayer = new HumanPlayer("an agent with tom " + tomLevel);
            }
        } else {
            firstPlayer = new HumanPlayer("agent1 with always tom 2");
            if (tomLevel == 0) {
                secondPlayer = new HumanPlayer("an agent with tom 0");
            } else if (tomLevel == 1){
                secondPlayer = new HumanPlayer("an agent with tom 1");
            } else {
                secondPlayer = new HumanPlayer("an agent with tom 2");
            }
        }

        game.addPlayer(firstPlayer);
        game.addPlayer(secondPlayer);

        GameController gameController = new GameController(game);
        ViewController viewController = new ViewController();

        GridPane gridPane = new GridPane(game, gameController, viewController);
//        OfferHistoryPane offerHistoryPane = new OfferHistoryPane(viewController, game, firstPlayer);
//        viewController.setOfferHistoryPane(offerHistoryPane);
        OfferPane offerPane = new OfferPane(game, gameController, viewController);

        //0 - no map loaded, x - patches_map_x will be loaded
        int loadMap = 0;
        game.setUp(loadMap);

        PlayerPanel playerPanel1 = new PlayerPanel(game, gameController, "Csenge", firstPlayer, viewController);
        PlayerPanel playerPanel2 = new PlayerPanel(game, gameController, "Lukasz", secondPlayer, viewController);

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
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Human vs. Human":
                selectedOption = e.getActionCommand();
                mainPanel.showCard("DummyOptions");
                break;
            case "Human vs. Agent", "Agent vs. Agent", "Human vs. Human like agent":
                selectedOption = e.getActionCommand();
                mainPanel.showCard("TomSelection");
                break;
            case "dummy1", "dummy2", "dummy3":
                //actually start the game based on game option and the tom if there is an agent
                startGame(selectedOption, selectedTom);
                break;
            case "BackFromDummy":
                System.out.println(selectedOption);
                String previousPanel = selectedOption.equals("Human vs. Human") ? "GameOptions" : "TomSelection";
                mainPanel.showCard(previousPanel);
                break;
            case "BackFromTom":
                mainPanel.showCard("GameOptions");
                break;
            case "0":
                selectedTom = 0;
                mainPanel.showCard("DummyOptions");
                break;
            case "1":
                selectedTom = 1;
                mainPanel.showCard("DummyOptions");
                break;
            case "2":
                selectedTom = 2;
                mainPanel.showCard("DummyOptions");
                break;

        }
    }
}
