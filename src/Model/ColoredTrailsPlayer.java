package Model;

import java.util.ArrayList;

public abstract class ColoredTrailsPlayer {
    private Patch goal;
    private ArrayList tokens;
    private Patch currentPatch;
    public abstract void initiateOffer();
    public abstract void acceptOffer();
    public abstract void rejectOffer();
    public abstract void communicateGoal();
    public abstract void moveToPatch();

    public ColoredTrailsPlayer(Patch goal) {
        this.goal = goal;
    }

}
