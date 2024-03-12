package Model;
import java.util.*;
public class SearchNode {
    int position;   //index in the array of patches indicating position
    ArrayList<Token> tokens; //tokens available when reached that position
    int utility; // Utility of this node

    public SearchNode(int position, ArrayList<Token> tokens, int cost) {
        this.position = position;
        this.tokens = tokens;
        this.utility = cost;
    }
}
