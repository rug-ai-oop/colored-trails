package View.model;

import Controller.GameController;
import Model.Grid;
import Model.HumanPlayer;
import Model.Token;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

//this shit ain't doing anything but yeah
public class PlayerPanel extends JPanel implements PropertyChangeListener{
    private static Color defaultButtonColor = new Color(26, 194, 26);
    protected static final Map<Model.Color, BufferedImage> tokenImages = new HashMap<>(5);
    protected static final Map<String, BufferedImage> playerImages = new HashMap<>(2);
    private Grid grid;
    private GameController controller;
    private JButton revealButton;
    private TokenButton tokenButtonToMove;
    private JPanel centerPanel;
    private JPanel yourTokensPanel;
 


    public PlayerPanel(Grid grid, GameController controller, String playerName, HumanPlayer player) {
        this.grid = grid;
        grid.addListener(this);
        this.controller = controller;
        loadImages();
        setUp(playerName, player);
    }
    /**
     * Inner ActionListener, not yet necessary
     */
    private ActionListener viewModifier = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == "selectToken") {
                if (tokenButtonToMove != null) {
                    tokenButtonToMove.setBackground(defaultButtonColor);
                }
                tokenButtonToMove = (TokenButton) e.getSource();
                tokenButtonToMove.setBackground(new Color(30, 38, 200));
            }
        }
    };


    /**
     * Preloads images
     */
    protected static void loadImages() {
        try {
            for (Model.Color color : Model.Color.values()) {
                tokenImages.put(color,
                        ImageIO.read(PlayerPanel.class.getResource("/" + color + ".png")));
            }
            playerImages.put("Lukasz", ImageIO.read(PlayerPanel.class.getResource("/PLAYER_LUKASZ.png")));
            playerImages.put("Csenge", ImageIO.read(PlayerPanel.class.getResource("/PLAYER_CSENGE.png")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * The method sets up the components in the player panel
     */
    private void setUp(String playerName, HumanPlayer player) {

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
        Image scaledPlayerImage =  playerImages.get(playerName).getScaledInstance(80
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
            Image scaledTokenImage =  tokenImages.get(token.getColor()).getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            JLabel tokenImage = new JLabel(new ImageIcon(scaledTokenImage));
            yourTokensPanel.add(tokenImage);
        }
        add(yourTokensPanel);

        // Send Button
        revealButton = new JButton("Reveal Goal");
        revealButton.setFont(new Font("Serif", Font.BOLD, 14));
        revealButton.setActionCommand("reveal goal");
        revealButton.addActionListener(controller);
        revealButton.setBackground(new Color(179, 119, 162));
        revealButton.setPreferredSize(new Dimension(100, 50));
        revealButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(revealButton);
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
        PlayerPanel playerPanel = new PlayerPanel(game, new GameController(game), "Lukasz", firstPlayer);
        frame.add(playerPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void propertyChange(PropertyChangeEvent evt) {
    }
}