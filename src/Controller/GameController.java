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
                break;
            case "acceptOffer" :
                humanPlayer.acceptOffer();
                break;
            case "rejectOffer" :
                humanPlayer.rejectOffer();
                break;
            case "communicateGoal" :
                humanPlayer.communicateGoal(humanPlayer.getPartner(), ( (ColoredTrailsPlayer)(e.getSource()) ).getGoalToCommunicate());
                break;
        }
    }
}
