package View.model.visuals;

import View.model.menu.ChoosePictureMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoubleImagePanel extends JPanel {
    private Image leftImage;
    private Image rightImage;
    private JLabel leftLabel;
    private JLabel rightLabel;
    public DoubleImagePanel(Image leftImage, Image rightImage) {
        this.setBackground(Color.GRAY);
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        this.setLayout(new FlowLayout());
        setLayout(new GridLayout(1, 2, 2, 4));
        leftLabel = getPlayerImageLabel(leftImage);
        rightLabel = getPlayerImageLabel(rightImage);
        add(leftLabel);
        add(rightLabel);
    }

    public static JLabel getPlayerImageLabel(Image image) {
        Image scaledPlayerImage =  image.getScaledInstance(120
                , 200, Image.SCALE_SMOOTH);
        JLabel playerImageLabel = new JLabel(new ImageIcon(scaledPlayerImage));
        playerImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return playerImageLabel;
    }

}
