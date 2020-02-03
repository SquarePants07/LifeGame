package ru.apolyakov.UI;

import ru.apolyakov.GameEntities.Cell;
import ru.apolyakov.GameEntities.CellField;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Anatoliy.Polyakov on 23.02.2019.
 */
public class FieldPanel extends JPanel{
    CellField cellField;
    int cellWidth;
    int cellHeight;

    public FieldPanel(){
        super();
        cellWidth = 8;
        cellHeight = 8;
    }

    public void setCells(CellField cellField){
        this.cellField = cellField;
        cellWidth = this.getWidth()/cellField.getWidth();
        cellHeight = this.getHeight()/cellField.getHeight();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(cellField!=null) {
            for (int i = 0; i < cellField.getWidth(); i++) {
                for (int j = 0; j < cellField.getHeight(); j++) {
                    Cell cell = cellField.getCell(i, j);
                    Color color = cell.getColor();
                    g.setColor(color);
                    //if(j % 2 == 0 && i % 2 == 0)
                    g.fillRect(cellWidth * i, this.getHeight() - cellHeight * j, cellWidth, cellHeight);
                }
            }
        }
    }

    public void shiftSize() {
        this.cellWidth = getWidth()/cellField.getWidth();
        this.cellHeight = getHeight()/cellField.getHeight();
    }
}
