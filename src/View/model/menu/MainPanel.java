package View.model.menu;

import View.controller.SetUpGameOptionsController;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    private JFrame frame;
    private SetUpGameOptionsController controller = new SetUpGameOptionsController(this);
    private ChoosePictureMenu choosePictureMenu;

    public MainPanel(JFrame frame) {
        this.frame = frame;
        frame.setBackground(Color.darkGray);

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setPreferredSize(new Dimension(250, 250));

        // Create instances of panels (cards)
        GameOptionsPanel gameOptionsPanel = new GameOptionsPanel( controller);
        DummyOptionsPanel dummyOptionsPanel = new DummyOptionsPanel(controller);
        TomSelectionPanel tomSelectionPanel = new TomSelectionPanel(controller);
        this.choosePictureMenu = new ChoosePictureMenu(controller);

        // Add panels to this main panel
        add(gameOptionsPanel, "GameOptions");
        add(dummyOptionsPanel, "DummyOptions");
        add(tomSelectionPanel, "TomSelection");
        add(choosePictureMenu, "ChoosePicture");

    }

    // Method to switch to a specific card
    public void showCard(String cardName) {
        cardLayout.show(this, cardName);
    }

    public JFrame getFrame() {
        return frame;
    }

    public ChoosePictureMenu getChoosePictureMenu() {
        return choosePictureMenu;
    }
}
