package Model;

import java.util.ArrayList;

public class HumanPlayer extends ColoredTrailsPlayer{

    public enum OfferState {
        COMPLETE, INCOMPLETE
    }
    private volatile OfferState state = OfferState.INCOMPLETE;
    private ArrayList<Token> supposedOwnTokens;

    /**
     * Removes all tokens from the supposedOwnTokens
     */
    private void removeAllSupposedTokens() {
        int numberOfSupposedTokens = supposedOwnTokens.size();
        for(int i = 0; i < numberOfSupposedTokens; i++) {
            supposedOwnTokens.remove(0);
        }
    }

    /**
     * Alternative for .removeAll(), since the tokens are copied and the objects do not correspond
     * @return the hand of the partner
     */
    private ArrayList<Token> constructPartnerHand() {
        ArrayList<Token> partnerHand = grid.getAllTokensInPlay();
        if(supposedOwnTokens != null) {
            for (int i = 0; i < supposedOwnTokens.size(); i ++) {
                for (int j = 0; j < partnerHand.size(); j ++) {
                    if(supposedOwnTokens.get(i).getColor() == partnerHand.get(j).getColor()) {
                        partnerHand.remove(j);
                        break;
                    }
                }
            }
        }
        return partnerHand;
    }

    public String getName() {
        return name;
    }

    public HumanPlayer() {
        super();
        supposedOwnTokens = new ArrayList();
    }
    public HumanPlayer(String name) {
        super(name);
        supposedOwnTokens = new ArrayList();
    }
    @Override
    public void revealGoal() {

    }

    @Override
    public void receiveOffer(ArrayList<ArrayList<Token>> offer) {

    }
    @Override
    public void listenToGoal(Patch goal) {

    }

    public void setState(OfferState state) {
        this.state = state;
    }

    /**
     * Adds token to supposedOwnTokens
     * @param token
     */
    public void addTokenToSupposedOwnTokens(Token token) {
        if(!supposedOwnTokens.contains(token)) {
            supposedOwnTokens.add(token);
        }
    }

    /**
     * Removes token from supposedOwnTokens
     * @param token
     */
    public void removeFromSupposedOwnTokens(Token token) {
        if(supposedOwnTokens.contains(token)) {
            supposedOwnTokens.remove(token);
        }
    }


    /**
     * The method waits until the offer is complete, when it sets the offer in the grid
     */
    @Override
    public ArrayList<ArrayList<Token>> makeOffer() {
        removeAllSupposedTokens();
        while(state != OfferState.COMPLETE) {

        }
        ArrayList<ArrayList<Token>> offer = new ArrayList(2);
        ArrayList<Token> partnerHand = constructPartnerHand();
        offer.add(0, supposedOwnTokens);
        offer.add(1, partnerHand);
        state = OfferState.INCOMPLETE;
        return offer;
    }


}
