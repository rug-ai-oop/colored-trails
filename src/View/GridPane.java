package View;

import Controller.GameController;
import Model.Color;
import Model.Grid;
import Model.HumanPlayer;
import Model.Patch;

import javax.swing.*;
import java.awt.*;

public class GridPane extends JPanel {
    private GridLayout grid;
    private GameController controller;
    public void init() {
        grid = new GridLayout(5, 5, 10, 10);
        this.setLayout(grid);
        addButtonsToGrid();
    }
    public void addButtonsToGrid() {
        for(int i = 0; i < grid.getRows() ; i++) {
            for(int j = 0; j < grid.getColumns(); j++) {
                IndexButton button = new IndexButton();
                button.setColumnOnGrid(j);
                button.setRowOnGrid(i);
                //generate random integer between 0 and 5
                int random = (int)(Math.random() * 5);
                switch (random) {
                    case 0:
                        button.setBackground(java.awt.Color.CYAN);
                        break;
                    case 1:
                        button.setBackground(java.awt.Color.magenta);
                        break;
                    case 2:
                        button.setBackground(java.awt.Color.RED);
                        break;
                    case 3:
                        button.setBackground(java.awt.Color.orange);
                        break;
                    case 4:
                        button.setBackground(java.awt.Color.BLUE);
                        break;
                }
                button.addActionListener(controller);
                button.setActionCommand("move");
                this.add(button);
            }
        }
    }
    public GridPane(GameController controller) {
        this.controller = controller;
        init();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GridPane gridPane = new GridPane(new GameController(new Grid()));
        frame.add(gridPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}

