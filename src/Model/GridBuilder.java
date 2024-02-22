package Model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GridBuilder {
    // This needs to be changed to how many listeners we think we will have
    private static int numberOfListeners = 42;
    private ArrayList<PropertyChangeListener> listeners;
    private ArrayList<ColoredTrailsPlayer> players;

    public GridBuilder addListener(PropertyChangeListener listener) {
        listeners.add(listener);
        return this;
    }
    public GridBuilder addPlayer(ColoredTrailsPlayer player) {
        players.add(player);
        return this;
    }
    public Grid build() throws IllegalAccessException{
        if(listeners.size() != numberOfListeners) {
            throw new IllegalAccessException("Add all the necessary listeners");
        }
        if(players.size() < 2) {
            throw new IllegalAccessException("The game needs at least 2 players");
        }
        Grid buildTarget = new Grid();
        for(PropertyChangeListener listener : listeners) {
            buildTarget.addListener(listener);
        }
        for(ColoredTrailsPlayer player : players) {
            buildTarget.addPlayer(player);
        }
        return buildTarget;
    }

}
