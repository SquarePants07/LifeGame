package ru.apolyakov.GameEntities;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anatoliy.Polyakov on 23.02.2019.
 */
public class Cell {

    Color color;
    boolean alive;

    public Cell(){
        this.color = Color.WHITE;
        alive = false;
    }

    public Cell(Color color){
        this.color = color;
        this.alive = true;
    }

    public Cell(ArrayList<Cell> cells){
        int red = 0;
        int green = 0;
        int blue = 0;
        for (Cell cell : cells) {
            red += cell.getColor().getRed();
            green += cell.getColor().getGreen();
            blue += cell.getColor().getBlue();
        }
        red /= cells.size();
        green /= cells.size();
        blue /= cells.size();
        this.color = new Color(red, green, blue);
        this.alive = true;
    }

    public Color getColor(){
        return color;
    }

    public  boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        if(!alive)
        this.color = Color.WHITE;
    }
}
