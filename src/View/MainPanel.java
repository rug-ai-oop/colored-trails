package View;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    private JFrame frame; // Reference to the main JFrame

    // Define titles for each card
    private static final String[] CARD_TITLES = {"Game Options", "Dummy Options", "Tom Selection"};

    public MainPanel(JFrame frame) {
        this.frame = frame;
        cardLayout = new CardLayout();
        setLayout(cardLayout);

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
        updateFrameTitle(cardName);
    }

    // Method to update the frame title based on the card being shown
    private void updateFrameTitle(String cardName) {
        int index = -1;
        for (int i = 0; i < CARD_TITLES.length; i++) {
            if (CARD_TITLES[i].equals(cardName)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            frame.setTitle(CARD_TITLES[index]);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Colored Trails");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            MainPanel mainPanel = new MainPanel(frame);
            frame.add(mainPanel);

            // Show a specific card initially
            mainPanel.showCard("GameOptions");

            frame.pack();
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });
    }
}
