package View.model;

import javax.swing.*;
import java.awt.*;

public class TomSelectionPanel extends JPanel {
    private JButton levelZeroButton;
    private JButton levelOneButton;
    private JButton levelTwoButton;
    private JButton backButton;
    private JLabel tomLabel;

    private MainPanel mainPanel; // Reference to the main panel

    public TomSelectionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 300));

        tomLabel = new JLabel("ToM Level of agent");
        tomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        levelZeroButton = new JButton("0");
        levelZeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        levelOneButton = new JButton("1");
        levelOneButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        levelTwoButton = new JButton("2");
        levelTwoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new JButton("Back");

        // Add action listeners to the buttons
        levelZeroButton.addActionListener(e -> {
            printButtonNumber("0");
            ((MainPanel) getParent()).showCard("DummyOptions");
        });

        levelOneButton.addActionListener(e -> {
            printButtonNumber("1");
            ((MainPanel) getParent()).showCard("DummyOptions");
        });

        levelTwoButton.addActionListener(e -> {
            printButtonNumber("2");
            ((MainPanel) getParent()).showCard("DummyOptions");
        });

        backButton.addActionListener(e -> {
            ((MainPanel) getParent()).showCard("GameOptions");
        });

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set the bounds of the backButton after the panel has been displayed
        backButton.setBounds(10, getHeight() - backButton.getHeight() - 10, 100, 30);
    }

    private void printButtonNumber(String number) {
        System.out.println("Agent ToM level is set to" + number);
    }

}
