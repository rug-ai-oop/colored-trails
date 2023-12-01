package View;

import Controller.GameController;
import Model.Color;
import Model.HumanPlayer;
import Model.Patch;

import javax.swing.*;
import java.awt.*;

public class InitOffer extends JPanel{

    private GameController controller;

    private BorderLayout border;

    public void init() {
        border = new BorderLayout();
        this.setLayout(border);
        addButtons();
    }

    public InitOffer(GameController controller) {
        this.controller = controller;
        init();
    }
    public void addButtons() {
        JButton button1 = new JButton("send offer");
        button1.addActionListener(controller);
        button1.setActionCommand("initiateOffer");
        JButton button2 = new JButton("cancel");
        button2.addActionListener(controller);
        button2.setActionCommand("cancelOffer");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(button1);
        buttonPanel.add(button2);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Make an offer");
        InitOffer initOffer = new InitOffer(new GameController(new HumanPlayer(new Patch(Color.COLOR1, 1, 1))));
        frame.add(initOffer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}
