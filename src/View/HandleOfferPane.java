package View;

import javax.swing.*;
import java.awt.*;

public class HandleOfferPane extends JPanel{

    public HandleOfferPane() {
        setLayout(new FlowLayout());

        JButton button1 = new JButton("Accept offer");
        JButton button2 = new JButton("Reject offer");

        add(button1);
        add(button2);
    }

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("Handle Offer");
        HandleOfferPane handleOfferPane = new HandleOfferPane();
        myFrame.add(handleOfferPane);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(300, 100);
        myFrame.setVisible(true);
    }


}