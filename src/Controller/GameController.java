package Controller;

import Model.ColoredTrailsPlayer;
import Model.HumanPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private HumanPlayer humanPlayer;  //remove it when game is implemented

    public GameController(HumanPlayer humanPlayer) {
        this.humanPlayer = humanPlayer;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
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
        }

    }
}
