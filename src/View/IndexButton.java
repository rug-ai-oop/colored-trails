package View;

import javax.swing.*;

public class IndexButton extends JButton {
    private int columnOnGrid;
    private int rowOnGrid;

    public void setColumnOnGrid(int columnOnGrid) {
        this.columnOnGrid = columnOnGrid;
    }

    public void setRowOnGrid(int rowOnGrid) {
        this.rowOnGrid = rowOnGrid;
    }

    public int getColumnOnGrid() {
        return columnOnGrid;
    }

    public int getRowOnGrid() {
        return rowOnGrid;
    }
}
