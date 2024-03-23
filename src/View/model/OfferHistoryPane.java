package View.model;

import Model.Grid;
import Model.HumanPlayer;
import Model.Token;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

public class OfferHistoryPane extends JPanel implements PropertyChangeListener, AllowedToListen {
    private Grid grid;
    private HumanPlayer playerToDisplayOnTheLeft;
    private JScrollPane scrollPane;
    private JPanel mainPanel = new JPanel();
    private JPanel labelPanel = new JPanel();
    private JLabel yourTokens = new JLabel("Your tokens");
    private JLabel partnerTokens = new JLabel("Partner's tokens");
    private JPanel middlePanel = new JPanel();
    private Dimension middlePanelDimension = new Dimension(80, 40);
    private Dimension sidePanelDimension = new Dimension(320, 40);

    private void setUp() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        OfferPane.loadImages();
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

    public OfferHistoryPane(Grid grid, HumanPlayer playerToDisplayOnTheLeft) {
        this.grid = grid;
        grid.addListener(this);
        this.playerToDisplayOnTheLeft = playerToDisplayOnTheLeft;
        setUp();
    }

    private void addOffer(ArrayList<ArrayList<Token>> offer) {
        JPanel mainOfferPanel = new JPanel();
        mainOfferPanel.setLayout(new BorderLayout());

        JPanel middlePanel = new JPanel();
        middlePanel.setPreferredSize(middlePanelDimension);
        middlePanel.setBackground(Color.LIGHT_GRAY);

        JPanel leftOfferPanel = new JPanel();
        leftOfferPanel.setPreferredSize(sidePanelDimension);
        int numberOfLeftTokens = offer.get(0).size();
        leftOfferPanel.setLayout(new GridLayout(1, numberOfLeftTokens));

        JPanel rightOfferPanel = new JPanel();
        rightOfferPanel.setPreferredSize(sidePanelDimension);
        int numberOfRightTokens = offer.get(1).size();
        rightOfferPanel.setLayout(new GridLayout(1, numberOfRightTokens));

        // Adds the tokens to the left panel of the offer panel
        for(Token token : offer.get(0)) {
            JLabel tokenLabel = new JLabel();
            tokenLabel.setPreferredSize(new Dimension(40, 40));
            Image scaledTokenImage =  OfferPane.tokenImages.get(token.getColor()).
                    getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            tokenLabel.setIcon(new ImageIcon(scaledTokenImage));
            leftOfferPanel.add(tokenLabel);
        }

        // Adds the tokens to the right panel of the offer panel
        for(Token token : offer.get(1)) {
            JLabel tokenLabel = new JLabel();
            tokenLabel.setPreferredSize(new Dimension(40, 40));
            Image scaledTokenImage =  OfferPane.tokenImages.get(token.getColor()).
                    getScaledInstance(40, 20, Image.SCALE_SMOOTH);
            tokenLabel.setIcon(new ImageIcon(scaledTokenImage));
            rightOfferPanel.add(tokenLabel);
        }

        mainOfferPanel.add(leftOfferPanel, BorderLayout.WEST);
        mainOfferPanel.add(middlePanel, BorderLayout.CENTER);
        mainOfferPanel.add(rightOfferPanel, BorderLayout.EAST);

        mainPanel.add(mainOfferPanel);
        revalidate();
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
