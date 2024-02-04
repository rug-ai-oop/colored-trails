package View;

import Controller.GameController;
import Model.Grid;
import Model.HumanPlayer;

import javax.swing.*;
import java.awt.*;

public class OfferPane  extends JPanel {
    private Grid grid;
    private GameController controller;

    /**
     * The method sets up the components in the offer panel
     */
    private void setUp() {
        int labelHeight = 50;
        int labelWidth = 100;
        this.setLayout(new BorderLayout(0, 0));

        // "Your tokens" label
        JLabel yourTokensLabel = new JLabel("Your Tokens");
        yourTokensLabel.setHorizontalAlignment(JLabel.CENTER);
        yourTokensLabel.setBackground(Color.LIGHT_GRAY);
        yourTokensLabel.setOpaque(true);
        yourTokensLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        // "Partner's tokens" label
        JLabel partnerTokensLabel = new JLabel("Partner's Tokens");
        partnerTokensLabel.setHorizontalAlignment(JLabel.CENTER);
        partnerTokensLabel.setBackground(Color.LIGHT_GRAY);
        partnerTokensLabel.setOpaque(true);
        partnerTokensLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        // "Offer Panel" label
        JLabel offerPanelLabel = new JLabel("Offer Panel");
        offerPanelLabel.setHorizontalAlignment(JLabel.CENTER);
        offerPanelLabel.setPreferredSize(new Dimension(100, 40));
        offerPanelLabel.setOpaque(true);
        offerPanelLabel.setBackground(Color.GRAY);

        // "Unassigned tokens" label
        JLabel unassignedTokensLabel = new JLabel("Unassigned Tokens");
        unassignedTokensLabel.setHorizontalAlignment(JLabel.CENTER);
        unassignedTokensLabel.setBackground(new Color(200, 200, 200));
        unassignedTokensLabel.setOpaque(true);
        unassignedTokensLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        // North panel
        this.add(offerPanelLabel, BorderLayout.NORTH);

        // Center
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));

        // Center-Left
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        JPanel yourTokensPanel = new JPanel();
        yourTokensPanel.setBackground(Color.darkGray);
        leftPanel.add(yourTokensLabel, BorderLayout.NORTH);
        leftPanel.add(yourTokensPanel, BorderLayout.CENTER);

        // Center-Middle
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        JPanel unassignedTokensPanel = new JPanel();
        unassignedTokensPanel.setBackground(new Color(110, 110, 110));
        middlePanel.add(unassignedTokensLabel, BorderLayout.NORTH);
        middlePanel.add(unassignedTokensPanel, BorderLayout.CENTER);

        // Center-Right
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        JPanel partnerTokensPanel = new JPanel();
        partnerTokensPanel.setBackground(Color.darkGray);
        rightPanel.add(partnerTokensLabel, BorderLayout.NORTH);
        rightPanel.add(partnerTokensPanel, BorderLayout.CENTER);


        centerPanel.add(leftPanel);
        centerPanel.add(middlePanel);
        centerPanel.add(rightPanel);
        this.add(centerPanel, BorderLayout.CENTER);

    }

    public OfferPane(Grid grid, GameController controller) {
        this.grid = grid;
        this.controller = controller;
        setUp();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(700, 300);
        Grid game = new Grid();
        OfferPane offerPane = new OfferPane(game, new GameController(new HumanPlayer()));
        frame.add(offerPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
