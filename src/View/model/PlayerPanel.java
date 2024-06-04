package View.model;

import Controller.GameController;
import Model.ColoredTrailsPlayer;
import Model.Grid;
import Model.HumanPlayer;
import Model.Token;
import View.controller.ViewController;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



public class PlayerPanel extends JPanel implements PropertyChangeListener{
    private static Color defaultButtonColor = new Color(26, 194, 26);
//    protected static final Map<Model.Color, BufferedImage> tokenImages = new HashMap<>(5);
//    protected static final Map<String, BufferedImage> playerImages = new HashMap<>(2);
    private Grid grid;
    private GameController controller;
    private ViewController viewController;
    private JButton revealButton;
    private JButton withdrawButton;
    private TokenButton tokenButtonToMove;
    private JPanel centerPanel;
    private JPanel yourTokensPanel;
    private JButton offerHistoryButton;
 


    public PlayerPanel(Grid grid, GameController controller, String playerName, ColoredTrailsPlayer player, ViewController viewController) {
        this.grid = grid;
        grid.addListener(this);
        this.controller = controller;
        this.viewController = viewController;
        ImageLoader.loadImages();
        setUp(playerName, player);
    }

    /**
     * The method sets up the components in the player panel
     */
    private void setUp(String playerName, ColoredTrailsPlayer player) {

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

        //Withdraw Button
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
        offerHistoryButton.addActionListener(viewController);
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
        //0 - no map loaded, x - patches_map_x will be loaded
        int loadMap = 0;
        game.setUp(loadMap);
        PlayerPanel playerPanel = new PlayerPanel(game, new GameController(game, new MainPanel(new JFrame())), "Lukasz", firstPlayer, new ViewController());
        frame.add(playerPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void propertyChange(PropertyChangeEvent evt) {
    }
}