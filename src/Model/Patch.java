package Model;
import java.util.ArrayList;

public class Patch {
    private Color color;

    public Color getColor() {
        return color;
    }

    public void moveToPatch(ArrayList<Token> tokens, Patch goal, Patch current, ColoredTrailsPlayer player) {
        Token tokenToSpend = new Token; //no clue how to work with that, lets try
        ArrayList<Token> tokensLeft = tokens;
        //we have to make an assumption that the player moves only to its neighbour, otherwise this violates the rules
        private int isFound = 0;
        for(Token token : tokens) {
            if(token.getColor() == goal.getColor()) {
                isFound = 1;
                tokenToSpend = token;
                break;
            }
        }

        tokensLeft.remove(tokenToSpend);
        if(isFound == 1) {
            player.currentPatch = goal;
            player.tokens = tokensLeft;
        }


    }


}
