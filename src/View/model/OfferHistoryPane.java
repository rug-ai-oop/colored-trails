package View.model;

import Model.Grid;
import Model.HumanPlayer;
import Model.Token;
import View.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

public class OfferHistoryPane extends JPanel implements PropertyChangeListener {
    private Grid grid;
    private ViewController viewController;
    private HumanPlayer playerToDisplayOnTheLeft;
    private JScrollPane scrollPane;
    private JPanel mainPanel = new JPanel();
    private JPanel labelPanel = new JPanel();
    private JSplitPane lastOfferPanel;
    private JLabel yourTokens = new JLabel("Your tokens");
    private JLabel partnerTokens = new JLabel("Partner's tokens");
    private JPanel middlePanel = new JPanel();
    private Dimension middlePanelDimension = new Dimension(80, 40);
    private Dimension sidePanelDimension = new Dimension(320, 40);

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

    public OfferHistoryPane(ViewController viewController, Grid grid, HumanPlayer playerToDisplayOnTheLeft) {
        this.viewController = viewController;
        this.grid = grid;
        grid.addListener(this);
        this.playerToDisplayOnTheLeft = playerToDisplayOnTheLeft;
        viewController.setOfferHistoryPane(this);
        setUp();
    }

    private void addOffer(ArrayList<ArrayList<Token>> offer) {

        JPanel middlePanel = new JPanel();
        JPanel lastMiddlePanel = new JPanel();  // mimic
        middlePanel.setPreferredSize(middlePanelDimension);
        middlePanel.setBackground(Color.LIGHT_GRAY);
        lastMiddlePanel.setPreferredSize(middlePanelDimension); // mimic
        lastMiddlePanel.setBackground(Color.gray); // set the color of the middle panel to grey

        JPanel leftOfferPanel = new JPanel();
        JPanel lastLeftOfferPanel = new JPanel(); // mimic
        leftOfferPanel.setPreferredSize(sidePanelDimension);
        lastLeftOfferPanel.setPreferredSize(sidePanelDimension); // mimic
        int numberOfLeftTokens = offer.get(0).size();
        leftOfferPanel.setLayout(new GridLayout(1, numberOfLeftTokens));
        lastLeftOfferPanel.setLayout(new GridLayout(1, numberOfLeftTokens)); // mimic

        JPanel rightOfferPanel = new JPanel();
        JPanel lastRightOfferPanel = new JPanel(); // mimic
        rightOfferPanel.setPreferredSize(sidePanelDimension);
        lastRightOfferPanel.setPreferredSize(sidePanelDimension); // mimic
        int numberOfRightTokens = offer.get(1).size();
        rightOfferPanel.setLayout(new GridLayout(1, numberOfRightTokens));
        lastRightOfferPanel.setLayout(new GridLayout(1, numberOfRightTokens)); // mimic

        // Adds the tokens to the left panel of the offer panel
        for(Token token : offer.get(0)) {
            JLabel tokenLabel = new JLabel();
            JLabel lastTokenLabel = new JLabel(); // mimic
            tokenLabel.setOpaque(true);
            lastTokenLabel.setOpaque(true); // mimic
            tokenLabel.setPreferredSize(new Dimension(40, 40));
            lastTokenLabel.setPreferredSize(new Dimension(40, 40)); // mimic
            Image scaledTokenImage =  ImageLoader.tokenImages.get(token.getColor()).
                    getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            tokenLabel.setIcon(new ImageIcon(scaledTokenImage));
            lastTokenLabel.setIcon(new ImageIcon(scaledTokenImage)); // mimic
            tokenLabel.setBackground(OfferPane.defaultButtonColor);
            lastTokenLabel.setBackground(OfferPane.defaultButtonColor); //mimic
            leftOfferPanel.add(tokenLabel);
            lastLeftOfferPanel.add(lastTokenLabel); // mimic
        }

        // Adds the tokens to the right panel of the offer panel
        for(Token token : offer.get(1)) {
            JLabel tokenLabel = new JLabel();
            JLabel lastTokenLabel = new JLabel(); // mimic
            tokenLabel.setOpaque(true);
            lastTokenLabel.setOpaque(true); // mimic
            tokenLabel.setPreferredSize(new Dimension(40, 40));
            lastTokenLabel.setPreferredSize(new Dimension(40, 40)); // mimic
            Image scaledTokenImage =  ImageLoader.tokenImages.get(token.getColor()).
                    getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            tokenLabel.setIcon(new ImageIcon(scaledTokenImage));
            lastTokenLabel.setIcon(new ImageIcon(scaledTokenImage)); // mimic
            tokenLabel.setBackground(OfferPane.defaultButtonColor);
            lastTokenLabel.setBackground(OfferPane.defaultButtonColor); //mimic
            rightOfferPanel.add(tokenLabel);
            lastRightOfferPanel.add(lastTokenLabel); // mimic
        }

        JSplitPane mainOfferPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftOfferPanel, rightOfferPanel);

        mainOfferPanel.setResizeWeight(0.5); // divides the space between the panels equally
        mainOfferPanel.setDividerLocation(0.5); // place the divider in the middle
        mainOfferPanel.setDividerSize(5);;
        mainOfferPanel.setEnabled(false);

        // mimic but reverse the order, because it needs to be displayed for the partner
        lastOfferPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lastRightOfferPanel, lastLeftOfferPanel);

        lastOfferPanel.setResizeWeight(0.5); // divides the space between the panels equally
        lastOfferPanel.setDividerLocation(0.5); // place the divider in the middle
        lastOfferPanel.setDividerSize(5);;
        lastOfferPanel.setEnabled(false);

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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName() == "offer") {
            ArrayList<ArrayList<Token>> offer = (ArrayList<ArrayList<Token>>) evt.getNewValue();
            if(evt.getSource() != playerToDisplayOnTheLeft) {
                Collections.reverse(offer);
            }
            addOffer(offer);
        }
    }

}
