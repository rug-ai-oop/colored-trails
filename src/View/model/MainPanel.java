package View.model;

import Model.Grid;
import Model.HumanPlayer;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private String selectedOption; // Store the selected game option
    private CardLayout cardLayout;
    private JFrame frame; // Reference to the main JFrame

    public MainPanel(JFrame frame) {
        this.frame = frame;
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setPreferredSize(new Dimension(250, 250));

        // Create instances of panels (cards)
        GameOptionsPanel gameOptionsPanel = new GameOptionsPanel(this);
        DummyOptionsPanel dummyOptionsPanel = new DummyOptionsPanel(this);
        TomSelectionPanel tomSelectionPanel = new TomSelectionPanel();

        // Add panels to this main panel
        add(gameOptionsPanel, "GameOptions");
        add(dummyOptionsPanel, "DummyOptions");
        add(tomSelectionPanel, "TomSelection");

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

    public void initializeGame() {
        // Initialize the game based on the selected option
        System.out.println(selectedOption);
        if (selectedOption != null) {
            switch (selectedOption) {
                case "Human vs. Human":
                    // Initialize game with 2 human players
                    break;
                case "Human vs. Agent":
                    // Initialize game with 1 human player and 1 agent
                    break;
                case "Agent vs. Agent":
                    // Initialize game with 2 agents
                    break;
                default:
                    break;
            }
        }
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
