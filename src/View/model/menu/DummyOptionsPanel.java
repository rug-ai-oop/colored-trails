package View.model.menu;

import Controller.GameController;
import View.controller.SetUpGameOptionsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DummyOptionsPanel extends JPanel {
    private ActionListener controller;
    private JButton humanVsHumanButton;
    private JButton humanVsAgentButton;
    private JButton backButton;
    private JLabel subGameOptionsLabel;
    public DummyOptionsPanel(ActionListener controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        subGameOptionsLabel = new JLabel("Game Options");
        subGameOptionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        humanVsHumanButton = new JButton("Human vs. Human");
        humanVsHumanButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsHumanButton.setActionCommand("dummy1");
        humanVsHumanButton.addActionListener(controller);

        humanVsAgentButton = new JButton("Human vs. Agent");
        humanVsAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsAgentButton.setActionCommand("dummy2");
        humanVsAgentButton.addActionListener(controller);

        backButton = new JButton("Back");
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
        add(backButton);
        add(Box.createVerticalGlue());

    }

}
