package Controller;

import Model.*;
import View.model.game.IndexButton;
import View.model.game.TokenButton;

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
        switch (e.getActionCommand()) {
            case "selectToken":
                selectedToken = (((TokenButton) e.getSource()).getToken());
                break;
            case "moveToYours":
                ColoredTrailsPlayer currentPlayer0 = grid.getCurrentPlayer();
                if(currentPlayer0 instanceof HumanPlayer && selectedToken != null) {
                    ((HumanPlayer) currentPlayer0).addTokenToSupposedOwnTokens(selectedToken);
                }
                selectedToken = null;
                break;
            case "moveToPartner", "moveTo":
                ColoredTrailsPlayer currentPlayer1 = grid.getCurrentPlayer();
                if(currentPlayer1 instanceof HumanPlayer && selectedToken != null) {
                    ((HumanPlayer) currentPlayer1).removeFromSupposedOwnTokens(selectedToken);
                }
                selectedToken = null;
                break;
            case "send":
                ColoredTrailsPlayer currentPlayer2 = grid.getCurrentPlayer();
                if(currentPlayer2 instanceof HumanPlayer) {
                    ((HumanPlayer) currentPlayer2).setState(HumanPlayer.State.OFFER_COMPLETE);
                }
                selectedToken = null;
                break;
            case "selectPatch":
                ColoredTrailsPlayer currentPlayer3 = grid.getCurrentPlayer();
                if (currentPlayer3 instanceof HumanPlayer) {
                    int buttonIndex = ((IndexButton) e.getSource()).getIndex();
                    ((HumanPlayer) currentPlayer3).setState(HumanPlayer.State.INCOMPLETE);
                    currentPlayer3.setGoalToCommunicate(grid.getPatches().get(buttonIndex));
                }
                break;
            case "yes":
                ColoredTrailsPlayer currentPlayer4 = grid.getCurrentPlayer();
                if(currentPlayer4 instanceof HumanPlayer) {
                    if (currentPlayer4.getGoalToCommunicate() != null) {
                        ((HumanPlayer) currentPlayer4).setState(HumanPlayer.State.GOAL_COMPLETE);
                    }
                }
                break;
            case "no":
                ColoredTrailsPlayer currentPlayer5 = grid.getCurrentPlayer();
                currentPlayer5.setGoalToCommunicate(null);
                break;
            case "yesCommunicate":
                break;
            case "noCommunicate":
                ColoredTrailsPlayer currentPlayer6 = grid.getCurrentPlayer();
                if(currentPlayer6 instanceof HumanPlayer) {
                    currentPlayer6.setGoalToCommunicate(null);
                    ((HumanPlayer) currentPlayer6).setState(HumanPlayer.State.GOAL_COMPLETE);
                }
                break;
            case "accept":
                ColoredTrailsPlayer currentPlayer7 = grid.getCurrentPlayer();
                if(currentPlayer7 instanceof HumanPlayer) {
                    ((HumanPlayer) currentPlayer7).setState(HumanPlayer.State.OFFER_ACCEPTED);
                }
                break;
            case "reject":
                ColoredTrailsPlayer currentPlayer8 = grid.getCurrentPlayer();
                if(currentPlayer8 instanceof HumanPlayer) {
                    ((HumanPlayer) currentPlayer8).setState(HumanPlayer.State.OFFER_REJECTED);
                }
                break;
            case "withdrawGame":
                System.out.println("A player exited the negotiations");
                grid.endGame();
                break;
        }
    }
}
