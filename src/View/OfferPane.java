package View;

import Controller.GameController;
import Model.Grid;
import Model.HumanPlayer;
import Model.Token;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class OfferPane  extends JPanel {
    private Grid grid;
    private GameController controller;
    private JButton yourTokensButton;
    private JButton partnerTokensButton;
    private JButton unassignedTokensButton;
    private JLabel offerPanelLabel;
    private JPanel centerPanel;
    private JPanel leftPanel;
    private JPanel yourTokensPanel;
    private JPanel middlePanel;
    private JPanel unassignedTokensPanel;
    private JPanel rightPanel;
    private JPanel partnerTokensPanel;

    public static final Map<Model.Color, BufferedImage> tokenImages = new HashMap<>(5);

    /**
     * Preloads images
     */
    protected static void loadImages() {
        try {
            for (Model.Color color : Model.Color.values()) {
                tokenImages.put(color,
                        ImageIO.read(OfferPane.class.getResource("/" + color + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * The method sets up the components in the offer panel
     */
    private void setUp() {
        int labelHeight = 50;
        int labelWidth = 100;
        this.setLayout(new BorderLayout(0, 0));

        // "Your tokens" button
        yourTokensButton = new JButton("Your Tokens");
        yourTokensButton.setHorizontalAlignment(JLabel.CENTER);
        yourTokensButton.setBackground(Color.LIGHT_GRAY);
        yourTokensButton.setOpaque(true);
        yourTokensButton.setPreferredSize(new Dimension(labelWidth, labelHeight));

        // "Partner's tokens" label
        partnerTokensButton = new JButton("Partner's Tokens");
        partnerTokensButton.setHorizontalAlignment(JLabel.CENTER);
        partnerTokensButton.setBackground(Color.LIGHT_GRAY);
        partnerTokensButton.setOpaque(true);
        partnerTokensButton.setPreferredSize(new Dimension(labelWidth, labelHeight));

        // "Offer Panel" label
        offerPanelLabel = new JLabel("Offer Panel");
        offerPanelLabel.setHorizontalAlignment(JLabel.CENTER);
        offerPanelLabel.setPreferredSize(new Dimension(100, 25));
        offerPanelLabel.setOpaque(true);
        offerPanelLabel.setBackground(Color.GRAY);

        // "Unassigned tokens" label
        unassignedTokensButton = new JButton("Unassigned Tokens");
        unassignedTokensButton.setHorizontalAlignment(JLabel.CENTER);
        unassignedTokensButton.setBackground(new Color(200, 200, 200));
        unassignedTokensButton.setOpaque(true);
        unassignedTokensButton.setPreferredSize(new Dimension(labelWidth, labelHeight));

        // North panel
        this.add(offerPanelLabel, BorderLayout.NORTH);

        // Center
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));

        // Center-Left
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        yourTokensPanel = new JPanel();
        yourTokensPanel.setBackground(Color.darkGray);
        leftPanel.add(yourTokensButton, BorderLayout.NORTH);
        leftPanel.add(yourTokensPanel, BorderLayout.CENTER);

        // Center-Middle
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        unassignedTokensPanel = new JPanel();
        unassignedTokensPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        unassignedTokensPanel.setBackground(new Color(110, 110, 110));
        middlePanel.add(unassignedTokensButton, BorderLayout.NORTH);
        middlePanel.add(unassignedTokensPanel, BorderLayout.CENTER);

        // Center-Right
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        partnerTokensPanel = new JPanel();
        partnerTokensPanel.setBackground(Color.darkGray);
        rightPanel.add(partnerTokensButton, BorderLayout.NORTH);
        rightPanel.add(partnerTokensPanel, BorderLayout.CENTER);


        centerPanel.add(leftPanel);
        centerPanel.add(middlePanel);
        centerPanel.add(rightPanel);
        this.add(centerPanel, BorderLayout.CENTER);

    }

    /**
     * The method adds the buttons on the unassignedTokensPanel
     * corresponding to all the grid's tokens in play
     */
    private void addInitialButtonsToUnassignedTokensPanel() {
        for(Token token : grid.getAllTokensInPlay()) {
            JButton tokenButton = new JButton();
            tokenButton.setPreferredSize(new Dimension(40, 40));
            Image scaledTokenImage =  tokenImages.get(token.getColor()).getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            tokenButton.setIcon(new ImageIcon(scaledTokenImage));
            unassignedTokensPanel.add(tokenButton);
        }
        this.repaint();
    }

    public OfferPane(Grid grid, GameController controller) {
        this.grid = grid;
        this.controller = controller;
        loadImages();
        setUp();
        addInitialButtonsToUnassignedTokensPanel();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(700, 300);
        Grid game = new Grid();
        game.addPlayer(new HumanPlayer());
        game.addPlayer(new HumanPlayer());
        game.setUp();
        OfferPane offerPane = new OfferPane(game, new GameController(new HumanPlayer()));
        frame.add(offerPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
