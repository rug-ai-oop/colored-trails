package Model;
import java.util.ArrayList;

public class Patch {
    private Color color;

    public Color getColor() {
        return color;
    }

    public void moveToPatch(ArrayList<Token> tokens, Patch goal, Patch current, ColoredTrailsPlayer player) {
        ArrayList<Token> tokensToSpend = new ArrayList<Token>; //no clue how to work with that, lets try
        ArrayList<Token> tokensLeft = tokens;
        //we have to make an assumption that the player moves only to its neighbour, otherwise this violates the rules




        if(tokensLeft.equals(tokens)) {
            //umm whats there reverse of equals
        }
        else
        {
            player.currentPatch = goal;
            player.tokens = tokensLeft;
            //set tokens
        }


    }


}
