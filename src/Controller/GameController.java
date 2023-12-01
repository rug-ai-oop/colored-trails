package Controller;

import Model.ColoredTrailsPlayer;
import Model.HumanPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private HumanPlayer humanPlayer;

    public GameController(HumanPlayer humanPlayer) {
        this.humanPlayer = humanPlayer;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "initiateOffer" :
                humanPlayer.initiateOffer();
                System.out.println("initiated");
                //close panel after clicking a button, using dispose() ?
                break;
            case "cancelOffer" :
                System.out.println("cancelled");
                break;
                //close panel after clicking a button
            case "acceptOffer" :
                humanPlayer.acceptOffer();
                System.out.println("accepted");
                break;
                //close panel after clicking a button
            case "rejectOffer" :
                humanPlayer.rejectOffer();
                System.out.println("rejected");
                break;
                //close panel after clicking a button
            case "communicateGoal" :
                humanPlayer.communicateGoal(humanPlayer.getPartner(), ( (ColoredTrailsPlayer)(e.getSource()) ).getGoalToCommunicate());
                break;
            case "move":
                System.out.println("moved");
                break;
        }
    }
}
