package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOptionsPanel extends JPanel {
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
        setPreferredSize(new Dimension(250, 350));

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
            ((MainPanel) getParent()).showCard("DummyOptions");
        });

        humanVsAgentButton.addActionListener(e -> {
            printGameOption("Human vs. Agent");
            ((MainPanel) getParent()).showCard("TomSelection");
        });

        agentVsAgentButton.addActionListener(e -> {
            printGameOption("Agent vs. Agent");
            ((MainPanel) getParent()).showCard("TomSelection");
        });

        humanVsHumanLikeAgent.addActionListener(e -> {
            printGameOption("Human vs. Human like Agent");
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Colored Trails - Game Options");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel mainPanel = new MainPanel(frame); // Create an instance of MainPanel
        frame.getContentPane().add(mainPanel); // Add the main panel to the frame
        GameOptionsPanel gameOptionsPanel = new GameOptionsPanel(mainPanel); // Pass the main panel to the constructor
        mainPanel.add(gameOptionsPanel, "GameOptions"); // Add the GameOptionsPanel to the main panel
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
