package View.controller;

import Model.Grid;
import Model.HumanPlayer;
import View.model.GridPane;
import View.model.OfferPane;
import View.model.TokenButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ViewController implements ActionListener {
    private GridPane gridPane;
    private OfferPane offerPane;
    //Hold the last panel on the grid to display the next card
    private JPanel lastSelectedPatchPanel;

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void setOfferPane(OfferPane offerPane) {
        this.offerPane = offerPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "selectToken")) {
            if (offerPane.getTokenButtonToMove() != null) {
                offerPane.getTokenButtonToMove().setBackground(OfferPane.defaultButtonColor);
            }
            offerPane.setTokenButtonToMove((TokenButton) e.getSource());
            offerPane.getTokenButtonToMove().setBackground(new Color(60, 200, 30));
        } else if (Objects.equals(e.getActionCommand(), "moveTo") || Objects.equals(e.getActionCommand(), "moveToYours") ||
                Objects.equals(e.getActionCommand(), "moveToPartner")) {
            if (offerPane.getTokenButtonToMove() != null) {
                // the panel which initially holds the TokenButton
                JPanel sourcePanel = offerPane.getSourceOfTokenButtonPanelAccordingToSelectedButton();
                // the panel corresponding to selected button
                JPanel destinationPanel = offerPane.getDestinationOfTokenButtonPanel((JButton) e.getSource());
                if (sourcePanel != destinationPanel) {
                    // move the button from source to destination
                    destinationPanel.add(offerPane.getTokenButtonToMove());
                    sourcePanel.remove(offerPane.getTokenButtonToMove());
                    if (offerPane.getUnassignedTokensPanel().getComponentCount() == 0) {
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
        } else if (Objects.equals(e.getActionCommand(), "selectPatch")) {
            if (gridPane.getGrid().getGameState() != Grid.STATE.INACTIVE && gridPane.isAllowToPickPatch()) {
                if (gridPane.getGrid().getCurrentPlayer() instanceof HumanPlayer) {
                    JButton pressedButton = (JButton) e.getSource();
                    // The parent of the parent
                    JPanel panelToChange = (JPanel) (pressedButton.getParent()).getParent();
                    CardLayout cardLayout = (CardLayout) panelToChange.getLayout();

                    if (lastSelectedPatchPanel != null) {
                        // Change the appearances of the panel of the last pressed button
                        CardLayout lastCardLayout = (CardLayout) lastSelectedPatchPanel.getLayout();
                        lastCardLayout.next(lastSelectedPatchPanel);
                        lastSelectedPatchPanel.repaint();
                        lastSelectedPatchPanel.revalidate();
                    }
                    // Change the appearances of the selected panel
                    cardLayout.next(panelToChange);

                    panelToChange.repaint();
                    panelToChange.revalidate();
                    gridPane.repaint();
                    gridPane.revalidate();

                    //Update the lastSelectedPanel
                    lastSelectedPatchPanel = panelToChange;
                }
            }
        } else if (Objects.equals(e.getActionCommand(), "yes")
                || Objects.equals(e.getActionCommand(), "no")) {
            if (lastSelectedPatchPanel != null) {
                JButton pressedButton = (JButton) ((JPanel) lastSelectedPatchPanel.getComponent(0)).getComponent(0);
                if (gridPane.isAllowToPickPatch()) {
                    // Change the appearances of the panel of the last pressed button
                    CardLayout lastCardLayout = (CardLayout) lastSelectedPatchPanel.getLayout();
                    lastCardLayout.next(lastSelectedPatchPanel);
                    if (Objects.equals(e.getActionCommand(), "yes")) {
                        pressedButton.setText(("Revealed as goal at round " + gridPane.getGrid().getNumberOfTurns()));
                        gridPane.setAllowToPickPatch(false);
                        pressedButton.setEnabled(false);
                    }
                    lastSelectedPatchPanel.repaint();
                    lastSelectedPatchPanel.revalidate();
                }
                lastSelectedPatchPanel = null;
            }
        } else if (Objects.equals(e.getActionCommand(), "yesCommunicate") ||
                Objects.equals(e.getActionCommand(), "noCommunicate")) {
            if (Objects.equals(e.getActionCommand(), "yesCommunicate")) {
                gridPane.setAllowToPickPatch(true);
            }
            gridPane.getDialog().dispose();
        }
    }
}
