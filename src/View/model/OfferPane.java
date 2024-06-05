package View.model;

import Controller.GameController;
import Model.ColoredTrailsPlayer;
import Model.Grid;
import Model.HumanPlayer;
import Model.Token;
import View.controller.ViewController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class OfferPane  extends JPanel implements PropertyChangeListener {
    public static Color defaultButtonColor = new Color(238, 238, 238);
    private Grid grid;
    private GameController controller;
    private JButton yourTokensButton;
    private JButton partnerTokensButton;
    private JButton unassignedTokensButton;
    private JButton sendButton;
    private JButton acceptButton;
    private JButton rejectButton;
    private TokenButton tokenButtonToMove;
    private JLabel offerPanelLabel;
    private JLabel receivedOfferMessageLabel;
    private JPanel centerPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel middlePanel;
    private JPanel receivedOfferPanel;
    private JPanel acceptRejectPanel;
    private JPanel yourTokensPartnerTokens;
    private volatile JPanel yourTokensPanel;
    private volatile JPanel unassignedTokensPanel;
    private volatile JPanel partnerTokensPanel;
    private boolean isSendButtonOnScreen = false;
    private ViewController viewController;



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
        yourTokensButton.setActionCommand("moveToYours");
        yourTokensButton.addActionListener(controller);
        yourTokensButton.addActionListener(viewController);

        // "Partner's tokens" button
        partnerTokensButton = new JButton("Partner's Tokens");
        partnerTokensButton.setHorizontalAlignment(JLabel.CENTER);
        partnerTokensButton.setBackground(Color.LIGHT_GRAY);
        partnerTokensButton.setOpaque(true);
        partnerTokensButton.setPreferredSize(new Dimension(labelWidth, labelHeight));
        partnerTokensButton.setActionCommand("moveToPartner");
        partnerTokensButton.addActionListener(controller);
        partnerTokensButton.addActionListener(viewController);

        // "Offer Panel" label
        offerPanelLabel = new JLabel("Offer Panel");
        offerPanelLabel.setHorizontalAlignment(JLabel.CENTER);
        offerPanelLabel.setPreferredSize(new Dimension(100, 25));
        offerPanelLabel.setOpaque(true);
        offerPanelLabel.setBackground(Color.GRAY);

        // "Unassigned tokens" button
        unassignedTokensButton = new JButton("Unassigned Tokens");
        unassignedTokensButton.setHorizontalAlignment(JLabel.CENTER);
        unassignedTokensButton.setBackground(new Color(200, 200, 200));
        unassignedTokensButton.setOpaque(true);
        unassignedTokensButton.setPreferredSize(new Dimension(labelWidth, labelHeight));
        unassignedTokensButton.setActionCommand("moveTo");
        unassignedTokensButton.addActionListener(controller);
        unassignedTokensButton.addActionListener(viewController);

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

        // Send Button
        sendButton = new JButton("Send Offer");
        sendButton.setActionCommand("send");
        sendButton.addActionListener(controller);
        sendButton.setHorizontalAlignment(JLabel.CENTER);
        sendButton.setBackground(new Color(0, 200, 0));
        sendButton.setOpaque(true);
        sendButton.setPreferredSize(new Dimension(200, 40));

        // Accept Button
        acceptButton = new JButton("Accept Offer");
        acceptButton.setActionCommand("accept");
        acceptButton.setBackground(Color.green);
        acceptButton.addActionListener(viewController);
        acceptButton.addActionListener(controller);

        // Reject Button
        rejectButton = new JButton("Reject Offer");
        rejectButton.setActionCommand("reject");
        rejectButton.setBackground(Color.red);
        rejectButton.addActionListener(viewController);
        rejectButton.addActionListener(controller);

        // Accept Reject Panel
        acceptRejectPanel = new JPanel();
        acceptRejectPanel.setLayout(new GridLayout(1, 2));
        acceptRejectPanel.add(rejectButton);
        acceptRejectPanel.add(acceptButton);

        // Received Offer Message Label
        receivedOfferMessageLabel = new JLabel("You received the following offer from your partner:");
        receivedOfferMessageLabel.setOpaque(true);
        receivedOfferMessageLabel.setBackground(Color.lightGray);
        receivedOfferMessageLabel.setHorizontalAlignment(JLabel.CENTER);


        // Received Offer Panel
        receivedOfferPanel = new JPanel();
        receivedOfferPanel.setLayout(new GridLayout(4, 1));

        // Your tokens partner tokens
        yourTokensPartnerTokens = new JPanel();
        yourTokensPartnerTokens.setLayout(new GridLayout(1, 2));
        JLabel yourTokensLabel = new JLabel("Your Tokens");
        yourTokensLabel.setHorizontalAlignment(JLabel.CENTER);
        yourTokensLabel.setBackground(Color.lightGray);
        JLabel partnerTokensLabel = new JLabel("Partner Tokens");
        partnerTokensLabel.setHorizontalAlignment(JLabel.CENTER);
        partnerTokensLabel.setBackground(Color.lightGray);
        yourTokensPartnerTokens.add(yourTokensLabel);
        yourTokensPartnerTokens.add(partnerTokensLabel);
    }

    /**
     * The method adds the buttons on the unassignedTokensPanel
     * corresponding to all the grid's tokens in play
     */
    private void addButtonsToUnassignedTokensPanel() {
        System.out.println("In OfferPane/addButtonsToUnassignedTokensPanel");
        for(Token token : grid.getAllTokensInPlay()) {
            TokenButton tokenButton = new TokenButton(token);
            tokenButton.setActionCommand("selectToken");
            tokenButton.setPreferredSize(new Dimension(40, 40));
            Image scaledTokenImage =  ImageLoader.tokenImages.get(token.getColor()).getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            tokenButton.setIcon(new ImageIcon(scaledTokenImage));
            tokenButton.addActionListener(controller);
            tokenButton.addActionListener(viewController);
            unassignedTokensPanel.add(tokenButton);
        }
        this.repaint();
        this.revalidate();
    }

    /**
     * Removes all the buttons on the panels
     */
    private void resetOfferPanel() {
        unassignedTokensPanel.removeAll();
        yourTokensPanel.removeAll();
        partnerTokensPanel.removeAll();
        isSendButtonOnScreen = false;
    }

    public OfferPane(Grid grid, GameController controller, ViewController viewController) {
        this.grid = grid;
        this.controller = controller;
        this.viewController = viewController;
        viewController.setOfferPane(this);
        grid.addListener(this);
        ImageLoader.loadImages();
        setUp();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "assignedGoalsIndex" -> {
                if (evt.getSource() instanceof HumanPlayer) {
                    HumanPlayer player = (HumanPlayer) evt.getSource();
                }
            }
            case "initiatingOffer" -> {
                System.out.println("In OfferPane/PropertyChange/initiatingOffer");
                if (grid.getCurrentPlayer() instanceof HumanPlayer) {
                    System.out.println("In OfferPane/PropertyChange/initiatingOffer/first if");
                    if (receivedOfferPanel.getParent() == this) {
                        System.out.println("In OfferPane/PropertyChange/initiatingOffer/first if/second if");
                        this.remove(receivedOfferPanel);
                        revalidate();
                    }
                    this.add(centerPanel, BorderLayout.CENTER);
                    addButtonsToUnassignedTokensPanel();
                    revalidate();
                }
            }
            case "offerFinished" -> {
                this.remove(centerPanel);
                resetOfferPanel();
                revalidate();
            }
            case "receiveOfferFromPartner" -> {
                if (evt.getOldValue() instanceof HumanPlayer) {
//                    HumanPlayer player = (HumanPlayer) evt.getOldValue();
//                    ArrayList<ArrayList<Token>> offer = (ArrayList<ArrayList<Token>>) evt.getNewValue();
//                    offer = (ArrayList<ArrayList<Token>>) offer.clone();
//                    Collections.reverse(offer);
//                    JSplitPane offerPanel = OfferHistoryPane.constructOfferPanel(offer);
//                    receivedOfferPanel.removeAll();
//                    receivedOfferPanel.add(receivedOfferMessageLabel);
//                    receivedOfferPanel.add(yourTokensPartnerTokens);
//                    receivedOfferPanel.add(offerPanel);
//                    receivedOfferPanel.add(acceptRejectPanel);
//                    this.add(receivedOfferPanel, BorderLayout.CENTER);
//                    revalidate();
                }
            }
        }
    }

    /**
     * Getters for the ViewController
     */
    public TokenButton getTokenButtonToMove() {
        return tokenButtonToMove;
    }
    public JButton getUnassignedTokensButton() {
        return unassignedTokensButton;
    }
    public JButton getSendButton() {
        return sendButton;
    }
    public JPanel getUnassignedTokensPanel() {
        return unassignedTokensPanel;
    }
    public boolean getIsSendButtonOnScreen() {
        return isSendButtonOnScreen;
    }
    /**
     * The method is designed to only be called with the arguments yourTokensButton, partnerTokensButton
     * or unassignedTokensButton
     * @param source The pressed button
     * @return The panel on the view corresponding to button source
     */
    public JPanel getDestinationOfTokenButtonPanel(JButton source) {
        if (source == yourTokensButton) {
            return yourTokensPanel;
        }
        if (source == unassignedTokensButton) {
            return unassignedTokensPanel;
        }
        if (source == partnerTokensButton) {
            return partnerTokensPanel;
        }
        return null;
    }
    /**
     * The method checks which of the three panels contains the selected button and returns that panel
     */
    public JPanel getSourceOfTokenButtonPanelAccordingToSelectedButton() {
        for(Component component : yourTokensPanel.getComponents()) {
            if(component == tokenButtonToMove) {
                return yourTokensPanel;
            }
        }
        for(Component component : partnerTokensPanel.getComponents()) {
            if(component == tokenButtonToMove) {
                return partnerTokensPanel;
            }
        }
        for(Component component : unassignedTokensPanel.getComponents()) {
            if(component == tokenButtonToMove) {
                return unassignedTokensPanel;
            }
        }

        return null;
    }

    /**
     * Setters for the ViewController
     */
    public void setIsSendButtonOnScreen(boolean value) {
        isSendButtonOnScreen = value;
    }

    public void setTokenButtonToMove(TokenButton tokenButtonToMove) {
        this.tokenButtonToMove = tokenButtonToMove;
    }
}
