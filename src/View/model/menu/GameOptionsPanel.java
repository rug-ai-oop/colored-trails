package View.model.menu;

import javax.swing.*;
import java.awt.*;

public class GameOptionsPanel extends JPanel {
    private String selectedOption; // Store the selected game option
    private JButton humanVsHumanButton;
    private JButton humanVsAgentButton;
    private JButton agentVsAgentButton;
    private JButton humanVsHumanLikeAgent;
    private JLabel gameOptionsLabel;
    private MainPanel mainPanel; // Reference to the main panel

    public GameOptionsPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        // Setting layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        // Initializing components
        gameOptionsLabel = new JLabel("Game Options");
        gameOptionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        humanVsHumanButton = new JButton("Human vs. Human");
        humanVsHumanButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        humanVsAgentButton = new JButton("Human vs. Agent");
        humanVsAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        agentVsAgentButton = new JButton("Agent vs. Agent");
        agentVsAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        humanVsHumanLikeAgent = new JButton("Human vs. HumanAgent");
        humanVsHumanLikeAgent.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding action listeners to the buttons
        humanVsHumanButton.addActionListener(e -> {
            printGameOption("Human vs. Human");
            selectedOption = "Human vs. Human";
            mainPanel.setGameOption(selectedOption);
            ((MainPanel) getParent()).showCard("DummyOptions");
        });

        humanVsAgentButton.addActionListener(e -> {
            printGameOption("Human vs. Agent");
            selectedOption = "Human vs. Agent";
            mainPanel.setGameOption(selectedOption);
            ((MainPanel) getParent()).showCard("TomSelection");
        });

        agentVsAgentButton.addActionListener(e -> {
            printGameOption("Agent vs. Agent");
            selectedOption = "Agent vs. Agent";
            mainPanel.setGameOption(selectedOption);
            ((MainPanel) getParent()).showCard("TomSelection");
        });

        humanVsHumanLikeAgent.addActionListener(e -> {
            printGameOption("Human vs. Human like Agent");
            selectedOption = "Human vs. Human like Agent";
            mainPanel.setGameOption(selectedOption);
            ((MainPanel) getParent()).showCard("TomSelection");
        });

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
