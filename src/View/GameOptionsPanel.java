package View;

import javax.swing.*;
import java.awt.*;

public class GameOptionsPanel extends JPanel {
    private JButton humanVsHumanButton;
    private JButton humanVsAgentButton;
    private JButton agentVsAgentButton;
    private JButton humanVsHumanLikeAgent;
    private JLabel gameOptionsLabel;

    public GameOptionsPanel() {
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Colored Trails - Game Options");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GameOptionsPanel());
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }
}
