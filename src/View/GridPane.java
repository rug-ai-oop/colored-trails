package View;

import Controller.GameController;
import Model.Color;
import Model.Grid;
import Model.HumanPlayer;
import Model.Patch;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GridPane extends JPanel implements PropertyChangeListener, AllowedToListen {
    private Grid grid;
    private ArrayList<HumanPlayer> humanPlayers = new ArrayList();
    private GameController controller;
    private ArrayList<JButton> buttons = new ArrayList();
    public void init() {
        this.setLayout(new GridLayout(5, 5, 5, 5));
    }

    /**
     * Constructs the grid, where every Patch is a button
     */
    public void addButtonsToGrid() {
        for(int i = 0; i < grid.getPatches().size(); i++) {
            Patch patch = grid.getPatches().get(i);
            JButton button = new JButton();
            if(patch.getState() == Patch.State.ACTIVE) {
                button.setBackground(Color.getColor(patch.getColor()));
                button.setActionCommand("selectPatch");
                button.addActionListener(controller);
            } else {
                button.setBackground(java.awt.Color.BLACK);
                button.setEnabled(false);
                if(i == grid.getStartPatchIndex()) {
                    button.setText("START");
                    button.setForeground(java.awt.Color.WHITE);
                }
            }
            buttons.add(button);
            add(button);
        }
        repaint();
        revalidate();
    }

    public GridPane(Grid grid, GameController controller) {
        this.grid = grid;
        grid.addListener(this);
        this.controller = controller;
        init();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Grid game = new Grid();
        game.addPlayer(new HumanPlayer());
        game.addPlayer(new HumanPlayer());
        game.setUp();
        GridPane gridPane = new GridPane(game, new GameController(game));
        frame.add(gridPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "assignedGoalsIndex":
                if(evt.getOldValue() instanceof HumanPlayer) {
                    JButton button = buttons.get((Integer) evt.getNewValue());
                    Image scaledTokenImage =  OfferPane.auxiliaryImages.get("redFlag").getScaledInstance(80
                            , 80, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledTokenImage));
                    humanPlayers.add((HumanPlayer) evt.getOldValue());
                }
                break;
            case "createdPatches":
                addButtonsToGrid();
                break;

        }
    }
}

