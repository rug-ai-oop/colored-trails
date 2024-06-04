package View.model;

import Model.ColoredTrailsPlayer;
import Model.Grid;
import Model.HumanPlayer;
import Controller.GameController;
import View.controller.ViewController;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private String selectedOption; // Store the selected game option
    private int selectedTom; // Store the selected tom level
    private CardLayout cardLayout;
    private JFrame frame; // Reference to the main JFrame

    public MainPanel(JFrame frame) {
        this.frame = frame;
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setPreferredSize(new Dimension(250, 250));

        // Create instances of panels (cards)
        GameOptionsPanel gameOptionsPanel = new GameOptionsPanel(this, new GameController(new Grid(), this));
        DummyOptionsPanel dummyOptionsPanel = new DummyOptionsPanel(this, new GameController(new Grid(), this));
        TomSelectionPanel tomSelectionPanel = new TomSelectionPanel(this, new GameController(new Grid(), this));

        // Add panels to this main panel
        add(gameOptionsPanel, "GameOptions");
        add(dummyOptionsPanel, "DummyOptions");
        add(tomSelectionPanel, "TomSelection");

    }




    public void startGame(String gameOption, int tomLevel, Grid game, GameController controller) {

        JFrame frame = new JFrame("Colored Trails");
        frame.setSize(1200, 800);

        ColoredTrailsPlayer firstPlayer;
        ColoredTrailsPlayer secondPlayer;

        if (gameOption.equals("Human vs. Human")) {
            firstPlayer = new HumanPlayer("Csenge");
            secondPlayer = new HumanPlayer("Lukasz");
        } else if (gameOption.equals("Human vs. Agent") ) {
            firstPlayer = new HumanPlayer("Matei");
            if (tomLevel == 0) {
                secondPlayer = new HumanPlayer("an agent with tom 0");
            } else if (tomLevel == 1){
                secondPlayer = new HumanPlayer("an agent with tom 1");
            } else {
                secondPlayer = new HumanPlayer("an agent with tom 2");
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

        ViewController viewController = new ViewController();

        GridPane gridPane = new GridPane(game, controller, viewController);
        OfferHistoryPane offerHistoryPane = new OfferHistoryPane(viewController, game, firstPlayer);
        viewController.setOfferHistoryPane(offerHistoryPane);
        OfferPane offerPane = new OfferPane(game, controller, viewController, offerHistoryPane);

        //0 - no map loaded, x - patches_map_x will be loaded
        int loadMap = 0;
        game.setUp(loadMap);

        PlayerPanel playerPanel1 = new PlayerPanel(game, controller, "Csenge", firstPlayer, new ViewController());
        PlayerPanel playerPanel2 = new PlayerPanel(game, controller, "Lukasz", secondPlayer, new ViewController());

        frame.setLayout(new BorderLayout());
        frame.add(offerPane, BorderLayout.SOUTH);
        frame.add(gridPane, BorderLayout.CENTER);
        frame.add(playerPanel1, BorderLayout.WEST);
        frame.add(playerPanel2, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

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






    // Method to switch to a specific card
    public void showCard(String cardName) {
        cardLayout.show(this, cardName);
    }

    public void setGameOption(String option) {
        selectedOption = option;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedTom(int tom) {
        selectedTom = tom;
    }

    public int getSelectedTom() {
        return selectedTom;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Colored Trails");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            MainPanel mainPanel = new MainPanel(frame);
            frame.add(mainPanel);

            Grid game = new Grid();
            game.addPlayer(new HumanPlayer());
            game.addPlayer(new HumanPlayer());
            //0 - no map loaded, x - patches_map_x will be loaded
            int loadMap = 0;
            game.setUp(loadMap);

            // Show a specific card initially
            mainPanel.showCard("GameOptions");

            frame.pack();
            frame.setLocationRelativeTo(null); // Center the window
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
