package Model;
import Math;
import java.util.ArrayList;

public class Grid {
    private ArrayList<Patch> patches;
    public Grid(ArrayList<Patch> patches) {
        this.patches = patches;
    }
    public ArrayList<Patch> getPatches() {
        return patches;
    }

}