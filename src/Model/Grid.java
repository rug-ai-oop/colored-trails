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

    public int distanceBetween(Patch x, Patch y)
    {
        int x_coord = x.getX();
        int y_coord = y.getY();
        int distance = Math.sqrt(x_coord*x_coord+y_coord*y_coord);
        return distance;
    }
}