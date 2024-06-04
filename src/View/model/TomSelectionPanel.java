package View.model;

import Controller.GameController;
import View.controller.SetUpGameOptionsController;

import javax.swing.*;
import java.awt.*;

public class TomSelectionPanel extends JPanel {
    private JButton levelZeroButton;
    private JButton levelOneButton;
    private JButton levelTwoButton;
    private JButton backButton;
    private JLabel tomLabel;

    private MainPanel mainPanel; // Reference to the main panel
    private SetUpGameOptionsController controller;

    public TomSelectionPanel(MainPanel mainPanel, SetUpGameOptionsController controller) {
        this.controller = controller;
        this.mainPanel = mainPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        JLabel tomLabel = new JLabel("ToM Level of agent");
        tomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton levelZeroButton = new JButton("0");
        levelZeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelZeroButton.setActionCommand("0");
        levelZeroButton.addActionListener(controller);

        JButton levelOneButton = new JButton("1");
        levelOneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelOneButton.setActionCommand("1");
        levelOneButton.addActionListener(controller);

        JButton levelTwoButton = new JButton("2");
        levelTwoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelTwoButton.setActionCommand("2");
        levelTwoButton.addActionListener(controller);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setActionCommand("BackFromTom");
        backButton.addActionListener(controller);

        add(Box.createVerticalGlue());
        add(tomLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(levelZeroButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(levelOneButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(levelTwoButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(backButton);
        add(Box.createVerticalGlue());

    }

}
