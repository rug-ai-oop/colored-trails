package View.model;

import Model.ColoredTrailsPlayer;
import Model.Grid;
import Model.HumanPlayer;
import Controller.GameController;
import View.controller.SetUpGameOptionsController;
import View.controller.ViewController;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    private JFrame frame;
    private SetUpGameOptionsController controller = new SetUpGameOptionsController(this);

    public MainPanel(JFrame frame) {
        this.frame = frame;

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setPreferredSize(new Dimension(250, 250));

        // Create instances of panels (cards)
        GameOptionsPanel gameOptionsPanel = new GameOptionsPanel(controller);
        DummyOptionsPanel dummyOptionsPanel = new DummyOptionsPanel(controller);
        TomSelectionPanel tomSelectionPanel = new TomSelectionPanel(controller);

        // Add panels to this main panel
        add(gameOptionsPanel, "GameOptions");
        add(dummyOptionsPanel, "DummyOptions");
        add(tomSelectionPanel, "TomSelection");

    }

    // Method to switch to a specific card
    public void showCard(String cardName) {
        cardLayout.show(this, cardName);
    }

    public JFrame getFrame() {
        return frame;
    }
}
