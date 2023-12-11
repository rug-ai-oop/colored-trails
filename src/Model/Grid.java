package Model;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.*;

public class Grid implements PropertyChangeListener {
    private ArrayList<Patch> patches;
    private ArrayList<ColoredTrailsPlayer> players;
    private HashMap<ColoredTrailsPlayer, ArrayList<Token>> tokens;
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
    private ColoredTrailsPlayer getCurrentPlayer(int numberOfTurns) {
        return players.get(numberOfTurns % players.size());
    }

    /**
     * defines the allowed indices in the five by five grid
     * @return  a patch with an allowed index
     */
    private Patch pickRandomGoalFiveByFive() {
        Random random = new Random();
        ArrayList<Integer> allowedIndices = new ArrayList(Arrays.asList(0, 2, 3, 4, 5, 9, 15, 19, 20, 21, 23, 24));
        Integer randomIndex = (Integer) random.nextInt(25);
        while( ! allowedIndices.contains( randomIndex ) ) {
            randomIndex = (Integer) random.nextInt(25);
        }
        return patches.get(randomIndex);
    }

    /**
     * if the players and patches have been created, assigns tokens randomly to each player
     * currently, it distributes as many tokens as patches
     * should be changed to distribute only 4
     */
    private void distributeTokensToPlayers() {
        if(players != null && patches != null) {
            Random random = new Random();
            ArrayList<Patch> patchesCopy = (ArrayList<Patch>) patches.clone();
            Collections.shuffle(patchesCopy);
            for(Patch patchToBeReceived : patchesCopy) {
                ColoredTrailsPlayer playerToReceiveToken = players.get(random.nextInt(players.size()));  //pick a random player
                Token tokenToBeReceived = new Token(patchToBeReceived.getColor());
                tokens.get(playerToReceiveToken).add(tokenToBeReceived);
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
        }
    }

    /**
     * assigns DIFFERENT goals to each player
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

    /* Public Methods */


    /**
     * sets the state of the grid to inactive and, by default,
     * the distributionOfPatches is set to random and different
     */
    public Grid() {
        this.gameState = STATE.INACTIVE;
        this.wayOfAssigningGoals = "randDif";
    }


    /**
     * adds a player to the list of players
     */
    public void addPlayer(ColoredTrailsPlayer player) {
        players.add(player);
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
        distributeTokensToPlayers();
        assignGoalsToPlayers();
    }



    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    /**
     * @return patches
     */
    public ArrayList<Patch> getPatches() {
        return patches;
    }

    /**
     * sets the wayOfAssigningGoals to the given string
     * @param wayOfAssigningGoals a string between "randDif" or "randSame"
     */
    public void setWayOfAssigningGoals(String wayOfAssigningGoals) {
        this.wayOfAssigningGoals = wayOfAssigningGoals;
    }

    public static void main(String[] args) {
        Grid grid = new Grid();
        grid.generatePatchesFiveByFive();
        for(Patch patch : grid.getPatches()) {
            System.out.println(patch.getColor());
        }
    }
}