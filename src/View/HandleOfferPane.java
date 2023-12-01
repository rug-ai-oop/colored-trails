package View;

import javax.swing.*;
import java.awt.*;

import Controller.GameController;
import Model.Color;
import Model.HumanPlayer;
import Model.Patch;

public class HandleOfferPane extends JPanel{

    private GameController controller;

    private FlowLayout flow;

    public void init() {
        flow = new FlowLayout();
        this.setLayout(flow);
        addButtons();
    }

    public HandleOfferPane(GameController controller) {
        this.controller = controller;
        init();
    }

    public void addButtons() {
        JButton button1 = new JButton("Accept offer");
        button1.addActionListener(controller);
        button1.setActionCommand("acceptOffer");
        JButton button2 = new JButton("Reject offer");
        button2.addActionListener(controller);
        button2.setActionCommand("rejectOffer");

        add(button1);
        add(button2);
    }

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("Handle Offer");
        HandleOfferPane handleOfferPane = new HandleOfferPane(new GameController(new HumanPlayer(new Patch(Color.COLOR1, 1, 1))));
        myFrame.add(handleOfferPane);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(300, 100);
        myFrame.setVisible(true);
    }


}