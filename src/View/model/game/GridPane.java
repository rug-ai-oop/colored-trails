package View.model.game;

import Controller.GameController;
import Model.Color;
import Model.Grid;
import Model.HumanPlayer;
import Model.Patch;
import View.controller.ViewController;
import View.model.visuals.DoubleImagePanel;
import View.model.visuals.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GridPane extends JPanel implements PropertyChangeListener {
    private static boolean playerRemainsOnStart = false;
    private final Image rightPlayerImage;
    private final Image leftPlayerImage;
    private Grid grid;
    private ArrayList<HumanPlayer> humanPlayers = new ArrayList();
    private DoubleImagePanel doubleImagePanel;
    private GameController gameController;
    private JPanel mainPanel = new JPanel();
    private final ArrayList<JButton> buttons = new ArrayList();
    private ViewController viewController;
    private JOptionPane optionPaneRevealGoal;
    private JDialog dialog;
    private boolean allowToPickPatch = false;

    /**
     * @param playerImage
     * @return a panel with gray background containing the DoubleImagePanel.getPlayerImageLabel(image)
     */
    private JPanel getPlayerImagePanel(Image playerImage) {
        JPanel panel = new JPanel();
        panel.setBackground(java.awt.Color.GRAY);
        panel.add(DoubleImagePanel.getPlayerImageLabel(playerImage));
        return panel;
    }


    /**
     * Constructs the grid, where every Patch is a button
     */
    private void addButtonsToGrid() {
        for(int i = 0; i < grid.getPatches().size(); i++) {
            JPanel leftPlayerImagePanel = getPlayerImagePanel(leftPlayerImage);
            JPanel rightPlayerImagePanel = getPlayerImagePanel(rightPlayerImage);
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
                if(i == grid.getStartPatchIndex()) {
                    panelHoldingButton.add(doubleImagePanel);
                }}


            buttons.add(button);


            mainPanelInGridSlot.add(panelHoldingButton, "button");
            mainPanelInGridSlot.add(menuPanel, "menu");
            mainPanelInGridSlot.add(leftPlayerImagePanel, "leftPlayerImage");
            mainPanelInGridSlot.add(rightPlayerImagePanel, "rightPlayerImage");
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

    private void init() {
        mainPanel.setLayout(new GridLayout(5, 5, 5, 5));
        mainPanel.setBackground(java.awt.Color.DARK_GRAY);
        getNewMenuPanelOnButton();
        optionPaneRevealGoal = constructRevealGoalOptionPane();
        this.setLayout(new BorderLayout());
        this.add(mainPanel);
        this.setPreferredSize(mainPanel.getPreferredSize());
    }


    public GridPane(Grid grid, GameController gameController, ViewController viewController, Image leftPlayerImage
            , Image rightPlayerImage) {
        this.setBackground(java.awt.Color.gray);
        this.grid = grid;
        grid.addListener(this);
        this.gameController = gameController;
        this.viewController = viewController;
        this.rightPlayerImage = rightPlayerImage;
        this.leftPlayerImage = leftPlayerImage;
        doubleImagePanel = new DoubleImagePanel(leftPlayerImage, rightPlayerImage);
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
            case "finalPatch":
                int [][] scores = (int[][]) evt.getNewValue();
                int firstPlayerIndex = scores[0][1];
                int secondPlayerIndex = scores[1][1];
                JPanel firstPatch = (JPanel) mainPanel.getComponent(firstPlayerIndex);
                JPanel secondPatch = (JPanel) mainPanel.getComponent(secondPlayerIndex);
                CardLayout firstCardLayout = (CardLayout) firstPatch.getLayout();
                CardLayout secondCardLayout = (CardLayout) secondPatch.getLayout();
                if (firstPlayerIndex != secondPlayerIndex) {
                    firstCardLayout.show(firstPatch, "leftPlayerImage");
                    secondCardLayout.show(secondPatch, "rightPlayerImage");
                } else {
                    firstPatch.add(doubleImagePanel, "doubleImage");
                    firstCardLayout.show(firstPatch, "doubleImage");
                }
                if (scores[0][1] != grid.getStartPatchIndex() && scores[1][1] != grid.getStartPatchIndex()) {
                    // Only display the "start" on the starting point if there is no player on it
                    JPanel startPatch = ((JPanel) mainPanel.getComponent(grid.getStartPatchIndex()));
                    startPatch.removeAll();
                    JLabel startLabel = new JLabel("Start", JLabel.HORIZONTAL);
                    startPatch.add(startLabel);
                }
                revalidate();
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

