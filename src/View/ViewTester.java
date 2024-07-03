package View;


import Controller.GameController;
import Model.ColoredTrailsPlayer;
import Model.ColoredTrailsToMPlayer;
import Model.Grid;
import Model.HumanPlayer;
import View.controller.ViewController;
import View.model.game.GridPane;
import View.model.menu.MainPanel;
import View.model.game.OfferPane;
import View.model.game.PlayerPanel;
import View.model.visuals.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class ViewTester {
    public static boolean firstPlayerChoseImage = false;
    public static boolean secondPlayerChoseImage = false;

    private static String selectedOption; // Store the selected game option
    private static int selectedTom; // Store the selected tom level
    private static Image defaultPlayerImage;
    private static Image firstPlayerImage;
    private static Image secondPlayerImage;
    public static String defaultTomAgentName = "Alex";

    public static volatile boolean readyToStartGame = false;

    public static void displayFullScreenFrame(JFrame frame) {
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }

    public static void startGame(String gameOption, int tomLevel) {
        JFrame frame = new JFrame("Colored Trails");
        frame.setSize(1200, 800);

        Grid game = new Grid();
        ColoredTrailsPlayer firstPlayer;
        ColoredTrailsPlayer secondPlayer;

        if (gameOption.equals("Human vs. Human")) {
            firstPlayer = new HumanPlayer("Csenge");
            secondPlayer = new HumanPlayer("Lukasz");
        } else {
            firstPlayer = new HumanPlayer("Matei");
            secondPlayer = new ColoredTrailsToMPlayer(defaultTomAgentName, tomLevel);
        }

        game.addPlayer(firstPlayer);
        game.addPlayer(secondPlayer);

        GameController gameController = new GameController(game);
        ViewController viewController = new ViewController();

        GridPane gridPane = new GridPane(game, gameController, viewController, firstPlayerImage, secondPlayerImage);
        OfferPane offerPane = new OfferPane(game, gameController, viewController);

        //0 - no map loaded, x - patches_map_x will be loaded
        int loadMap = 0;
        game.setUp(loadMap);

        PlayerPanel playerPanel1 = new PlayerPanel(game, gameController, firstPlayer, viewController, firstPlayerImage);
        PlayerPanel playerPanel2 = new PlayerPanel(game, gameController, secondPlayer, viewController, secondPlayerImage);

        frame.setLayout(new BorderLayout());
        frame.add(offerPane, BorderLayout.SOUTH);
        frame.add(gridPane, BorderLayout.CENTER);
        frame.add(playerPanel1, BorderLayout.WEST);
        frame.add(playerPanel2, BorderLayout.EAST);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displayFullScreenFrame(frame);

        try {
            boolean saveMap = false;
            if (secondPlayer instanceof ColoredTrailsToMPlayer) {
                ((ColoredTrailsToMPlayer) secondPlayer).init();
            }
            int[] finishArray = game.start(saveMap);
            if (finishArray[0] == 1) {
                JOptionPane.showMessageDialog(null, "Agreement reached!\nPlayer 1 score: " + finishArray[1] + "\nPlayer 2 score: " + finishArray[2]);
            } else {
                JOptionPane.showMessageDialog(null, "Agreement not reached!\nPlayer 1 score: " + finishArray[1] + "\nPlayer 2 score: " + finishArray[2]);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ImageLoader.loadImages();
        defaultPlayerImage = ImageLoader.playerImages.get("PLAYER_12");
        firstPlayerImage = defaultPlayerImage;
        secondPlayerImage = defaultPlayerImage;


        JFrame optionFrame = new JFrame("Colored Trails");
        optionFrame.setSize(250, 250);
        optionFrame.setLocationRelativeTo(null);

        MainPanel mainPanel = new MainPanel(optionFrame);

        optionFrame.add(mainPanel);
        optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionFrame.setVisible(true);

        while (!readyToStartGame) {

        }
        startGame(selectedOption, selectedTom);
    }

    public static void setSelectedOption(String selectedOption) {
        ViewTester.selectedOption = selectedOption;
    }

    public static void setSelectedTom(int selectedTom) {
        ViewTester.selectedTom = selectedTom;
    }

    public static void setReadyToStartGame(boolean readyToStartGame) {
        ViewTester.readyToStartGame = readyToStartGame;
    }

    public static void setPlayerImage(Image playerImage) {
        if(!firstPlayerChoseImage) {
            firstPlayerImage = playerImage;
            firstPlayerChoseImage = true;
        } else {
            secondPlayerImage = playerImage;
            secondPlayerChoseImage = true;
        }
    }
}
