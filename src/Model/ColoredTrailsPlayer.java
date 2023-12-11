package Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public abstract class ColoredTrailsPlayer {
    protected Patch goal;
    protected Patch goalPartner;
    protected Patch goalToCommunicate;
    public ColoredTrailsPlayer() {
        goalPartner = null;
        goalToCommunicate = null;
    }

    public abstract void communicateGoal(ColoredTrailsPlayer otherPlayer, Patch goalToCommunicate);

    public abstract ArrayList<ArrayList<Token>> makeOffer(ArrayList<Token> ownTokens, ArrayList<Token> partnerTokens);

    public void setGoalToCommunicate(Patch goalToCommunicate) {
        this.goalToCommunicate = goalToCommunicate;
    }

    public void setGoal(Patch goal) {
        this.goal = goal;
    }

    public Patch getGoalToCommunicate() {
        return goalToCommunicate;
    }


}
