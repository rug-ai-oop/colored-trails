package View;

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

    private void setUp() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        OfferPane.loadImages();
        mainPanel.setBackground(OfferPane.defaultButtonColor);
        this.setBackground(OfferPane.defaultButtonColor);
        scrollPane.setBackground(OfferPane.defaultButtonColor);
        this.add(mainPanel);
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
        middlePanel.setBackground(OfferPane.defaultButtonColor);
        middlePanel.setPreferredSize(new Dimension(80, 40));

        JPanel leftOfferPanel = new JPanel();
        int numberOfLeftTokens = offer.get(0).size();
        leftOfferPanel.setLayout(new GridLayout(1, numberOfLeftTokens));

        JPanel rightOfferPanel = new JPanel();
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
