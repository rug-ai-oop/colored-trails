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

    private int playerPosition;             //make this actually useful instead of a ghost variable
    public int getPlayerPosition() {
        return playerPosition;
    }

    public Patch getGoal() {
        return goal;
    }
    public abstract void communicateGoal(Grid grid, Patch goalToCommunicate);
    public abstract ArrayList<Token> makeOffer(Grid grid);

    public abstract void receiveOffer(Grid grid, ArrayList<Token> offer);

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
