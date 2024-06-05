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

    private SetUpGameOptionsController controller;

    public TomSelectionPanel( SetUpGameOptionsController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        tomLabel = new JLabel("ToM Level of agent");
        tomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        levelZeroButton = new JButton("0");
        levelZeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelZeroButton.setActionCommand("0");
        levelZeroButton.addActionListener(controller);

        levelOneButton = new JButton("1");
        levelOneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelOneButton.setActionCommand("1");
        levelOneButton.addActionListener(controller);

        levelTwoButton = new JButton("2");
        levelTwoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelTwoButton.setActionCommand("2");
        levelTwoButton.addActionListener(controller);

        backButton = new JButton("Back");
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
