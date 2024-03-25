package View.model;

import Controller.GameController;
import Model.Color;
import Model.Grid;
import Model.HumanPlayer;
import Model.Patch;
import View.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GridPane extends JPanel implements PropertyChangeListener, AllowedToListen {
    private Grid grid;
    private ArrayList<HumanPlayer> humanPlayers = new ArrayList();
    private GameController gameController;
    private JPanel mainPanel = new JPanel();
    private JPanel menuPanelOnButton = new JPanel();
    private JButton yesButton = new JButton("Yes");
    private JButton noButton = new JButton("No");
    private JLabel communicateGoalLabel = new JLabel("Communicate this patch as your goal?");
    private final ArrayList<JButton> buttons = new ArrayList();
    private ArrayList<Boolean> buttonStates = new ArrayList<>();
    private ViewController viewController;

    /**
     * Inner ActionListener which controls the movements of the components in the view that do not involve the model
     */
//    private ActionListener viewModifier = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if(e.getActionCommand() == "selectPatch") {
//                if(grid.getCurrentPlayer() instanceof HumanPlayer) {
//                    int buttonIndex = buttons.indexOf(e.getSource());
//                    if(buttonStates.get(buttonIndex) == false) {
//                        JPanel panelToChange = ((JPanel) mainPanel.getComponent(buttonIndex));
//                        menuPanelOnButton.setBackground(((JButton) e.getSource()).getBackground());
//                        panelToChange.add(menuPanelOnButton, BorderLayout.CENTER);
//                        buttonStates.set(buttonIndex, true);
//                    } else {
//                        ((JPanel) mainPanel.getComponent(buttonIndex)).remove(menuPanelOnButton);
//                        buttonStates.set(buttonIndex, false);
//                    }
//                    revalidate();
//                    repaint();
//                }
//            }
//        }
//    };

    /**
     * Constructs the grid, where every Patch is a button
     */
    private void addButtonsToGrid() {
        for(int i = 0; i < grid.getPatches().size(); i++) {
            Patch patch = grid.getPatches().get(i);
            JPanel panelHoldingButton = new JPanel();
            panelHoldingButton.setLayout(new BorderLayout());
            JButton button = new JButton();
            if(patch.getState() == Patch.State.ACTIVE) {
                button.setBackground(Color.getColor(patch.getColor()));
                button.setActionCommand("selectPatch");
                button.addActionListener(gameController);
                button.addActionListener(viewController);
            } else {
                button.setBackground(java.awt.Color.BLACK);
                button.setEnabled(false);
                if(i == grid.getStartPatchIndex()) {
                    button.setText("START");
                    button.setForeground(java.awt.Color.WHITE);
                }
            }
            panelHoldingButton.add(button);
            buttons.add(button);
            buttonStates.add(false);
            mainPanel.add(panelHoldingButton);
        }
        repaint();
        revalidate();
    }

    private void init() {
        mainPanel.setLayout(new GridLayout(5, 5, 5, 5));

        this.setLayout(new BorderLayout());
        this.add(mainPanel);
        this.setPreferredSize(mainPanel.getPreferredSize());
    }


    public GridPane(Grid grid, GameController gameController, ViewController viewController) {
        this.grid = grid;
        grid.addListener(this);
        this.gameController = gameController;
        this.viewController = viewController;
        viewController.setGridPane(this);
        init();
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

    /**
     * Getters and Setters for ViewController
     */
    public Grid getGrid() {
        return grid;
    }
    public ArrayList<Boolean> getButtonStates() {
        return buttonStates;
    }

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getMenuPanelOnButton() {
        return menuPanelOnButton;
    }


}

