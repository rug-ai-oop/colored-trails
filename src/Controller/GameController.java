package Controller;

import Model.*;
import View.model.IndexButton;
import View.model.TokenButton;
import View.model.OfferHistoryPane;
import View.model.MainPanel;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private Grid grid;
    private MainPanel mainPanel;
    private Token selectedToken;


    public GameController(Grid grid, MainPanel mainPanel) {
        this.grid = grid;
        this.mainPanel = mainPanel;
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
            case "Human vs. Human":
                mainPanel.setGameOption("Human vs. Human");
                mainPanel.showCard("DummyOptions");
                break;
            case "Human vs. Agent":
                mainPanel.setGameOption("Human vs. Agent");
                mainPanel.showCard("TomSelection");
                break;
            case "Agent vs. Agent":
                mainPanel.setGameOption("Agent vs. Agent");
                mainPanel.showCard("TomSelection");
                break;
            case "Human vs. Human like agent":
                mainPanel.setGameOption("Human vs. Human like agent");
                mainPanel.showCard("TomSelection");
                break;
            case "dummy1":
            case "dummy2":
            case "dummy3":
                //actually start the game based on game option and the tom if there is an agent
                break;
            case "BackFromDummy":
                String previousPanel = mainPanel.getSelectedOption().equals("Human vs. Human") ? "GameOptions" : "TomSelection";
                mainPanel.showCard(previousPanel);;
                break;
            case "BackFromTom":
                mainPanel.showCard("GameOptions");;
                break;
            case "0":
                mainPanel.setSelectedTom(0);
                mainPanel.showCard("DummyOptions");
                break;
            case "1":
                mainPanel.setSelectedTom(1);
                mainPanel.showCard("DummyOptions");
                break;
            case "2":
                mainPanel.setSelectedTom(2);
                mainPanel.showCard("DummyOptions");
                break;

        }
    }
}
