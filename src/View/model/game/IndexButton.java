package View.model.game;

import javax.swing.*;

public class IndexButton extends JButton {
    private final int index;

    public IndexButton(int index) {
        super();
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
