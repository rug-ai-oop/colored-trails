package Model;
import java.util.ArrayList;

public class Patch {
    private Color color;
    private int x;
    private int y;
    public Patch(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void moveToPatch(ArrayList<Token> tokens, Patch goal, Patch current, ColoredTrailsPlayer player) {
        int distance = Grid.distanceBetween(current, goal);
        if (distance > 1) {
            System.out.println("Too large of a step");
            return;
        }
        Token tokenToSpend = new Token;
        ArrayList<Token> tokensLeft = tokens;
        private int isFound = 0;
        for(Token token : tokens) {
            if(token.getColor() == goal.getColor()) {
                isFound = 1;
                tokenToSpend = token;
                break;
            }
        }

        //tokensLeft.remove(tokenToSpend);

        if(isFound == 1) {  //change to setters when implemented in coloredTrailsPlayer
            player.currentPatch = goal;
            player.tokens.remove(tokenToSpend);
        }
        else {
            System.out.println("Impossible to travel to the selected tile");
        }
    }
}
