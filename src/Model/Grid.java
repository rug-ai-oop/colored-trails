package Model;
import View.AllowedToListen;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.lang.Math;

public class Grid {
    private int maximumNumberOfTurns;
    private int numberOfTurns;
    private int startPatchIndex;
    private ArrayList<Patch> patches = new ArrayList();
    private ArrayList<ColoredTrailsPlayer> players = new ArrayList(2);
    private ArrayList<PropertyChangeListener> listeners = new ArrayList();
    private ArrayList<Token> allTokensInPlay = new ArrayList();
    private HashMap<ColoredTrailsPlayer, ArrayList<Token>> tokens = new HashMap();
    private HashMap<ColoredTrailsPlayer, ArrayList<ArrayList<Token>>> offers = new HashMap();
    private HashMap<ColoredTrailsPlayer, Patch> goalsToAnnounce = new HashMap();
    private String wayOfAssigningGoals;

    private enum STATE {
        INACTIVE, ACTIVE, WAITING_FOR_OFFER
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
     * if the players and patches have been created, assigns 4 tokens randomly to each player
     */
    private void distributeTokensToPlayers() {
        if(players != null && patches != null) {
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
                } else {
                    return;
                }

            }
        }
    }

    /**
     * creates 25 patches with the colors from generateRandomColorFiveByFive()
     */
    private void generatePatchesFiveByFive() {
        patches = new ArrayList<>();
        ArrayList<Color> colors = generateRandomColorFiveByFive();
        for(int i = 0; i < 25; i++) {
            patches.add(new Patch(colors.get(i)));
            patches.get(i).setState(Patch.State.ACTIVE);
        }
        patches.get(startPatchIndex).setState(Patch.State.INACTIVE);
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
            player.setGoal(goal);
            assignedPatches.add(goal);
            notifyListeners(new PropertyChangeEvent(player, "assignedGoalsIndex",
                    null, patches.indexOf(goal)));
        }
    }

    /**
     * assigns the same goal to each player
     */
    private void assignSameRandomGoalsToPlayers() {
        for(ColoredTrailsPlayer player : players) {
            player.setGoal(pickRandomGoalFiveByFive());
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
        return allTokensInPlay.equals(allTokensInOffer) || allTokensInPlay.size() != allTokensInOffer.size();
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
        for(int index = 0; index < priorOffer.size(); index++) {
            if( priorOffer.get(index).size() != posteriorOffer.get(index).size()) {
                return false;
            }
            for(int indexInHands = 0; indexInHands < priorOffer.get(index).size(); indexInHands++){
                if(priorOffer.get(index).get(indexInHands).getColor() != posteriorOffer.get(index).get(indexInHands).getColor()) {
                    return false;
                }
            }
        }
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
     * @return The current player in turn
     */
    public ColoredTrailsPlayer getCurrentPlayer() {
        return getPlayer(numberOfTurns);
    }

    /**
     * @param player: The player which the tokens belong to
     * @return A clone of tokens of the player
     */
    public ArrayList<Token> getTokensOfPlayer(ColoredTrailsPlayer player) {
        return (ArrayList) tokens.get(player).clone();
    }

    /**
     * @return A clone of allTokensInPlay
     */
    public ArrayList<Token> getAllTokensInPlay() {
        return (ArrayList) allTokensInPlay.clone();
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
    }

    /**
     * @param listener PropertyChangeListener which is added to the listeners of the grid
     */
    public void addListener(PropertyChangeListener listener) {
        if(listener instanceof AllowedToListen) {
            listeners.add(listener);
        }

    }

    /**
     * Notifies the listeners
     * @param evt PropertyChangeEvent passed in the propertyChange()
     */
    public void notifyListeners(PropertyChangeEvent evt) {
        for(PropertyChangeListener listener : listeners) {
            listener.propertyChange(evt);
        }
    }

    /**
     * assigns the goals according to the wayOfAssigningGoals
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
     * sets up the game by distributing the tokens to players and assigning goals to players
     */
    public void setUp() {
        generatePatchesFiveByFive();
        distributeTokensToPlayers();
        assignGoalsToPlayers();
    }

    /**
     * The method updates the offer associated to the player, according to the parameter offer.
     * @param player: The player which makes the offer
     * @param offer: The offer of the player
     */
    public void setOffer(ColoredTrailsPlayer player, ArrayList<ArrayList<Token>> offer) {
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
     * @return true if the negotiations ended because of agreement, false if it reached the maximumNumberOfTurns
     */
    public boolean start() {
        setGameState(STATE.ACTIVE);
        while (gameState != STATE.INACTIVE && numberOfTurns < maximumNumberOfTurns) {
            ColoredTrailsPlayer currentPlayer = getPlayer(numberOfTurns);
            ColoredTrailsPlayer partner = getPlayer(numberOfTurns + 1);
            currentPlayer.revealGoal();        // Ask the player to reveal its goal
            if(goalsToAnnounce.get(currentPlayer) != null) {
                partner.listenToGoal(goalsToAnnounce.get(currentPlayer));
            }
            setGameState(STATE.WAITING_FOR_OFFER);
            currentPlayer.makeOffer();      // Ask the player to make an offer
            if(!isOfferLegal(offers.get(currentPlayer))) {     // Ignore any illegal offer
                offers.put(currentPlayer, null);
            } else {
                if(acceptedOffer(offers.get(currentPlayer), offers.get(partner))) {
                    setGameState(STATE.INACTIVE);
                }
            }
            numberOfTurns++;
        }
        return numberOfTurns < maximumNumberOfTurns;
    }

    /**
     * Calculating the bonus score from unspent tokens
     * @param tokens, the current token list available to a player
     * @return score, the bonus score
     */

    public int tokenScore(ArrayList<Token> tokens){
        int score = 0;
        for(Token token : tokens) {
            score += 1;
        }
        return score;
    }

    /**
     * Creating a heuristic array for the A* algorithm based on the manhattan distance to the goal coordinates
     * @param player the player for whom the score is calculated
     * @return heuristicArray, the heuristic array of that player
     */
    public ArrayList<Integer> calculateHeuristicArray(ColoredTrailsPlayer player) {
        ArrayList<Integer> heuristicArray = new ArrayList<>();
        int goalPosition = player.getGoal().getPatchPosition();
        for (int i=0;i<25;i++) {
            int y = i % 5;
            int x = i / 5;
            int goalY = goalPosition % 5;
            int goalX = goalPosition / 5;
            int distance = Math.abs(x-goalX) + Math.abs(y-goalY);
            heuristicArray.add(distance);

        }
        return heuristicArray;
    }

    /**
     * Checking whether a move is possible
     * @param tokens, the current token list available to a player
     *        destination, the destination of the move
     */

    public boolean isTokenAvailable(ArrayList<Token> tokens, Patch destination) {
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
    public ArrayList<Token> spendToken(ArrayList<Token> tokens, Patch destination) {
        for(Token token : tokens) {
            if(token.getColor() == destination.getColor()) {
                tokens.remove(token);
                break;
            }
        }
        return tokens;
    }

    public boolean indexInRange(int index){
        return index >= 0 && index < 25;
    }

    /**
     * Adding new positions to the priority queue
     * @param queue, the current queue
     * @param position, the current position
     * @param visited, the list of the already visited fields
     * @param tokens, the current token list available to a player
     * @return updated queue
     */
    public PriorityQueue<SearchNode> addNeighborsToQueue(PriorityQueue<SearchNode> queue, int position, int[] visited, ArrayList<Token> tokens) {
        if (indexInRange(position + 1)) {
            if  (visited[position + 1] == 0) {
                if  (isTokenAvailable(tokens, patches.get(position+1))) {
                    queue.add(new SearchNode(position+1, spendToken(tokens, patches.get(position+1)), 1));
                }
            }
        }
        if (indexInRange(position - 1)) {
            if (visited[position - 1] == 0) {
                if (isTokenAvailable(tokens, patches.get(position - 1))) {
                    queue.add(new SearchNode(position - 1, spendToken(tokens, patches.get(position-1)), 1));
                }
            }
        }
        if (indexInRange(position+5)) {
            if (visited[position + 5] == 0) {
                if (isTokenAvailable(tokens, patches.get(position + 5))) {
                    queue.add(new SearchNode(position + 5, spendToken(tokens, patches.get(position+5)), 1));
                }
            }
        }
        if (indexInRange(position-5)) {
            if  (visited[position-5] == 0) {
                if (isTokenAvailable(tokens, patches.get(position - 5))) {
                    queue.add(new SearchNode(position - 5, spendToken(tokens, patches.get(position-5)), 1));
                }
            }
        }
        return queue;
    }

    /**
     * Handling the A* algorithm.
     * To improve: fix a proper priority mechanism (by cost)
     * @param player, the player for whom the score is calculated
     *        tokens, the list of the tokens that a player can spend
     *        visited, the list of the visited fields while looking for the solution
     * @return finalScore, the score of that player made up of the remaining tokens and its best position
     */
    public int astarTraverse(ColoredTrailsPlayer player, ArrayList<Token> tokens, int[] visited) {
        int position = player.getPlayerPosition();
        int finalScore = -1;

        ArrayList<Integer> heuristicArray = calculateHeuristicArray(player);
        PriorityQueue<SearchNode> queue = new PriorityQueue<>(new Comparator<SearchNode>() {
            public int compare(SearchNode node1, SearchNode node2) {
                if (node1.cost +  heuristicArray.get(node1.position) < node2.cost + heuristicArray.get(node1.position)) return -1;
                if (node1.cost + heuristicArray.get(node1.position) > node2.cost + heuristicArray.get(node1.position)) return 1;
                return 0;
            }
        });
        SearchNode startNode = new SearchNode(position, tokens, 0);
        queue.add(startNode);


        ArrayList<Token> tokenCopy = (ArrayList<Token>) tokens.clone();
        int goalPosition = player.getGoal().getPatchPosition();
        int goalY = goalPosition % 5;
        int goalX = goalPosition / 5;

        while (!queue.isEmpty()) {
            SearchNode currentNode = queue.poll();
            int currentPosition = currentNode.position;
            visited[currentPosition] = 1;
            queue = addNeighborsToQueue(queue, currentPosition, visited, tokenCopy);

            if(currentPosition == goalPosition) {
                finalScore = tokenScore(tokenCopy);
                break;
            }
            if (isTokenAvailable(tokenCopy, patches.get(currentPosition))) {
                visited[currentPosition]=1;
                tokenCopy = spendToken(tokenCopy, patches.get(currentPosition));

                int playerY = currentPosition % 5;
                int playerX = currentPosition/ 5;
                int positionScore = 4 - (Math.abs(playerX-goalX) + Math.abs(playerY-goalY));
                int tokenScore = tokenScore(tokenCopy);

                if (finalScore < positionScore + tokenScore ) finalScore = positionScore + tokenScore;
            }

        }
        return finalScore;
    }

    /**
     * Final score calculation based on A* traversal.
     * @param player, the player for whom the score is calculated
     * @return bestScore, the score of that player
     */
    public int calculateFinalScore(ColoredTrailsPlayer player) {
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
     * The method is intended to be used by players to change the state of the game. The state can only be changed if
     * the game is active (Here, we should think of way to allow it without messing the game, maybe think of a hierarchy
     * if commands, for instance, revealing the goal should be prioritized compared to offering)
     * @param state
     */
    public void setGameStateByPlayer(STATE state) {
        if(gameState == STATE.ACTIVE) {
            gameState = state;
        }
    }

}