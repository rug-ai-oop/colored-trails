package View;

import javax.swing.*;
import java.awt.*;

public class TomSelectionPanel extends JPanel {
    private JButton levelZeroButton;
    private JButton levelOneButton;
    private JButton levelTwoButton;
    private JLabel tomLabel;

    public TomSelectionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 250));

        tomLabel = new JLabel("ToM Level of agent");
        tomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        levelZeroButton = new JButton("0");
        levelZeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        levelOneButton = new JButton("1");
        levelOneButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        levelTwoButton = new JButton("2");
        levelTwoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners to the buttons
        levelZeroButton.addActionListener(e -> {printButtonNumber("0");});

        levelOneButton.addActionListener(e -> {printButtonNumber("1");});

        levelTwoButton.addActionListener(e -> {printButtonNumber("2");});

        add(Box.createVerticalGlue());
        add(tomLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(levelZeroButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(levelOneButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(levelTwoButton);
        add(Box.createVerticalGlue());

    }

    private void printButtonNumber(String number) {
        System.out.println("Agent ToM level is set to" + number);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ToM Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TomSelectionPanel());
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }
}
