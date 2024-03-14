package View;

import Controller.GameController;
import Model.Grid;
import Model.HumanPlayer;
import Model.Token;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

//this shit ain't doing anything but yeah
public class PlayerPanel extends JPanel implements PropertyChangeListener{
    private static Color defaultButtonColor = new Color(238, 238, 238);
    protected static final Map<Model.Color, BufferedImage> tokenImages = new HashMap<>(5);
    protected static final Map<String, BufferedImage> playerImages = new HashMap<>(5);
    private Grid grid;
    private GameController controller;
    private JButton revealButton;
    private TokenButton tokenButtonToMove;
    private JPanel centerPanel;
    private JPanel yourTokensPanel;
 


    public PlayerPanel(Grid grid, GameController controller) {
        this.grid = grid;
        grid.addListener(this);
        this.controller = controller;
        loadImages();
        setUp();
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
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * The method sets up the components in the player panel
     */
    private void setUp() {
        
        this.setLayout(new BorderLayout(0, 0));


        // Center
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));
        this.add(centerPanel, BorderLayout.CENTER);


        //Token Panel
        yourTokensPanel = new JPanel();
        yourTokensPanel.setBackground(Color.green);
        yourTokensPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //yourTokensPanel.addActionListener(controller);
        //yourTokensPanel.addActionListener(viewModifier);
        centerPanel.add(yourTokensPanel, BorderLayout.CENTER);

        // Send Button
        revealButton = new JButton("Reveal Goal");
        revealButton.setActionCommand("reveal goal");
        revealButton.addActionListener(controller);
        //revealButton.setHorizontalAlignment(JLabel.CENTER);
        revealButton.setBackground(new Color(179, 119, 162));
        revealButton.setPreferredSize(new Dimension(10, 10));
        Image scaledPlayerImage =  playerImages.get("Lukasz").getScaledInstance(50
                , 80, Image.SCALE_SMOOTH);
        revealButton.setIcon(new ImageIcon(scaledPlayerImage));
        centerPanel.add(revealButton);
    }

    /**
     * The method adds the buttons on the unassignedTokensPanel
     * corresponding to all the grid's tokens in play
     */
    private void addInitialButtonsToUnassignedTokensPanel() {
        for (Token token : grid.getAllTokensInPlay()) {
            TokenButton tokenButton = new TokenButton(token);
            tokenButton.setActionCommand("selectToken");
            tokenButton.setPreferredSize(new Dimension(40, 40));
            Image scaledTokenImage = tokenImages.get(token.getColor()).getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            tokenButton.setIcon(new ImageIcon(scaledTokenImage));
            tokenButton.addActionListener(controller);
            tokenButton.addActionListener(viewModifier);
            yourTokensPanel.add(tokenButton);
        }
        this.repaint();
        this.revalidate();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1200, 200);
        Grid game = new Grid();
        game.addPlayer(new HumanPlayer());
        game.addPlayer(new HumanPlayer());
        game.setUp();
        PlayerPanel playerPanel = new PlayerPanel(game, new GameController(game));
        frame.add(playerPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void propertyChange(PropertyChangeEvent evt) {
    }
}