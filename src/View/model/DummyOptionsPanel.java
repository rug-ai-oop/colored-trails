package View.model;

import Controller.GameController;

import javax.swing.*;
import java.awt.*;

public class DummyOptionsPanel extends JPanel {
    private GameController controller;
    private JButton humanVsHumanButton;
    private JButton humanVsAgentButton;
    private JButton agentVsAgentButton;
    private JButton backButton;
    private JLabel subGameOptionsLabel;
    private MainPanel mainPanel; // Reference to the main panel

    public DummyOptionsPanel(MainPanel mainPanel, GameController controller) {
        this.controller = controller;
        this.mainPanel = mainPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        JLabel subGameOptionsLabel = new JLabel("Game Options");
        subGameOptionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton humanVsHumanButton = new JButton("Human vs. Human");
        humanVsHumanButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsHumanButton.setActionCommand("dummy1");
        humanVsHumanButton.addActionListener(controller);

        JButton humanVsAgentButton = new JButton("Human vs. Agent");
        humanVsAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsAgentButton.setActionCommand("dummy2");
        humanVsAgentButton.addActionListener(controller);

        JButton agentVsAgentButton = new JButton("Agent vs. Agent");
        agentVsAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        agentVsAgentButton.setActionCommand("dummy3");
        agentVsAgentButton.addActionListener(controller);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setActionCommand("BackFromDummy");
        backButton.addActionListener(controller);

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

}
