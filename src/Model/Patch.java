package Model;
import java.util.ArrayList;
import java.lang.Math;

public class Patch {
    public int position;    // make this actually useful and not just a ghost variable

    private Color color;
    private State state;
    public enum State {
        ACTIVE, INACTIVE;
    }

    /**
     * The following four setters/getters are a current implementation of patch position encoding, used
     * in distance calculation and therefore determining the final utility of a player.
     */
    private int x;
    private int y;
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public Patch(Color color) {
        this.color = color;
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

    /**
     * @return distance between two patches a and b
     */
    public int distanceBetween(Patch a, Patch b) {
        int x_coord = Math.abs(a.getX() - b.getX());
        int y_coord = Math.abs(a.getY() - b.getY());
        int distance = x_coord + y_coord;
        return distance;
    }

    /**
     * This function implements the logic of moving to a patch step by step, but should be changed
     * i.e. by returning the patch the player moved to, to allow for the grid to control this
     * @param tokens - tokens of a player
     * @param destination - destination patch
     * @param current - current patch
     * @param player - player willing to move <- TO CHANGE, should be controlled by the grid
     */
    public void moveToPatch(ArrayList<Token> tokens, Patch destination, Patch current, ColoredTrailsPlayer player) {
        int distance = distanceBetween(current, destination);
        if (distance != 1) {
            System.out.println("Too large of a step");
            return;
        }
        Token tokenToSpend = null;
        int isFound = 0;
        for(Token token : tokens) {
            if(token.getColor() == destination.getColor()) {
                isFound = 1;
                tokenToSpend = token;
                break;
            }
        }

        if(isFound == 1) {  //change when implemented in grid
            System.out.println();
            //player.currentPatch = destination;
            //player.tokens.remove(tokenToSpend);
        }
        else {
            System.out.println("Impossible to travel to the selected tile");
        }
    }

    public static void main(String[] args) {
        System.out.println();
    }
}