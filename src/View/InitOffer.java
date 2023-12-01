package View;

import javax.swing.*;
import java.awt.*;

public class InitOffer extends JPanel{

    public InitOffer() {
        setLayout(new BorderLayout());

        JButton button1 = new JButton("send offer");
        JButton button2 = new JButton("cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(button1);
        buttonPanel.add(button2);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Make an offer");
        InitOffer initOffer = new InitOffer();
        frame.add(initOffer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}
