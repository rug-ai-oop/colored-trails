package View.model;

import Controller.GameController;
import Model.Color;
import Model.Grid;
import Model.HumanPlayer;
import Model.Patch;
import View.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GridPane extends JPanel implements PropertyChangeListener {
    private Grid grid;
    private ArrayList<HumanPlayer> humanPlayers = new ArrayList();
    private GameController gameController;
    private JPanel mainPanel = new JPanel();
    private final ArrayList<JButton> buttons = new ArrayList();
    private ViewController viewController;
    private JOptionPane optionPaneRevealGoal;
    private JOptionPane optionPaneAcceptOffer;
    private JDialog dialog;
    private boolean allowToPickPatch = false;


    /**
     * Constructs the grid, where every Patch is a button
     */
    private void addButtonsToGrid() {
        for(int i = 0; i < grid.getPatches().size(); i++) {
            Patch patch = grid.getPatches().get(i);
            JPanel mainPanelInGridSlot = new JPanel();      // The panel which takes care of the cardLayout
            mainPanelInGridSlot.setLayout(new CardLayout());
            JPanel panelHoldingButton = new JPanel();
            JPanel menuPanel = getMenuPanelOnButton();
            panelHoldingButton.setLayout(new BorderLayout());
            JButton button = new IndexButton(i);
            if(patch.getState() == Patch.State.ACTIVE) {
                button.setBackground(Color.getColor(patch.getColor()));
                menuPanel.setBackground(Color.getColor(patch.getColor()));
                button.setActionCommand("selectPatch");
                button.addActionListener(gameController);
                button.addActionListener(viewController);
                panelHoldingButton.add(button);
            } else {
//                button.setBackground(java.awt.Color.GRAY);
//                button.setEnabled(false);
                if(i == grid.getStartPatchIndex()) {
                    button.setForeground(java.awt.Color.BLACK);
                    button.setText("START");
                }
            }
            panelHoldingButton.add(button);
            buttons.add(button);
            mainPanelInGridSlot.add(panelHoldingButton, "button");
            mainPanelInGridSlot.add(menuPanel, "menu");
            mainPanel.add(mainPanelInGridSlot);
        }
        repaint();
        revalidate();
    }

    /**
     * Constructs the menuPanelOnButton by adding yesButton, noButton and communicateGoalLabel
     */
    private JPanel getNewMenuPanelOnButton() {
        JPanel menuPanelOnButton = new JPanel();

        JButton yesButton = new JButton("Yes");
        yesButton.setActionCommand("yes");
        JButton noButton = new JButton("No");
        noButton.setActionCommand("no");
        JLabel communicateGoalLabel = new JLabel("Communicate as goal?");

        menuPanelOnButton.setLayout(new BorderLayout());

        //Center the text on the label
        communicateGoalLabel.setHorizontalAlignment(JLabel.CENTER);

        //Add the controllers to the buttons
        noButton.addActionListener(gameController);
        noButton.addActionListener(viewController);
        yesButton.addActionListener(gameController);
        yesButton.addActionListener(viewController);

        //Create a panel holding the buttons
        JPanel panelHoldingButtons = new JPanel();
        panelHoldingButtons.setLayout(new GridLayout(1, 2));
        panelHoldingButtons.add(yesButton);
        panelHoldingButtons.add(noButton);

        //Add the components
        menuPanelOnButton.add(communicateGoalLabel, BorderLayout.NORTH);
        menuPanelOnButton.add(panelHoldingButtons, BorderLayout.CENTER);

        return menuPanelOnButton;
    }

    /**
     * Constructs the pop-up menu which will ask the player whether they want to reveal their goal
     * @return The JOption representing the pop-up
     */
    private JOptionPane constructRevealGoalOptionPane() {

        JButton yesButton = new JButton("Yes");
        yesButton.setActionCommand("yesCommunicate");
        yesButton.addActionListener(viewController);
        yesButton.addActionListener(gameController);

        JButton noButton = new JButton("No");
        noButton.setActionCommand("noCommunicate");
        noButton.addActionListener(viewController);
        noButton.addActionListener(gameController);

        String message = "Would you want to communicate a patch as Goal to your partner?";

        JOptionPane optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION, null, new Object[]{yesButton, noButton},
                noButton);
        return optionPane;
    }

    private JOptionPane constructAcceptOfferOptionPane() {
        return null;
    }

    private void init() {
        mainPanel.setLayout(new GridLayout(5, 5, 5, 5));
        getNewMenuPanelOnButton();
        optionPaneRevealGoal = constructRevealGoalOptionPane();
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
                    HumanPlayer player = (HumanPlayer) evt.getOldValue();
                    JButton button = buttons.get((Integer) evt.getNewValue());
                    Image scaledTokenImage =  ImageLoader.auxiliaryImages.get("redFlag").getScaledInstance(80
                            , 80, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledTokenImage));
                    button.setText(player.getName());
                    humanPlayers.add((HumanPlayer) evt.getOldValue());
                }
                break;
            case "createdPatches":
                addButtonsToGrid();
                break;
            case "initiatingAnnounceGoal":
                if(evt.getSource() instanceof HumanPlayer) {
                    dialog = optionPaneRevealGoal.createDialog( this, "Reveal Goal?");
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    dialog.setVisible(true);
                }
                break;
        }
    }

    /**
     * Getters and Setters for ViewController
     */
    public Grid getGrid() {
        return grid;
    }

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getMenuPanelOnButton() {
        return getNewMenuPanelOnButton();
    }

    public JDialog getDialog() {
        return dialog;
    }

    public JOptionPane getOptionPaneRevealGoal() {
        return optionPaneRevealGoal;
    }

    public boolean isAllowToPickPatch() {
        return allowToPickPatch;
    }

    public void setAllowToPickPatch(boolean allowToPickPatch) {
        this.allowToPickPatch = allowToPickPatch;
    }
}

