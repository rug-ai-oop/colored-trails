package View.model;

import Controller.GameController;
import Model.ColoredTrailsPlayer;
import Model.Grid;
import Model.HumanPlayer;
import Model.Token;
import View.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;


public class PlayerPanel extends JPanel implements PropertyChangeListener{
    public static HashMap<PlayerPanel, JFrame> offerHistoryFrames = new HashMap<>(2);
    public static JFrame lastOpenFrame;
    private static Color defaultButtonColor = new Color(26, 194, 26);
    private Grid grid;
    private GameController controller;
    private ViewController viewController;
    private JButton withdrawButton;
    private JPanel yourTokensPanel;
    private JButton offerHistoryButton;
    private OfferHistoryPane offerHistoryPane;
    private HumanPlayer player;
 

    private void setUpFrame() {
        if (offerHistoryFrames.get(this) == null) {
            offerHistoryFrames.put(this, new JFrame("Offer History"));
            offerHistoryFrames.get(this).setLayout(new BorderLayout());
            offerHistoryFrames.get(this).setSize(1000, 500);
            offerHistoryFrames.get(this).setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
        offerHistoryFrames.get(this).add(offerHistoryPane);
    }

    public PlayerPanel(Grid grid, GameController controller, HumanPlayer player, ViewController viewController) {
        this.grid = grid;
        grid.addListener(this);
        this.controller = controller;
        this.viewController = viewController;
        ImageLoader.loadImages();
        this.player = player;
        offerHistoryPane = OfferPane.offerHistoryPanes.get(player);
        setUpFrame();
        setUp();
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
        Image scaledPlayerImage =  ImageLoader.playerImages.get(playerName).getScaledInstance(80
                , 120, Image.SCALE_SMOOTH);
        JLabel playerImage = new JLabel(new ImageIcon(scaledPlayerImage));
        playerImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(playerImage);

        add(Box.createRigidArea(new Dimension(0, 10)));

        // Token title
        JLabel tokenLabel = new JLabel("Your tokens:");
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
        withdrawButton.setBackground(new Color(179, 119, 162));
        withdrawButton.setPreferredSize(new Dimension(130, 50));
        withdrawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(withdrawButton);

        // Displaying offer history
        offerHistoryButton = new JButton("Offer History");
        offerHistoryButton.setFont(new Font("Serif", Font.BOLD, 14));
        offerHistoryButton.setActionCommand("openOfferHistory");
        offerHistoryButton.addActionListener(e -> {
            if (lastOpenFrame != offerHistoryFrames.get(this)) {
                if (lastOpenFrame != null) {
                    lastOpenFrame.setVisible(false);
                }
                lastOpenFrame = offerHistoryFrames.get(this);
            }
            offerHistoryFrames.get(this).setVisible(!offerHistoryFrames.get(this).isVisible());
        });
        offerHistoryButton.setBackground(new Color(179, 119, 162));
        offerHistoryButton.setPreferredSize(new Dimension(130, 50));
        offerHistoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(offerHistoryButton);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(200, 350);
        Grid game = new Grid();
        HumanPlayer firstPlayer = new HumanPlayer();
        HumanPlayer secondPlayer = new HumanPlayer();
        game.addPlayer(firstPlayer);
        game.addPlayer(secondPlayer);
        game.setUp();
        PlayerPanel playerPanel = new PlayerPanel(game, new GameController(game), firstPlayer, new ViewController());
        frame.add(playerPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "newTurn") {
            System.out.println(((ColoredTrailsPlayer) evt.getNewValue()).getName());
            if (evt.getNewValue() == player) {
                withdrawButton.setEnabled(true);
                offerHistoryButton.setEnabled(true);
            } else {
                withdrawButton.setEnabled(false);
                offerHistoryButton.setEnabled(false);
            }
        }
    }
}