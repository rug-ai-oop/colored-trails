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
    private Grid grid;  //remove it when game is implemented
    private Token selectedToken;

    public GameController(Grid grid) {
        this.grid = grid;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ColoredTrailsPlayer currentPlayer = grid.getCurrentPlayer();
        switch (e.getActionCommand()) {
            case "initiateOffer" :
                //change to grid
                System.out.println("initiated");
                //close panel after clicking a button, using dispose() ?
                break;
            case "cancelOffer" :
                System.out.println("cancelled");
                break;
                //close panel after clicking a button
            case "acceptOffer" :
                //change to grid;
                System.out.println("accepted");
                break;
                //close panel after clicking a button
            case "rejectOffer" :
                //change
                System.out.println("rejected");
                break;
                //close panel after clicking a button
            case "communicateGoal" :
                break;
            case "move":
                System.out.println("moved");
                System.out.println( ((JButton) e.getSource()).getBackground());
                break;
            case "selectToken":
                selectedToken = (((TokenButton) e.getSource()).getToken());
                System.out.println(selectedToken.getColor());
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
