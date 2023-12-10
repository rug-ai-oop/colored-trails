package Model;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Grid {
    private ArrayList<Patch> patches;
    private ArrayList<ColoredTrailsPlayer> players;

    public Grid(ArrayList<Patch> patches, ArrayList<ColoredTrailsPlayer> players) {
        this.patches = patches;
        this.players = players;
    }

    public void initiateOffer() {
        
    }
    public void acceptOffer() {

    }

    public void rejectOffer() {

    }

    public ArrayList<Patch> getPatches() {
        return patches;
    }

    public ArrayList<ColoredTrailsPlayer> getPlayers() {
        return players;
    }
}