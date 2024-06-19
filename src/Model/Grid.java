package Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.lang.Math;

import static java.lang.Math.abs;

public class Grid implements Serializable {
    /**
     * The order of using the grid is:
     *      1) Create the grid
     *      2) Add the players
     *      3) Add the listeners (By creating the view components)
     *      4) Set up the grid
     *      5) Start the grid
     */
    private int maximumNumberOfTurns;
    private int numberOfTurns;
    private int startPatchIndex;
    private ArrayList<Patch> patches = new ArrayList();
    private ArrayList<ColoredTrailsPlayer> players = new ArrayList(2);
    private ArrayList<PropertyChangeListener> listeners = new ArrayList();
    private ArrayList<PropertyChangeListener> temoporaryListeners = new ArrayList();
    private ArrayList<Token> allTokensInPlay = new ArrayList();
    private HashMap<ColoredTrailsPlayer, ArrayList<Token>> tokens = new HashMap();
    private HashMap<ColoredTrailsPlayer, ArrayList<ArrayList<Token>>> offers = new HashMap();
    private HashMap<ColoredTrailsPlayer, Patch> goals = new HashMap();
    private HashMap<ColoredTrailsPlayer, Patch> goalsToAnnounce = new HashMap();
    private ArrayList<Patch> goalsToSave = new ArrayList();
    private String wayOfAssigningGoals;
    private boolean isNotifyingListeners = false;

    public enum STATE {
        INACTIVE, ACTIVE, WAITING_FOR_OFFER, WAITING_FOR_GOAL
    }
    private STATE gameState;

    /* Private Methods */

    /**
     * @return an ArrayList<Color> with random values from the Color enum
     */
    private ArrayList<Color> generateRandomColorFiveByFive() {
        ArrayList<Color> colors = new ArrayList();
        Random random = new Random();
        int numberOfColors = (Color.values()).length;
        for(int i = 0; i < 25; i++) {
            colors.add(Color.values()[random.nextInt(numberOfColors)]);
        }
        return colors;
    }

    /**
     * Sets the goal of player
     * @param player
     * @param patch
     */
    private void setGoal(ColoredTrailsPlayer player, Patch patch) {
        goals.put(player, patch);
    }

    /**
     * @param numberOfTurns represents the number of turns the negotiation had
     * @return the player corresponding to the number of turns the negotiation had
     * modulo the size of the players list
     */
    private ColoredTrailsPlayer getPlayer(int numberOfTurns) {
        return players.get(numberOfTurns % players.size());
    }

    /**
     * Sets the gameState to the given parameter
     * @param gameState
     */
    private void setGameState(STATE gameState) {
        this.gameState = gameState;
    }

    /**
     * defines the allowed indices in the five by five grid
     * @return a patch with an allowed index
     */
    private Patch pickRandomGoalFiveByFive() {
        Random random = new Random();
        ArrayList<Integer> allowedIndices = new ArrayList<>(Arrays.asList(0, 1, 3, 4, 5, 9, 15, 19, 20, 21, 23, 24));
        Integer randomIndex = (Integer) random.nextInt(25);
        while( ! allowedIndices.contains( randomIndex ) ) {
            randomIndex = (Integer) random.nextInt(25);
        }
        return patches.get(randomIndex);
    }

    /**
     * if the players and patches have been created, assigns 4 tokens randomly to each player, OR
     * if the mapNumber is not 0, loads the tokens from the file grid_mapNumber.ser
     */
    private void distributeTokensToPlayers(Grid savedGrid) throws IllegalAccessException {
        if(players != null && patches != null) {
            if (savedGrid != null) {
                int playerToSkip = 1;
                int playerIndex;
                for(Token tokenToBeReceived : savedGrid.allTokensInPlay) {
                    //add 1 token to a player (back-and-forth)
                    playerIndex = 0;
                    for (ColoredTrailsPlayer playerToReceiveToken: this.players) {
                        if (playerToSkip == playerIndex){
                        } else {
                            // Initialize the ArrayList of tokens for the player
                            if (tokens.get(playerToReceiveToken) == null) {
                                tokens.put(playerToReceiveToken, new ArrayList());
                            }

                            //add tokens
                            System.out.println("adding token " + tokenToBeReceived.getColor() +  " to player: " + playerToReceiveToken.name);
                            tokens.get(playerToReceiveToken).add(tokenToBeReceived);
                            allTokensInPlay.add(tokenToBeReceived);
                        }
                        playerIndex++;
                    }
                    playerToSkip = 1 - playerToSkip;
                }
            }
            else {
                ArrayList<Patch> patchesCopy = (ArrayList<Patch>) patches.clone();
                Collections.shuffle(patchesCopy);
                for(Patch patchToBeReceived : patchesCopy) {
                    int numberOfDistributedTokens = patchesCopy.indexOf(patchToBeReceived);
                    if(numberOfDistributedTokens < 4 * players.size()) {
                        ColoredTrailsPlayer playerToReceiveToken = players.get(numberOfDistributedTokens % players.size());
                        Token tokenToBeReceived = new Token(patchToBeReceived.getColor());
                        if(tokens.get(playerToReceiveToken) == null) {
                            // Initialize the ArrayList of tokens for the player
                            tokens.put(playerToReceiveToken, new ArrayList());
                        }
                        tokens.get(playerToReceiveToken).add(tokenToBeReceived);
                        allTokensInPlay.add(tokenToBeReceived);
                    }
                }
            }
            notifyListeners(new PropertyChangeEvent(this, "tokensDistributed",
                    null, null));
        } else {
            throw new IllegalAccessException("The players and patches need to be created first");
        }
    }

    /**
     * creates 25 patches with the colors from generateRandomColorFiveByFive() or map generated before
     */
    private void generatePatchesFiveByFive(Grid savedGrid) {
        patches = new ArrayList<>();
        if (savedGrid != null) {
            patches = savedGrid.patches;
        }
        else {
            ArrayList<Color> colors = generateRandomColorFiveByFive();
            for(int i = 0; i < 25; i++) {
                patches.add(new Patch(colors.get(i), i));
                patches.get(i).setState(Patch.State.ACTIVE);
            }
        }

        patches.get(startPatchIndex).setState(Patch.State.INACTIVE);
        notifyListeners(new PropertyChangeEvent(this, "createdPatches", null, null));
    }

    /**
     * assigns DIFFERENT goals to each player && notifies the listeners that the players have been assigned
     */
    private void assignDifferentRandomGoalsToPlayers() {
        ArrayList<Patch> assignedPatches = new ArrayList();
        Patch goal = pickRandomGoalFiveByFive();
        for(ColoredTrailsPlayer player : players) {
            while(assignedPatches.contains(goal)) {
                goal = pickRandomGoalFiveByFive();
            }
            setGoal(player, goal);
            assignedPatches.add(goal);
            notifyListeners(new PropertyChangeEvent(player, "assignedGoalsIndex",
                    player, patches.indexOf(goal)));    // Uses the oldValue to pass the player with the goal
        }
    }

    /**
     * assigns the same goal to each player
     */
    private void assignSameRandomGoalsToPlayers() {
        Patch goal = pickRandomGoalFiveByFive();
        for(ColoredTrailsPlayer player : players) {
            setGoal(player, goal);
            notifyListeners(new PropertyChangeEvent(player, "assignedGoalsIndex",
                    player, patches.indexOf(goal)));    // Uses the oldValue to pass the player with the goal
        }
    }

    /**
     * The method lets the playerToBeAnnounced that the goal of its partner is goalOfPartner
     * @param playerToBeAnnounced: The player which is announced
     * @param goalOfPartner: The goal of the partner
     */
    private void announceGoal(ColoredTrailsPlayer playerToBeAnnounced, Patch goalOfPartner) {
        playerToBeAnnounced.listenToGoal(goalOfPartner);
    }

    /**
     * The method sends the offer of the playerToOffer to playerToReceive
     * @param playerToOffer: The player which made the offer
     * @param playerToReceive: The player receiving the offer
     */
    private void makeOffer(ColoredTrailsPlayer playerToOffer, ColoredTrailsPlayer playerToReceive) {
        // Clone the offer of the playerToOffer
        ArrayList<ArrayList<Token>> offer = (ArrayList<ArrayList<Token>>) offers.get(playerToOffer).clone();
        // Reverse the offer, so that the playerToReceive receives it with its offered hand at index 0
        Collections.reverse(offer);
        playerToReceive.receiveOffer(offer);
    }

    /**
     * Prints the colours of the tokens in the offers
     */
    public static void printOffer(ArrayList<ArrayList<Token>> offer) {
        for(ArrayList<Token> hand : offer) {
            String offerAsString = "";
            for(Token token : hand) {
                offerAsString += token.getColor();
                offerAsString += ", ";
            }
            System.out.println(offerAsString);
        }
    }


    /**
     * The method assumes that tokens must be preserved within negotiations, i.e. the tokens in play
     * remain the same.
     * An offer is legal if all the players get a hand and the tokens offered
     * were in play, i.e. the players do not introduce new tokens in the game, and the tokens are preserved.
     * @param offer the offer consisting of an ArrayList with an ArrayList with tokens for each player,
     *              representing their offered hand
     * @return true if the offer is legal, false otherwise
     */
    private boolean isOfferLegal(ArrayList<ArrayList<Token>> offer) {
        if(offer.size() != players.size()) {    //check that the players offered something for all players
            return false;
        }
        ArrayList<Token> allTokensInOffer = new ArrayList<>();
        for(ArrayList<Token> hand : offer) {
            allTokensInOffer.addAll(hand);
        }
        if(allTokensInPlay.size() != allTokensInOffer.size() ) {
            return false;
        }
        for(int i = 0; i < allTokensInPlay.size(); i++) {
            for(int j = 0; j < allTokensInOffer.size(); j++) {
                if(allTokensInPlay.get(i).getColor() == allTokensInOffer.get(j).getColor()) {
                    allTokensInOffer.remove(j);
                    break;
                }
            }
        }
        if(!allTokensInOffer.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * An offer is an ArrayList that contains two ArrayLists, each representing the collection
     * of tokens of each player. The first one is the proposed collection of the player offering,
     * second one is the one of the one being offered. First reverses the posterior offer
     * to ensure that the collections checked belong to the same player. Checks if the offers are the same.
     * @param priorOffer    The initial offer
     * @param posteriorOffer    The counter-offer
     * @return true if the offers are the same, false otherwise
     */
    private boolean acceptedOffer(ArrayList<ArrayList<Token>> priorOffer, ArrayList<ArrayList<Token>> posteriorOffer) {
        ArrayList<ArrayList<Token>> posteriorOfferReversed = (ArrayList<ArrayList<Token>>) posteriorOffer.clone();
        Collections.reverse(posteriorOfferReversed);
        if (priorOffer.size() != posteriorOffer.size()) {
            return false;
        }

        Comparator<Token> tokenComparator = (Comparator<Token>) (t1, t2) -> Color.getColorPriority(t1.getColor()) - Color.getColorPriority(t2.getColor());
        for(int index = 0; index < priorOffer.size(); index++) {
            if( priorOffer.get(index).size() != posteriorOfferReversed.get(index).size()) {
                return false;
            }
            Collections.sort(priorOffer.get(index), tokenComparator);
            Collections.sort(posteriorOfferReversed.get(index), tokenComparator);
        }
        for(int index = 0; index < priorOffer.size(); index++) {
            for(int indexInHands = 0; indexInHands < priorOffer.get(index).size(); indexInHands++){
                if(priorOffer.get(index).get(indexInHands).getColor() != posteriorOfferReversed.get(index).get(indexInHands).getColor()) {
                    return false;
                }
            };
        }

        System.out.println("Accepted");
        return true;
    }

    /* Public Methods */


    /**
     * Public constructor:
     * sets the state of the grid to inactive and, by default,
     * the distributionOfPatches is set to random and different
     */
    public Grid() {
        this.gameState = STATE.INACTIVE;
        this.wayOfAssigningGoals = "randDif";
        this.maximumNumberOfTurns = 40;
        this.numberOfTurns = 0;
        this.startPatchIndex = 12;
    }

    /**
     * ends the game on player request
     */
    public void endGame() {
        this.gameState = STATE.INACTIVE;
    }
    /**
     * @return The current player in turn
     */
    public ColoredTrailsPlayer getCurrentPlayer() {
        return getPlayer(numberOfTurns);
    }

    /**
     * @return A clone of the offer of the partner of the current player
     */
    public ArrayList<ArrayList<Token>> getPartnerOffer() {
        return (ArrayList<ArrayList<Token>>) offers.get(getPlayer(numberOfTurns + 1)).clone();
    }

    /**
     * @param player: The player which the tokens belong to
     * @return A clone of tokens of the player
     */
    public ArrayList<Token> getTokens(ColoredTrailsPlayer player) {
        return (ArrayList) tokens.get(player).clone();
    }


    /**
     * @return A clone of allTokensInPlay
     */
    public ArrayList<Token> getAllTokensInPlay() {
        return (ArrayList) allTokensInPlay.clone();
    }

    /**
     * @return the state of the game
     */
    public STATE getGameState() {
        return gameState;
    }
    /**
     * adds a player to the list of players, sets the grid of the player to this grid,
     * and associates null to the player as its supposed goal to be announced
     * @param player The player to be added
     */
    public void addPlayer(ColoredTrailsPlayer player) {
        players.add(player);
        player.setGrid(this);
        goalsToAnnounce.put(player, null);
        offers.put(player, new ArrayList(2));
        offers.get(player).add(new ArrayList<>());
        offers.get(player).add(new ArrayList<>());
    }

    /**
     * @param listener PropertyChangeListener which is added to the listeners of the grid
     */
    public void addListener(PropertyChangeListener listener) {
        if (isNotifyingListeners) {
            temoporaryListeners.add(listener);
        } else {
            listeners.add(listener);
        }
    }

    /**
     * Notifies the listeners
     * @param evt PropertyChangeEvent passed in the propertyChange()
     */
    public void notifyListeners(PropertyChangeEvent evt) {
        isNotifyingListeners = true;
        for(PropertyChangeListener listener : listeners) {
            listener.propertyChange(evt);
        }
        isNotifyingListeners = false;
        listeners.addAll(temoporaryListeners);
        temoporaryListeners.removeAll(temoporaryListeners);
    }
    /**
     * assigns the goals according to the wayOfAssigningGoals (only for generating new goals)
     */
    public void assignGoalsToPlayers() {
        switch (wayOfAssigningGoals) {
            case "randDif":
                assignDifferentRandomGoalsToPlayers();
                break;
            case "randSame":
                assignSameRandomGoalsToPlayers();
                break;
        }
    }

    /**
     * Assigns the players just as in the saved grid and notifies listeners about it
     * @param savedGoals, goals saved from the previous games
     */
    private void reassignSavedGoals(ArrayList<Patch> savedGoals){
        int goalToTake = 0;
        for(ColoredTrailsPlayer player : players) {
            setGoal(player, savedGoals.get(goalToTake));
            notifyListeners(new PropertyChangeEvent(player, "assignedGoalsIndex",
                    player, patches.indexOf(savedGoals.get(goalToTake))));
            goalToTake++;
        }
    }

    /**
     * sets up the game by distributing the tokens to players and assigning goals to players
     */
    public void setUp(int mapNumber) {
        Grid savedGrid =  null;
        ArrayList<Patch> savedGoals = null;
        //read grid from file
        if (mapNumber != 0) {
            String fileName = "grid_" + mapNumber + ".ser";
            try {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                savedGrid = (Grid) in.readObject();
                savedGoals = savedGrid.goalsToSave;
                in.close();
                fileIn.close();
                System.out.println("Serialized data is loaded from " + fileName);
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("FILE NOT FOUND FATAL ERROR");
                throw new RuntimeException(e);
            }
        }

        generatePatchesFiveByFive(savedGrid);
        try {
            distributeTokensToPlayers(savedGrid);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //way of going around the hashing + file errors
        if (savedGoals != null) {
            reassignSavedGoals(savedGoals);
        }  else  {
            //DO NOT ADD ARGUMENTS TO THIS FUNCTION
            assignGoalsToPlayers();
        }
    }


    /**
     * The method updates the offer associated to the player, according to the parameter offer.
     * @param player: The player which makes the offer
     * @param offer: The offer of the player
     */
    private void setOffer(ColoredTrailsPlayer player, ArrayList<ArrayList<Token>> offer) {
        offers.put(player, offer);
    }

    /**
     * The method assumes that the player can only reveal its goal once. This needs to be discussed.
     * Associates the goalToAnnounce to the player as the goal the player wants to communicate to its partner
     * @param player The player communicating the supposed goal
     * @param goalToAnnounce The goal the player wants the partner
     */
    public void setGoalToAnnounce(ColoredTrailsPlayer player, Patch goalToAnnounce) {
        if (goalsToAnnounce.get(player) == null) {
            this.goalsToAnnounce.put(player, goalToAnnounce);
        }
    }

    /**
     * Starts the negotiations. Ends when both players sent the same offer or the number of turns has reached the
     * maximumNumberOfTurns, initially set to 40
     *
     * @return true if the negotiations ended because of agreement, false if it reached the maximumNumberOfTurns
     */
    public int[] start(boolean saveMap) throws IllegalAccessException {
        int agreementReached = 0;
        setGameState(STATE.ACTIVE);
        while (gameState != STATE.INACTIVE && numberOfTurns < maximumNumberOfTurns) {
            ColoredTrailsPlayer currentPlayer = getPlayer(numberOfTurns);
            ColoredTrailsPlayer partner = getPlayer(numberOfTurns + 1);
            if(gameState != STATE.INACTIVE)
            {
                setGameState(STATE.WAITING_FOR_GOAL);
            }
            notifyListeners(new PropertyChangeEvent(this, "newTurn", null,
                    currentPlayer));
            setGameState(STATE.WAITING_FOR_GOAL);
            notifyListeners(new PropertyChangeEvent(currentPlayer, "initiatingAnnounceGoal", null,
                    null));
            Patch goalToReveal = currentPlayer.revealGoal();        // Ask the player to reveal its goal
            goalsToAnnounce.put(currentPlayer, goalToReveal);       // update the goalsToAnnounce
            if(goalToReveal != null) {
                partner.listenToGoal(goalToReveal);
                notifyListeners(new PropertyChangeEvent(currentPlayer, "announceGoalFinished", null,
                        goalToReveal));
            }
            if(isOfferLegal(offers.get(partner))) {     // if the partner made a legal offer, announce it
                notifyListeners(new PropertyChangeEvent(partner, "receiveOfferFromPartner", currentPlayer,
                        offers.get(partner).clone()));// Use the oldValue to pass the current player
                currentPlayer.receiveOffer(offers.get(partner));
            }
            if(gameState != STATE.INACTIVE){
                setGameState(STATE.WAITING_FOR_OFFER);
            }
            notifyListeners(new PropertyChangeEvent(currentPlayer, "initiatingOffer", null, null));
            ArrayList<ArrayList<Token>> offer = currentPlayer.makeOffer();      // Ask the player to make an offer
            setOffer(currentPlayer, offer);
            notifyListeners(new PropertyChangeEvent(currentPlayer, "offerFinished", null, null));
            if(!isOfferLegal(offers.get(currentPlayer))) {     // Ignore any illegal offer
                setOffer(currentPlayer, null);
            } else {
                if(offers.get(partner) != null) {
                    notifyListeners(new PropertyChangeEvent(currentPlayer, "offer", null,
                            offers.get(currentPlayer).clone()));
                    if (acceptedOffer(offers.get(currentPlayer), offers.get(partner))) {
                        setGameState(STATE.INACTIVE);
                        notifyListeners(new PropertyChangeEvent(this, "gameOver", null,
                                numberOfTurns < maximumNumberOfTurns));
                        tokens.put(currentPlayer, offers.get(currentPlayer).get(0));
                        tokens.put(partner, offers.get(partner).get(0));
                        agreementReached = 1;
                    }
                }
            }
            numberOfTurns++;
        }


        //return values
        int[] toReturn = new int[3];
        toReturn[0] = agreementReached;
        int iterator = 0;
        for (ColoredTrailsPlayer player: players) {
            iterator += 1;
            System.out.println("player: " + player.getName());
            int[] score = calculateFinalScore(player);
            System.out.println("points obtained: " + score[0]);
            System.out.println("position reached: " + score[1]);
            notifyListeners(new PropertyChangeEvent(player, "finalPatch", iterator, score[1]));
            toReturn[iterator] = score[0];
        }

        //POTENTIALLY ADD MORE CONDITIONS TO SAVE MAPS
        if(abs(toReturn[1]-toReturn[2]) > 80){
            System.out.println("Saving map: point difference larger than 80");
            saveMap = true;
        }

        //save maps
        if(saveMap) {
            this.saveGoalPositions();
            MapSaver mapSaver = new MapSaver(this);
            mapSaver.saveMap();
        }

        return toReturn;
    }


    /**
     * @param player to be checked
     * @return true if it is player's turn
     */
    public boolean isPlayerActive(ColoredTrailsPlayer player) {
        return getCurrentPlayer() == player;
    }

    /**
     * Calculating the bonus score from unspent tokens
     * @param tokens, the current token list available to a player
     * @return score, the bonus score
     */

    private int tokenScore(ArrayList<Token> tokens){
        int score = 0;
        for(Token token : tokens) {
            score += 5;
        }
        return score;
    }

    /**
     * Creating a heuristic array for the A* algorithm based on the manhattan distance to the goal coordinates
     * On each position, multiply the distance by 5 as going closer to 1 step requires to lose 5 points.
     * Then, the distance
     * @param player the player for whom the score is calculated
     * @return heuristicArray, the heuristic array of that player
     */
    private ArrayList<Integer> calculateHeuristicArray(ColoredTrailsPlayer player) {
        ArrayList<Integer> heuristicArray = new ArrayList<>();
        int goalPosition = goals.get(player).getPatchPosition();
        for (int i=0;i<25;i++) {
            int y = i % 5;
            int x = i / 5;
            int goalY = goalPosition % 5;
            int goalX = goalPosition / 5;
            int distance = abs(x-goalX) + abs(y-goalY);
            heuristicArray.add(Math.max(0,50 - 5*distance));

        }
        return heuristicArray;
    }

    /**
     * Checking whether a move is possible
     * @param tokens, the current token list available to a player
     *        destination, the destination of the move
     */

    private boolean isTokenAvailable(ArrayList<Token> tokens, Patch destination) {
        for(Token token : tokens) {
            if(token.getColor() == destination.getColor()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deleting a token from the list of player tokens to move to a patch
     * @param tokens, the current token list available to a player
     *        destination, the destination of the move
     * @return modified token list
     */
    private ArrayList<Token> spendToken(ArrayList<Token> tokens, Patch destination) {
        for(Token token : tokens) {
            if(token.getColor() == destination.getColor()) {
                tokens.remove(token);
                break;
            }
        }
        return tokens;
    }

    /**
     * Calculating utility based on the position change and the tokens left to spend
     * @param position, the position the player wants to move to
     * @param tokens, the current token list available to a player
     * @param heuristicArray, the heuristic array of that player
     * @return tileScore, the combined token and
     * */
    private int getTileUtility(int position, ArrayList<Token> tokens, ArrayList<Integer> heuristicArray) {
        int tileScore = tokenScore(tokens) -5; //-5 as one token has to be spent to move
        tileScore = tileScore + heuristicArray.get(position);
        return tileScore;
    }

    /**
     * Adjusting utility based on the position change, as going closer to the goal should increase the utility
     * while going back should decrease it
     * @param tileUtility, the new utility obtained by moving to a tile
     * @param currentCost, the utility before moving to a tile
     * @return tileScore, the combined token and
     * */
    private int compareUtilities(int tileUtility, int currentCost) {
        if (tileUtility >= currentCost) {
            return tileUtility + 10;
        }
        else {
            return tileUtility - 10;
        }
    }

    private boolean indexInRange(int index){
        return index >= 0 && index < 25;
    }

    /**
     * Adding new positions to the priority queue based on tile utilities calculated from predefined rules
     * @param queue, the current queue
     * @param position, the current position
     * @param visited, the list of the already visited fields
     * @param tokens, the current token list available to a player
     * @param heuristicArray, the heuristic array of that player
     * @return updated queue
     */
    private PriorityQueue<SearchNode> addNeighborsToQueue(PriorityQueue<SearchNode> queue, int position, int[] visited, ArrayList<Token> tokens, ArrayList<Integer> heuristicArray, int currentCost) {
        if (indexInRange(position + 1)) {
            if  (visited[position + 1] == 0) {
                if  (isTokenAvailable(tokens, patches.get(position + 1))) {
                    int tileUtility =  getTileUtility(position + 1, tokens, heuristicArray);
                    tileUtility = compareUtilities(tileUtility, currentCost);
                    ArrayList<Token> tokenCopy = (ArrayList<Token>) tokens.clone();
                    queue.add(new SearchNode(position+1, spendToken(tokenCopy, patches.get(position+1)), tileUtility));
                }
            }
        }
        if (indexInRange(position - 1)) {
            if (visited[position - 1] == 0) {
                if (isTokenAvailable(tokens, patches.get(position - 1))) {
                    int tileUtility =  getTileUtility(position - 1, tokens, heuristicArray);
                    tileUtility = compareUtilities(tileUtility, currentCost);
                    ArrayList<Token> tokenCopy = (ArrayList<Token>) tokens.clone();
                    queue.add(new SearchNode(position - 1, spendToken(tokenCopy, patches.get(position-1)), tileUtility));
                }
            }
        }
        if (indexInRange(position+5)) {
            if (visited[position + 5] == 0) {
                if (isTokenAvailable(tokens, patches.get(position + 5))) {
                    int tileUtility =  getTileUtility(position + 5, tokens, heuristicArray);
                    tileUtility = compareUtilities(tileUtility, currentCost);
                    ArrayList<Token> tokenCopy = (ArrayList<Token>) tokens.clone();
                    queue.add(new SearchNode(position + 5, spendToken(tokenCopy, patches.get(position+5)), tileUtility));
                }
            }
        }
        if (indexInRange(position-5)) {
            if  (visited[position-5] == 0) {
                if (isTokenAvailable(tokens, patches.get(position - 5))) {
                    int tileUtility =  getTileUtility(position - 5, tokens, heuristicArray);
                    tileUtility = compareUtilities(tileUtility, currentCost);
                    ArrayList<Token> tokenCopy = (ArrayList<Token>) tokens.clone();
                    queue.add(new SearchNode(position - 5, spendToken(tokenCopy, patches.get(position-5)), tileUtility));
                }
            }
        }
        return queue;
    }

    /**
     * Handling the A* algorithm.
     * To improve: check for plausability
     * @param player, the player for whom the score is calculated
     *        tokens, the list of the tokens that a player can spend
     *        visited, the list of the visited fields while looking for the solution
     * @return final postion and score of a player, made up of its remaining tokens and its best position
     */
    private int[] astarTraverse(ColoredTrailsPlayer player, ArrayList<Token> tokens, int[] visited) {
        int[] toReturn = new int[2];
        toReturn[0] = -1;
        toReturn[1] = -1;
        int position = 12;

        ArrayList<Integer> heuristicArray = calculateHeuristicArray(player);
        PriorityQueue<SearchNode> queue = new PriorityQueue<>((node1, node2) -> {
            if (node1.utility < node2.utility) return -1;
            if (node1.utility > node2.utility) return 1;
            return 0;
        });

        ArrayList<Token> tokenCopy = (ArrayList<Token>) tokens.clone();

        SearchNode startNode = new SearchNode(position, tokenCopy, 0);
        queue.add(startNode);

        int goalPosition = goals.get(player).getPatchPosition();
        int goalY = goalPosition % 5;
        int goalX = goalPosition / 5;

        while (!queue.isEmpty()) {
            SearchNode currentNode = queue.poll();
            int currentPosition = currentNode.position;
            int currentUtility = currentNode.utility;
            ArrayList<Token> currentTokens =  currentNode.tokens;
            if(currentPosition == goalPosition) {
                System.out.println("Reached goal!");
                toReturn[0] = tokenScore(currentTokens) + 100;
                toReturn[1] = goalPosition;
                break;
            }

            visited[currentPosition] = 1;
            queue = addNeighborsToQueue(queue, currentPosition, visited, currentTokens, heuristicArray,currentUtility);

            //calculate the actual utility of the current position
            int playerY = currentPosition % 5;
            int playerX = currentPosition / 5;
            int positionScore = 50 - 10*((abs(playerX-goalX) + abs(playerY-goalY)));
            int tokenScore = tokenScore(currentTokens);
            if (toReturn[0] < positionScore + tokenScore) {
                toReturn[0] = positionScore + tokenScore;
                toReturn[1] = currentPosition;
            }

        }
        return toReturn;
    }

    /**
     * Final score calculation based on A* traversal.
     * @param player, the player for whom the score is calculated
     * @return bestScore, the score of that player
     */
    public int[] calculateFinalScore(ColoredTrailsPlayer player) {
        int[] visited = new int[25];
        for(int i = 0; i<25; i++) {
            visited[i] = 0;
        }
        return astarTraverse(player,tokens.get(player),visited);
    }

    /**
     * @return The index of the starting patch in patches
     */
    public int getStartPatchIndex() {
        return startPatchIndex;
    }

    /**
     * @return a clone of patches
     */
    public ArrayList<Patch> getPatches() {
        return (ArrayList<Patch>) patches.clone();
    }

    /**
     * @return the number of turns (The number of the current turn)
     */
    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    /**
     * sets the wayOfAssigningGoals to the given string
     * @param wayOfAssigningGoals a string between "randDif" or "randSame"
     */
    public void setWayOfAssigningGoals(String wayOfAssigningGoals) {
        this.wayOfAssigningGoals = wayOfAssigningGoals;
    }

    /**
     * Sets maximumNumberOfTurns to the given value
     * @param maximumNumberOfTurns
     */
    public void setMaximumNumberOfTurns(int maximumNumberOfTurns) {
        this.maximumNumberOfTurns = maximumNumberOfTurns;
    }

    /**
     * @param player
     * @return The index on the grid of the player
     */
    public int getGoalIndex(ColoredTrailsPlayer player) {
        return patches.indexOf(goals.get(player));
    }

    /**
     * Saves the goal positions of the players after the game is done
     */
    public void saveGoalPositions()
    {
        if (gameState == STATE.INACTIVE){
            for (ColoredTrailsPlayer player : players) {
                goalsToSave.add(goals.get(player));
            }
        }
        else {
            System.out.println("Cannot save goal positions while game is active");
        }
    }

    /**
     * @return array of player names for view purposes
     */
    public ArrayList<String> getPlayerNames() {
        ArrayList<String> toReturn = new ArrayList<>(2);
        for(ColoredTrailsPlayer player: this.players){
            toReturn.add(player.name);
        }
        return toReturn;
    }

}