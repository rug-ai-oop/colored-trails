package Model;

import java.util.ArrayList;

public abstract class ColoredTrailsPlayer {
    private boolean hasReceivedSignal;
    private Patch goal;
    private ArrayList tokens;
    private Patch currentPatch;
    public ColoredTrailsPlayer(Patch goal) {
        this.goal = goal;
        hasReceivedSignal = false;
    }
    public abstract void initiateOffer();
    public abstract void acceptOffer();
    public abstract void rejectOffer();
    public abstract Patch communicateGoal(ColoredTrailsPlayer otherPlayer);
    public abstract void moveToPatch();

    public void setHasReceivedSignal(boolean hasReceivedSignal) {
        this.hasReceivedSignal = hasReceivedSignal;
    }

    public ArrayList<> getTokens() {
        return ( (ArrayList) tokens.clone() );
    }
}
