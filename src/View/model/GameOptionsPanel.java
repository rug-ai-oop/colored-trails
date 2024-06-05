package View.model;

import Controller.GameController;
import View.controller.SetUpGameOptionsController;

import javax.swing.*;
import java.awt.*;

public class GameOptionsPanel extends JPanel {
    private SetUpGameOptionsController controller;
    private JButton humanVsHumanButton;
    private JButton humanVsAgentButton;
    private JButton agentVsAgentButton;
    private JButton humanVsHumanLikeAgent;
    private JLabel gameOptionsLabel;
    private MainPanel mainPanel; // Reference to the main panel

    public GameOptionsPanel(SetUpGameOptionsController controller) {
        this.controller = controller;
        // Setting layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        // Initializing components
        gameOptionsLabel = new JLabel("Game Options");
        gameOptionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        humanVsHumanButton = new JButton("Human vs. Human");
        humanVsHumanButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsHumanButton.setActionCommand("Human vs. Human");
        humanVsHumanButton.addActionListener(controller);

        humanVsAgentButton = new JButton("Human vs. Agent");
        humanVsAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsAgentButton.setActionCommand("Human vs. Agent");
        humanVsAgentButton.addActionListener(controller);

        agentVsAgentButton = new JButton("Agent vs. Agent");
        agentVsAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        agentVsAgentButton.setActionCommand("Agent vs. Agent");
        agentVsAgentButton.addActionListener(controller);

        humanVsHumanLikeAgent = new JButton("Human vs. HumanAgent");
        humanVsHumanLikeAgent.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsHumanLikeAgent.setActionCommand("Human vs. Human like agent");
        humanVsHumanLikeAgent.addActionListener(controller);

        // Adding components to panel
        add(Box.createVerticalGlue());
        add(gameOptionsLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(humanVsHumanButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(humanVsAgentButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(agentVsAgentButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(humanVsHumanLikeAgent);
        add(Box.createVerticalGlue());
    }

    private void printGameOption(String option) {
        System.out.println("Selected game option: " + option);
    }

}