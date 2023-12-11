package Model;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class HumanPlayer extends ColoredTrailsPlayer{


    @Override
    public void communicateGoal(ColoredTrailsPlayer otherPlayer, Patch goalToCommunicate) {

    }

    @Override
    public ArrayList<ArrayList<Token>> makeOffer(ArrayList<Token> ownTokens, ArrayList<Token> partnerTokens) {
        return null;
    }
}
