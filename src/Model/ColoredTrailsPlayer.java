package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public abstract class ColoredTrailsPlayer implements Serializable {
    private static ArrayList<Integer> hashes = new ArrayList<>();
    protected Patch goalPartner;
    protected Patch goalToCommunicate;
    protected ArrayList<ArrayList<Token>> offerPartner;
    protected Grid grid;
    private final int hash = generateHash();
    protected String name;

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
    public ColoredTrailsPlayer(String name) {
        this.name = name;
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

    public String getName() {
        if (name == null)
            return "Player " + hash;
        return name;
    }

    public abstract Patch revealGoal();
    public abstract void listenToGoal(Patch goal);
    public abstract ArrayList<ArrayList<Token>> makeOffer();

    public abstract void receiveOffer(ArrayList<ArrayList<Token>> offer);

    public void setGoalToCommunicate(Patch goalToCommunicate) {
        this.goalToCommunicate = goalToCommunicate;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Patch getGoalToCommunicate() {
        return goalToCommunicate;
    }


}
