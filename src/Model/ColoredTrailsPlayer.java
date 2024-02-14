package Model;

import java.util.ArrayList;
import java.util.Random;

public abstract class ColoredTrailsPlayer {
    private static ArrayList<Integer> hashes = new ArrayList<>();
    protected Patch goal;
    protected Patch goalPartner;
    protected Patch goalToCommunicate;
    protected ArrayList<ArrayList<Token>> offerPartner;
    protected Grid grid;
    private final int hash = generateHash();
    private int playerPosition;             //make this actually useful instead of a ghost variable

    /**
     * Generates a random number smaller or equal than 100000, that is not already assigned to any player
     * @return the generated random hash
     */
    private int generateHash() {
        Random random = new Random();
        Integer randomHash = (Integer) random.nextInt(100000);
        while (hashes.contains(randomHash)) {
            randomHash = (Integer) random.nextInt(100000);
        }
        hashes.add((Integer) randomHash);
        return randomHash;
    }
    public ColoredTrailsPlayer() {
        goalPartner = null;
        goalToCommunicate = null;
    }

    /**
     * Cannot be overloaded by child classes
     * @return the hash of the player
     */
    public final int getHash() {
        return hash;
    }
    public int getPlayerPosition() {
        return playerPosition;
    }

    public Patch getGoal() {
        return goal;
    }
    public abstract void revealGoal();
    public abstract void listenToGoal(Patch goal);
    public abstract void makeOffer();

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
