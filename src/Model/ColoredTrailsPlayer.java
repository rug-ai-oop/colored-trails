package Model;

import java.util.ArrayList;

public abstract class ColoredTrailsPlayer {
    private Patch goal;
    private Patch goalPartner;
    private Patch goalToCommunicate;
    private ArrayList<ArrayList<Token>> offerPartner;
    private Grid grid;
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
    public abstract void revealGoal();
    public abstract void listenToGoal(Patch goal);
    public abstract ArrayList<Token> makeOffer();

    public abstract void receiveOffer(ArrayList<ArrayList<Token>> offer);

    public void setGoalToCommunicate(Patch goalToCommunicate) {
        this.goalToCommunicate = goalToCommunicate;
    }

    public void setGoal(Patch goal) {
        this.goal = goal;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Patch getGoalToCommunicate() {
        return goalToCommunicate;
    }


}
