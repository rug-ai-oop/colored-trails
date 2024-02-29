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
    protected static final Map<String, BufferedImage> auxiliaryImages = new HashMap<>(5);
    private Grid grid;
    private GameController controller;
    private JButton makeButton;
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
     * Inner ActionListener which controls the movements of the components in the view that do not involve the model
     */
    private ActionListener viewModifier = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == "selectToken") {
                if (tokenButtonToMove != null) {
                    tokenButtonToMove.setBackground(defaultButtonColor);
                }
                tokenButtonToMove = (TokenButton) e.getSource();
                tokenButtonToMove.setBackground(new Color(60, 200, 30));
            } else if (e.getActionCommand() == "moveTo") {
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
                        ImageIO.read(OfferPane.class.getResource("/" + color + ".png")));
            }
            auxiliaryImages.put("redFlag", ImageIO.read(OfferPane.class.getResource("/RED_FLAG.png")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * The method sets up the components in the offer panel
     */
    private void setUp() {
        
        this.setLayout(new BorderLayout(0, 0));



        //Token Panel
        yourTokensPanel = new JPanel();
        yourTokensPanel.setBackground(Color.red);
        yourTokensPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //yourTokensPanel.addActionListener(controller);
        //yourTokensPanel.addActionListener(viewModifier);

        // Center
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));
        centerPanel.add(yourTokensPanel, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // Send Button
        makeButton = new JButton("Make Offer");
        makeButton.setActionCommand("making offer");
        makeButton.addActionListener(controller);
        makeButton.setHorizontalAlignment(JLabel.CENTER);
        makeButton.setBackground(new Color(0, 200, 0));
        makeButton.setOpaque(true);
        makeButton.setPreferredSize(new Dimension(200, 40));
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
        OfferPane offerPane = new OfferPane(game, new GameController(game));
        frame.add(offerPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void propertyChange(PropertyChangeEvent evt) {
    }
}