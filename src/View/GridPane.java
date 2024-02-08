package View;

import Controller.GameController;
import Model.Color;
import Model.Grid;
import Model.HumanPlayer;
import Model.Patch;

import javax.swing.*;
import java.awt.*;

public class GridPane extends JPanel {
    private Grid grid;
    private GameController controller;
    public void init() {
        this.setLayout(new GridLayout(5, 5, 10, 10));
        addButtonsToGrid();
    }
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
                if(i == 12) {
                    button.setText("START");
                    button.setForeground(java.awt.Color.WHITE);
                }
            }
            add(button);
        }
        repaint();
        revalidate();
    }
    public GridPane(Grid grid, GameController controller) {
        this.grid = grid;
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
}

