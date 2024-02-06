package Model;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class HumanPlayer extends ColoredTrailsPlayer{


    @Override
    public void revealGoal() {

    }


    @Override
    public void listenToGoal(Patch goal) {

    }

    /**
     * Idea to implement this: make a private ArrayList offer and have the controller able to add and remove
     * tokens from the offer, until it presses send, when offer is sent to the grid
     * @return
     */
    @Override
    public ArrayList<Token> makeOffer() {

        return null;
    }

    @Override
    public void receiveOffer(ArrayList<ArrayList<Token>> offer) {

    }
}
