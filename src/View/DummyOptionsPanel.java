package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DummyOptionsPanel extends JPanel {
    private JButton humanVsHumanButton;
    private JButton humanVsAgentButton;
    private JButton agentVsAgentButton;
    private JButton backButton;
    private JLabel subGameOptionsLabel;
    private MainPanel mainPanel; // Reference to the main panel

    public DummyOptionsPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        subGameOptionsLabel = new JLabel("Game Options");
        subGameOptionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        humanVsHumanButton = new JButton("Human vs. Human");
        humanVsHumanButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        humanVsAgentButton = new JButton("Human vs. Agent");
        humanVsAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        agentVsAgentButton = new JButton("Agent vs. Agent");
        agentVsAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new JButton("Back");

        // Add action listeners to the buttons
        humanVsHumanButton.addActionListener(e -> {
            printGameOption("Human vs. Human");
            mainPanel.initializeGame();
        });

        humanVsAgentButton.addActionListener(e -> {
            printGameOption("Human vs. Agent");
            mainPanel.initializeGame();
        });

        agentVsAgentButton.addActionListener(e -> {
            printGameOption("Agent vs. Agent");
            mainPanel.initializeGame();
        });

        backButton.addActionListener(e -> {
            String previousPanel;
            switch (mainPanel.getSelectedOption()) {
                case "Human vs. Human":
                    previousPanel = "GameOptions";
                    break;
                default:
                    previousPanel = "TomSelection";
                    break;
            }
            mainPanel.showCard(previousPanel);
        });

        add(Box.createVerticalGlue());
        add(subGameOptionsLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(humanVsHumanButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(humanVsAgentButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(agentVsAgentButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(backButton);
        add(Box.createVerticalGlue());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set the bounds of the backButton after the panel has been displayed
        backButton.setBounds(10, getHeight() - backButton.getHeight() - 10, 100, 30);
    }

    private void printGameOption(String option) {
        System.out.println("Game option selected by participant: " + option);
    }
}
