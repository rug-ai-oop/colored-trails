package Controller;

import Model.*;
import View.model.IndexButton;
import View.model.TokenButton;
import View.model.OfferHistoryPane;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private Grid grid;
    private Token selectedToken;

    public GameController(Grid grid) {
        this.grid = grid;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ColoredTrailsPlayer currentPlayer = grid.getCurrentPlayer();
        switch (e.getActionCommand()) {
            case "selectToken":
                selectedToken = (((TokenButton) e.getSource()).getToken());
                break;
            case "moveToYours":
                if(currentPlayer instanceof HumanPlayer && selectedToken != null) {
                    ((HumanPlayer) currentPlayer).addTokenToSupposedOwnTokens(selectedToken);
                }
                selectedToken = null;
                break;
            case "moveToPartner", "moveTo":
                if(currentPlayer instanceof HumanPlayer && selectedToken != null) {
                    ((HumanPlayer) currentPlayer).removeFromSupposedOwnTokens(selectedToken);
                }
                selectedToken = null;
                break;
            case "send":
                if(currentPlayer instanceof HumanPlayer) {
                    ((HumanPlayer) currentPlayer).setState(HumanPlayer.State.OFFER_COMPLETE);
                }
                selectedToken = null;
                break;
            case "selectPatch":
                if (currentPlayer instanceof HumanPlayer) {
                    int buttonIndex = ((IndexButton) e.getSource()).getIndex();
                    ((HumanPlayer) currentPlayer).setState(HumanPlayer.State.INCOMPLETE);
                    currentPlayer.setGoalToCommunicate(grid.getPatches().get(buttonIndex));
                }
                break;
            case "yes":
                if(currentPlayer instanceof HumanPlayer) {
                    if (currentPlayer.getGoalToCommunicate() != null) {
                        ((HumanPlayer) currentPlayer).setState(HumanPlayer.State.GOAL_COMPLETE);
                    }
                }
                break;
            case "no":
                currentPlayer.setGoalToCommunicate(null);
                break;
            case "yesCommunicate":
                break;
            case "noCommunicate":
                if(currentPlayer instanceof HumanPlayer) {
                    currentPlayer.setGoalToCommunicate(null);
                    ((HumanPlayer) currentPlayer).setState(HumanPlayer.State.GOAL_COMPLETE);
                }
                break;
            case "accept":
                if(currentPlayer instanceof HumanPlayer) {
                    ((HumanPlayer) currentPlayer).setState(HumanPlayer.State.OFFER_ACCEPTED);
                }
                break;
            case "reject":
                if(currentPlayer instanceof HumanPlayer) {
                    ((HumanPlayer) currentPlayer).setState(HumanPlayer.State.OFFER_REJECTED);
                }
                break;
            case "withdrawGame":
                System.out.printf("A player exited the negotiations");
                grid.endGame();
                break;
        }
    }
}
