package Model;

import java.beans.PropertyChangeEvent;

public class HumanPlayer extends ColoredTrailsPlayer{

    public HumanPlayer(Patch goal) {
        super(goal);
    }

    @Override
    public void communicateGoal(ColoredTrailsPlayer otherPlayer, Patch goalToCommunicate) {
        otherPlayer.propertyChange(new PropertyChangeEvent(this, "Communicate", null, goalToCommunicate));
    }
    @Override
    public void moveToPatch() {

    }
}
