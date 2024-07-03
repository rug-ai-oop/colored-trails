package View.model.game;

import Controller.GameController;
import Model.ColoredTrailsPlayer;
import Model.Grid;
import Model.HumanPlayer;
import Model.Token;
import View.controller.ViewController;
import View.model.visuals.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class PlayerPanel extends JPanel implements PropertyChangeListener {
    private static Color defaultButtonColor = new Color(26, 194, 26);
    private Grid grid;
    private GameController controller;
    private ViewController viewController;
    private JButton withdrawButton;
    private JPanel yourTokensPanel;
    private JPanel panelWithButtons;
    private JButton offerHistoryButton;
    private ColoredTrailsPlayer player;
    private JFrame historyFrame;
    private OfferHistoryPane offerHistoryPane;
    private Image playerImage;


    public PlayerPanel(Grid grid, GameController controller, ColoredTrailsPlayer player, ViewController viewController, Image playerImage) {
        this.grid = grid;
        grid.addListener(this);
        this.controller = controller;
        this.viewController = viewController;
        this.playerImage = playerImage;
        ImageLoader.loadImages();
        this.player = player;
        offerHistoryPane = new OfferHistoryPane(viewController, grid, player);
        setUpHistoryFrame();
        setUp();
    }

    /**
     * Creates the frame and adds the offerHistoryPane
     */
    private void setUpHistoryFrame() {
        historyFrame = new JFrame("Offers History");
        historyFrame.setSize(1000, 300);
        historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        historyFrame.add(offerHistoryPane);
        historyFrame.setLocationRelativeTo(null);
    }


    /**
     * The method sets up the components in the player panel
     */
    private void setUp() {
        String playerName = player.getName();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(51, 51, 52));
        setPreferredSize(new Dimension(200, 400));

        // Player name
        JLabel playerLabel = new JLabel("Player name: " + playerName);
        playerLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        playerLabel.setForeground(Color.GREEN);
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(playerLabel);

        // Player Image
        Image scaledPlayerImage =  playerImage.getScaledInstance(80
                , 120, Image.SCALE_SMOOTH);
        JLabel playerImage = new JLabel(new ImageIcon(scaledPlayerImage));
        playerImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(playerImage);


        add(Box.createRigidArea(new Dimension(0, 10)));

        // Token title
        JLabel tokenLabel = new JLabel(playerName + "'s tokens:");
        tokenLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        tokenLabel.setForeground(Color.GREEN);
        tokenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(tokenLabel);

        //Token Panel
        yourTokensPanel = new JPanel();
        yourTokensPanel.setBackground(new Color(51, 51, 52));
        yourTokensPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        yourTokensPanel.setPreferredSize(new Dimension(100, 100));
        yourTokensPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        for(Token token : grid.getTokens(player)) {
            Image scaledTokenImage =  ImageLoader.tokenImages.get(token.getColor()).getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            JLabel tokenImage = new JLabel(new ImageIcon(scaledTokenImage));
            yourTokensPanel.add(tokenImage);
        }
        add(yourTokensPanel);

        // Withdraw button
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Serif", Font.BOLD, 14));
        withdrawButton.setActionCommand("withdrawGame");
        withdrawButton.addActionListener(controller);
        withdrawButton.setBackground(Color.lightGray);
        withdrawButton.setPreferredSize(new Dimension(130, 80));
        withdrawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(withdrawButton);

        // Displaying offer history
        offerHistoryButton = new JButton("Offer History");
        offerHistoryButton.setFont(new Font("Serif", Font.BOLD, 14));
        offerHistoryButton.setActionCommand("openOfferHistory");
        offerHistoryButton.setBackground(Color.lightGray);
        offerHistoryButton.setPreferredSize(new Dimension(130, 80));
        offerHistoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        offerHistoryButton.addActionListener(e -> {
            historyFrame.setVisible(!historyFrame.isVisible());

        });

        panelWithButtons = new JPanel();
        panelWithButtons.setLayout(new GridLayout(2,1));
        panelWithButtons.add(withdrawButton);
        panelWithButtons.add(offerHistoryButton);
        panelWithButtons.setMaximumSize(new Dimension(1000, 200));
    }



    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "newTurn") {
            if (evt.getNewValue() == player) {
                if (player instanceof HumanPlayer) {
                    add(panelWithButtons);
                }
                if (historyFrame.isVisible()) {
                    historyFrame.setVisible(false);
                }
                revalidate();
            } else {
                remove(panelWithButtons);
                revalidate();
            }
        }
    }
}