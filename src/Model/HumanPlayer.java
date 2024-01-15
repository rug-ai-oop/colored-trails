package Model;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class HumanPlayer extends ColoredTrailsPlayer{


    @Override
    public void communicateGoal(Grid grid, Patch goalToCommunicate) {

    }

    @Override
    public ArrayList<Token> makeOffer(Grid grid) {
        return null;
    }

    @Override
    public void receiveOffer(Grid grid, ArrayList<Token> offer) {

    }
}
