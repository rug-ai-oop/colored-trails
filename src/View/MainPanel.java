package View;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    private JFrame frame; // Reference to the main JFrame

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
