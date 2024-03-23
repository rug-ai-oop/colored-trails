package View.controller;

import View.model.GridPane;
import View.model.OfferPane;
import View.model.TokenButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewController implements ActionListener {
    private GridPane gridPane;
    private OfferPane offerPane;

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void setOfferPane(OfferPane offerPane) {
        this.offerPane = offerPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "selectToken") {
            if (offerPane.getTokenButtonToMove() != null) {
                offerPane.getTokenButtonToMove().setBackground(OfferPane.defaultButtonColor);
            }
            offerPane.setTokenButtonToMove((TokenButton) e.getSource());
            offerPane.getTokenButtonToMove().setBackground(new Color(60, 200, 30));
        } else if (e.getActionCommand() == "moveTo" || e.getActionCommand() == "moveToYours" ||
                e.getActionCommand() == "moveToPartner") {
            if (offerPane.getTokenButtonToMove() != null) {
                // the panel which initially holds the TokenButton
                JPanel sourcePanel = offerPane.getSourceOfTokenButtonPanelAccordingToSelectedButton();
                // the panel corresponding to selected button
                JPanel destinationPanel = offerPane.getDestinationOfTokenButtonPanel((JButton) e.getSource());
                if (sourcePanel != destinationPanel) {
                    // move the button from source to destination
                    destinationPanel.add(offerPane.getTokenButtonToMove());
                    sourcePanel.remove(offerPane.getTokenButtonToMove());
                    if (offerPane.getUnassignedTokensButton().getComponentCount() == 0) {
                        // add the sendOfferButton when all the tokens are distributed
                        if (offerPane.getIsSendButtonOnScreen() == false) {
                            offerPane.getUnassignedTokensPanel().add(offerPane.getSendButton());
                            offerPane.setIsSendButtonOnScreen(true);
                        }
                    } else {
                        // remove the sendOfferButton when a token has been unselected
                        if (offerPane.getIsSendButtonOnScreen() &&
                                destinationPanel == offerPane.getUnassignedTokensPanel()) {
                            offerPane.getUnassignedTokensPanel().remove(offerPane.getSendButton());
                            offerPane.setIsSendButtonOnScreen(false);
                        }
                    }
                    offerPane.repaint();
                    offerPane.revalidate();
                }
                offerPane.getTokenButtonToMove().setBackground(OfferPane.defaultButtonColor);
                offerPane.setTokenButtonToMove(null);
            }
        }
    }
}
