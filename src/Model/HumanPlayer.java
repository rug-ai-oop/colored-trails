package Model;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class HumanPlayer extends ColoredTrailsPlayer{


    @Override
    public void communicateGoal(Patch goalToCommunicate) {

    }


    @Override
    public void listenToGoal(Patch goal) {

    }

    @Override
    public ArrayList<Token> makeOffer() {
        return null;
    }

    @Override
    public void receiveOffer(ArrayList<ArrayList<Token>> offer) {

    }
}
