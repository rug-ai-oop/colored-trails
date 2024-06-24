package View.model.menu;

import View.model.visuals.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoosePictureMenu extends JPanel {
    public static int imageWidth = 180;
    public static int imageHeight = 300;
    private JLabel chooseIconLabel = new JLabel("Choose your icon:");
    private JPanel gridPanel = new JPanel();
    private ActionListener controller;

    private void setUpChooseIconLabel(JLabel label) {
        label.setFont(new Font("Serif", Font.PLAIN, 21));
        label.setBackground(Color.darkGray);
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(200, 100));
    }

    /**
     * @param actionCommand
     * @param controller
     * @param image
     * @return the button with the given attributes
     */
    private JButton getButton(String actionCommand, ActionListener controller, Image image) {
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.addActionListener(controller);
        Image scaledTokenImage =  image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledTokenImage));
        button.setBackground(Color.darkGray);
        return button;
    }

    /**
     * Sets the GridPanel
     */
    private void setUpGridPanel(JPanel panel) {
        int cols = 4;
        int rows = ImageLoader.numberOfPlayerImages / cols;
        panel.setLayout(new GridLayout(rows, cols));
    }

    /**
     * Adds buttons with player images to panel, assuming the panel has a GridLayout
     * @param panel
     */
    private void addButtonsToGridPanel(JPanel panel) {
        ImageLoader.loadImages();
        for(int i = 0; i < ImageLoader.numberOfPlayerImages; i++) {
            String buttonId = "PLAYER_" + i;
            JButton button = getButton(buttonId, controller, ImageLoader.playerImages.get(buttonId));
            panel.add(button);
        }
    }

    /**
     * Adds the component in Parent
     * @param parent
     * @param label -> North
     * @param gridPanel -> Center
     */
    private void constructChoosePanel(JPanel parent, JLabel label, JPanel gridPanel) {
        parent.setLayout(new BorderLayout());
        parent.add(label, BorderLayout.NORTH);
        parent.add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a ChoosePictureMenu object with a grid containing player images buttons and a label
     * @param controller
     */
    public ChoosePictureMenu(ActionListener controller) {
        this.controller = controller;
        this.setBackground(Color.darkGray);
        setUpGridPanel(gridPanel);
        addButtonsToGridPanel(gridPanel);
        setUpChooseIconLabel(chooseIconLabel);
        constructChoosePanel(this, chooseIconLabel, gridPanel);
    }

    /**
     * Sets the text of the label and updates the view
     * @param text
     */
    public void setLabelText(String text) {
        chooseIconLabel.setText(text);
        revalidate();
    }


}
