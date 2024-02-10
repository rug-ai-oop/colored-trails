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

    public static void main(String[] args) {
        JFrame frame = new JFrame("ToM Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TomSelectionPanel());
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }
}
