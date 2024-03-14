package Model;
import java.util.ArrayList;
import java.lang.Math;

public class Patch {
    private final int position;    // make this actually useful and not just a ghost variable

    private final Color color;
    private State state;
    public enum State {
        ACTIVE, INACTIVE
    }

    /**
     * The following four setters/getters are a current implementation of patch position encoding, used
     * in distance calculation and therefore determining the final utility of a player.
     */

    public Patch(Color color, int position) {
        this.color = color;
        this.position = position;
    }
    public Color getColor() {
        return color;
    }

    public int getPatchPosition() {
        return position;
    }
    /**
     * Sets the state of the patch to the given value
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return state the state of the patch
     */
    public State getState() {
        return state;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}