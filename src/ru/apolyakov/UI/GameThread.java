package ru.apolyakov.UI;

import ru.apolyakov.GameEntities.CellField;

/**
 * Created by Anatoliy.Polyakov on 23.02.2019.
 */
public class GameThread extends Thread {
    private FieldPanel fieldPanel;
    private CellField cellField;
    private boolean pause;

    public GameThread(FieldPanel fieldPanel){
        this.fieldPanel = fieldPanel;
    }

    public void run() {
        fieldPanel.setCells(cellField);
        while(true){
                fieldPanel.shiftSize();
                fieldPanel.repaint();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            if(!pause)
                cellField.nextGeneration();
        }
    }


    public void pause() {
        if(!pause)
        this.pause = true;
        else
            this.pause = false;
    }

    public  void setCellField(CellField cellField){
        this.cellField = cellField;
    }
}
