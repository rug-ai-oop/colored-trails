package View.model.game;

import Model.ColoredTrailsPlayer;
import Model.Grid;
import Model.Token;
import View.controller.ViewController;
import View.model.visuals.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

public class OfferHistoryPane extends JPanel implements PropertyChangeListener {
    private Grid grid;
    private ViewController viewController;
    private final ColoredTrailsPlayer playerToDisplayOnTheLeft;
    private JScrollPane scrollPane;
    private JPanel mainPanel = new JPanel();
    private final JPanel labelPanel = new JPanel();
    private JSplitPane lastOfferPanel;
    private final JLabel yourTokens = new JLabel("Your tokens");
    private final JLabel partnerTokens = new JLabel("Partner's tokens");
    private final JPanel middlePanel = new JPanel();
    private static final Dimension sidePanelDimension = new Dimension(320, 40);

    /**
     * Constructs the panel to add on one side of the history
     * @param hand the offered tokens of a player
     * @return The panel with the hand
     */
    private static JPanel constructPlayerHandPanel(ArrayList<Token> hand) {
//        int tokensInHand = hand.size();
        JPanel handPanel = new JPanel();
        handPanel.setPreferredSize(sidePanelDimension);
        handPanel.setLayout(new GridLayout(1, 8));

        for(Token token : hand) {
            JLabel tokenLabel = new JLabel();
            tokenLabel.setOpaque(true);
            tokenLabel.setPreferredSize(new Dimension(40, 40));
            Image scaledTokenImage =  ImageLoader.tokenImages.get(token.getColor()).
                    getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            tokenLabel.setIcon(new ImageIcon(scaledTokenImage));
            tokenLabel.setBackground(OfferPane.defaultButtonColor);
            handPanel.add(tokenLabel);
        }
        return handPanel;
    }

    /**
     * @param offer The offer
     * @return A JsplitPanel that represents an offer.
     */
    public static JSplitPane constructOfferPanel(ArrayList<ArrayList<Token>> offer) {
        JPanel leftPanel = constructPlayerHandPanel(offer.get(0));
        JPanel rightPanel = constructPlayerHandPanel(offer.get(1));

        JSplitPane offerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        offerPanel.setResizeWeight(0.5); // divides the space between the panels equally
        offerPanel.setDividerLocation(0.5); // place the divider in the middle
        offerPanel.setDividerSize(5);;
        offerPanel.setEnabled(false);
        return offerPanel;
    }

    private void setUp() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ImageLoader.loadImages();
        mainPanel.setBackground(OfferPane.defaultButtonColor);
        scrollPane.setPreferredSize(new Dimension(740, 160));
        scrollPane.setBackground(OfferPane.defaultButtonColor);

        labelPanel.setLayout(new BorderLayout());
        middlePanel.setBackground(Color.BLACK);
        middlePanel.setPreferredSize(new Dimension(1, 40));

        yourTokens.setPreferredSize(sidePanelDimension);
        yourTokens.setHorizontalAlignment(JLabel.CENTER);

        partnerTokens.setPreferredSize(sidePanelDimension);
        partnerTokens.setHorizontalAlignment(JLabel.CENTER);

        labelPanel.add(yourTokens, BorderLayout.WEST);
        labelPanel.add(partnerTokens, BorderLayout.EAST);
        labelPanel.add(middlePanel, BorderLayout.CENTER);

        this.setPreferredSize(sidePanelDimension);
        this.add(labelPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setBackground(OfferPane.defaultButtonColor);
    }

    public OfferHistoryPane(ViewController viewController, Grid grid, ColoredTrailsPlayer playerToDisplayOnTheLeft) {
        this.viewController = viewController;
        this.grid = grid;
        this.playerToDisplayOnTheLeft = playerToDisplayOnTheLeft;
        grid.addListener(this);
        setUp();
    }


    /**
     * Adds a panel containing the offer to the mainPanel & updates the lastOfferPanel accordingly
     * @param offer
     */
    private void addOffer(ArrayList<ArrayList<Token>> offer) {
        JSplitPane mainOfferPanel = constructOfferPanel(offer);
        lastOfferPanel = constructOfferPanel(offer);

        mainPanel.add(mainOfferPanel);
        revalidate();
    }

    /**
     * Intended for displaying the offer in OfferPane
     * @return last offer panel
     */
    public JComponent getLastOfferPanel() {
        return lastOfferPanel;
    }

    /**
     * Intended for being used in ViewController to know the history of which player to display
     * @return playerToDisplayOnTheLeft
     */
    public ColoredTrailsPlayer getPlayerToDisplayOnTheLeft() {
        return playerToDisplayOnTheLeft;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("offer")) {
            ArrayList<ArrayList<Token>> offer = (ArrayList<ArrayList<Token>>) evt.getNewValue();
            offer = (ArrayList<ArrayList<Token>>) offer.clone();
            ColoredTrailsPlayer playerMakingTheOffer = (ColoredTrailsPlayer) evt.getSource();
            System.out.println("Original offer: ");
            Grid.printOffer(offer);

            // Reverse the offer if the current player is not the playerToDisplayOnTheLeft
            if (playerMakingTheOffer != playerToDisplayOnTheLeft) {
                Collections.reverse(offer);
                System.out.println("Reversed offer: ");
                Grid.printOffer(offer);
            }

            System.out.println("playerMakingTheOffer = " + playerMakingTheOffer.getName());
            System.out.println("playerToDisplayOnTheLeft = " + playerToDisplayOnTheLeft.getName());
            addOffer(offer);
        }
    }

}
