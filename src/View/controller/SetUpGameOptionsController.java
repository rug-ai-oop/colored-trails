package View.controller;

import View.ViewTester;
import View.model.menu.ChoosePictureMenu;
import View.model.menu.MainPanel;
import View.model.visuals.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetUpGameOptionsController implements ActionListener {
    private MainPanel mainPanel;
    private String selectedOption; // Store the selected game option
    private int selectedTom; // Store the selected tom level

    public SetUpGameOptionsController(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = mainPanel.getFrame();
        switch (e.getActionCommand()) {
            case "Human vs. Human" -> {
                selectedOption = e.getActionCommand();
                ViewTester.setSelectedOption(selectedOption);
                mainPanel.showCard("DummyOptions");
            }
            case "Human vs. Agent", "Agent vs. Agent", "Human vs. Human like agent" -> {
                selectedOption = e.getActionCommand();
                ViewTester.setSelectedOption(selectedOption);
                mainPanel.showCard("TomSelection");
            }
            case "dummy1", "dummy2", "dummy3" -> {
                ViewTester.displayFullScreenFrame(frame);
                frame.setLocation(0,0);
                frame.revalidate();
                mainPanel.showCard("ChoosePicture");
            }
            case "BackFromDummy" -> {
                String previousPanel = selectedOption.equals("Human vs. Human") ? "GameOptions" : "TomSelection";
                mainPanel.showCard(previousPanel);
            }
            case "BackFromTom" -> mainPanel.showCard("GameOptions");
            case "0", "1", "2" -> {
                selectedTom = Integer.parseInt(e.getActionCommand());
                ViewTester.setSelectedTom(selectedTom);
                mainPanel.showCard("DummyOptions");
            }
        }
        // Action command of the player images buttons
        if(e.getActionCommand().startsWith("PLAYER_")) {
            ViewTester.setPlayerImage(ImageLoader.playerImages.get(e.getActionCommand()));
            if(selectedOption.equals("Human vs. Human") ) {
                if(!ViewTester.secondPlayerChoseImage) {
                    mainPanel.getChoosePictureMenu().setLabelText("Choose the icon for the second player:");
                } else {
                    frame.dispose();
                    ViewTester.setReadyToStartGame(true);
                }
            } else {
                frame.dispose();
                ViewTester.setReadyToStartGame(true);
            }
        }
    }
}
