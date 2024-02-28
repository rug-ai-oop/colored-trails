package Controller;

import Model.ColoredTrailsPlayer;
import Model.Grid;
import Model.HumanPlayer;
import Model.Token;
import View.OfferPane;
import View.TokenButton;

import javax.swing.*;
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
                break;
            case "send":
                if(currentPlayer instanceof HumanPlayer) {
                    ((HumanPlayer) currentPlayer).setState(HumanPlayer.OfferState.COMPLETE);
                }
        }

    }
}
