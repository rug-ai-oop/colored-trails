package Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public abstract class ColoredTrailsPlayer implements PropertyChangeListener {
    protected Patch goal;
    protected Patch goalPartner;
    protected Patch goalToCommunicate;
    protected Patch currentPatch;
    public ColoredTrailsPlayer(Patch goal) {
        this.goal = goal;
        goalPartner = null;
        goalToCommunicate = null;
    }

    public abstract void communicateGoal(ColoredTrailsPlayer otherPlayer, Patch goalToCommunicate);
    public abstract void moveToPatch();

    public void setGoalToCommunicate(Patch goalToCommunicate) {
        this.goalToCommunicate = goalToCommunicate;
    }

    public Patch getGoalToCommunicate() {
        return goalToCommunicate;
    }

}
