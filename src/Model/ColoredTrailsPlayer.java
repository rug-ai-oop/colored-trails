package Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public abstract class ColoredTrailsPlayer implements PropertyChangeListener {
    protected Patch goal;
    protected Patch goalPartner;
    protected Patch goalToCommunicate;
    protected ArrayList<Token> tokens;
    protected Patch currentPatch;
    private ColoredTrailsPlayer partner;
    public ColoredTrailsPlayer(Patch goal) {
        this.goal = goal;
        goalPartner = null;
        goalToCommunicate = null;
    }

    public abstract void communicateGoal(ColoredTrailsPlayer otherPlayer, Patch goalToCommunicate);
    public abstract void moveToPatch();

    public void setPartner(ColoredTrailsPlayer partner) {
        this.partner = partner;
    }

    public void setGoalToCommunicate(Patch goalToCommunicate) {
        this.goalToCommunicate = goalToCommunicate;
    }

    public ColoredTrailsPlayer getPartner() {
        return partner;
    }

    public Patch getGoalToCommunicate() {
        return goalToCommunicate;
    }

    public ArrayList<Token> getTokens() {
        return ( (ArrayList) tokens.clone() );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Communicate")) {
            goalPartner = (Patch) evt.getNewValue();
        }
    }
}
