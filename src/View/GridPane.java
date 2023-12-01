package View;

import Controller.GameController;
import Model.Color;
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
        for(int i = 0; i < grid.getRows() * grid.getColumns(); i++) {
            JButton button = new JButton();
            button.addActionListener(controller);
            button.setActionCommand("move");
            this.add(button);

            //To do: When clicked needs to reveal the options
        }
    }
    public GridPane(GameController controller) {
        this.controller = controller;
        init();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GridPane gridPane = new GridPane(new GameController(new HumanPlayer(new Patch(Color.COLOR1, 1, 1))));
        frame.add(gridPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}

