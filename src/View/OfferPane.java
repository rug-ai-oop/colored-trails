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

public class OfferPane  extends JPanel implements PropertyChangeListener, AllowedToListen {
    public static final Map<Model.Color, BufferedImage> tokenImages = new HashMap<>(5);
    public static Color defaultButtonColor = new Color(238, 238, 238);
    protected static final Map<String, BufferedImage> auxiliaryImages = new HashMap<>(5);
    private Grid grid;
    private GameController controller;
    private JButton yourTokensButton;
    private JButton partnerTokensButton;
    private JButton unassignedTokensButton;
    private JButton sendButton;
    private TokenButton tokenButtonToMove;
    private JLabel offerPanelLabel;
    private JPanel centerPanel;
    private JPanel leftPanel;
    private JPanel yourTokensPanel;
    private JPanel middlePanel;
    private JPanel unassignedTokensPanel;
    private JPanel rightPanel;
    private JPanel partnerTokensPanel;
    private boolean isSendButtonOnScreen = false;

    /**
     * Inner ActionListener which controls the movements of the components in the view that do not involve the model
     */
    private ActionListener viewModifier = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand() == "selectToken") {
                if(tokenButtonToMove != null) {
                    tokenButtonToMove.setBackground(defaultButtonColor);
                }
                tokenButtonToMove = (TokenButton) e.getSource();
                tokenButtonToMove.setBackground(new Color(60, 200, 30));
            } else if (e.getActionCommand() == "moveTo" || e.getActionCommand() == "moveToYours" ||
                    e.getActionCommand() == "moveToPartner") {
                if(tokenButtonToMove != null) {
                    // the panel which initially holds the TokenButton
                    JPanel sourcePanel = getSourceOfTokenButtonPanelAccordingToSelectedButton();
                    // the panel corresponding to selected button
                    JPanel destinationPanel = getDestinationOfTokenButtonPanel((JButton) e.getSource());
                    if(sourcePanel != destinationPanel) {
                        // move the button from source to destination
                        destinationPanel.add(tokenButtonToMove);
                        sourcePanel.remove(tokenButtonToMove);
                        if(unassignedTokensPanel.getComponentCount() == 0) {
                            // add the sendOfferButton when all the tokens are distributed
                            if(isSendButtonOnScreen == false) {
                                unassignedTokensPanel.add(sendButton);
                                isSendButtonOnScreen = true;
                            }
                        } else {
                            // remove the sendOfferButton when a token has been unselected
                            if(isSendButtonOnScreen && destinationPanel == unassignedTokensPanel) {
                                unassignedTokensPanel.remove(sendButton);
                                isSendButtonOnScreen = false;
                            }
                        }
                        repaint();
                        revalidate();
                    }
                    tokenButtonToMove.setBackground(defaultButtonColor);
                    tokenButtonToMove = null;
                }
            }
        }
    };


    /**
     * Preloads images
     */
    public static void loadImages() {
        try {
            if (tokenImages.isEmpty()) {
                for (Model.Color color : Model.Color.values()) {
                    tokenImages.put(color,
                            ImageIO.read(OfferPane.class.getResource("/" + color + ".png")));
                }
            }
            if(auxiliaryImages.isEmpty()) {
                auxiliaryImages.put("redFlag", ImageIO.read(OfferPane.class.getResource("/RED_FLAG.png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * The method checks which of the three panels contains the selected button and returns that panel
     */
    private JPanel getSourceOfTokenButtonPanelAccordingToSelectedButton() {
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
     * The method is designed to only be called with the arguments yourTokensButton, partnerTokensButton
     * or unassignedTokensButton
     * @param source The pressed button
     * @return The panel on the view corresponding to button source
     */
    private JPanel getDestinationOfTokenButtonPanel(JButton source) {
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
        yourTokensButton.addActionListener(viewModifier);

        // "Partner's tokens" button
        partnerTokensButton = new JButton("Partner's Tokens");
        partnerTokensButton.setHorizontalAlignment(JLabel.CENTER);
        partnerTokensButton.setBackground(Color.LIGHT_GRAY);
        partnerTokensButton.setOpaque(true);
        partnerTokensButton.setPreferredSize(new Dimension(labelWidth, labelHeight));
        partnerTokensButton.setActionCommand("moveToPartner");
        partnerTokensButton.addActionListener(controller);
        partnerTokensButton.addActionListener(viewModifier);

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
        unassignedTokensButton.addActionListener(viewModifier);

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
    }

    /**
     * The method adds the buttons on the unassignedTokensPanel
     * corresponding to all the grid's tokens in play
     */
    private void addButtonsToUnassignedTokensPanel() {
        for(Token token : grid.getAllTokensInPlay()) {
            TokenButton tokenButton = new TokenButton(token);
            tokenButton.setActionCommand("selectToken");
            tokenButton.setPreferredSize(new Dimension(40, 40));
            Image scaledTokenImage =  tokenImages.get(token.getColor()).getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            tokenButton.setIcon(new ImageIcon(scaledTokenImage));
            tokenButton.addActionListener(controller);
            tokenButton.addActionListener(viewModifier);
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

    public OfferPane(Grid grid, GameController controller) {
        this.grid = grid;
        this.controller = controller;
        grid.addListener(this);
        loadImages();
        setUp();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "initiatingOffer":
                if(grid.getCurrentPlayer() instanceof HumanPlayer) {
                    this.add(centerPanel, BorderLayout.CENTER);
                    addButtonsToUnassignedTokensPanel();
                    revalidate();
                }
                break;
            case "offerFinished":
                this.remove(centerPanel);
                resetOfferPanel();
                revalidate();
                break;
        }
    }
}
