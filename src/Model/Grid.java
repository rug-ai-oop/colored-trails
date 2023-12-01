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

    public ArrayList<Patch> getPatches() {
        return patches;
    }

    public ArrayList<ColoredTrailsPlayer> getPlayers() {
        return players;
    }
}