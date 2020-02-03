package ru.apolyakov.GameEntities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anatoliy.Polyakov on 23.02.2019.
 */
public class CellField {
    private Cell[][] cells;
    private int width;
    private int height;
    private Color[] colors = new Color[7];
    private int cellCount;
    private int cellAlone;
    private int cellCrowded;

    public CellField(int width, int height, int randomDensity, int cellCount, int cellAlone, int cellCrowded){
        this.cells = new Cell[width][height];
        this.width = width;
        this.height = height;
        this.cellCount = cellCount;
        this.cellAlone = cellAlone;
        this.cellCrowded = cellCrowded;
        int k = 0;
        colors[0] = Color.BLUE;
        colors[1] = Color.ORANGE;
        colors[2] = Color.GREEN;
        colors[3] = Color.RED;
        colors[4] = Color.CYAN;
        colors[5] = Color.YELLOW;
        colors[6] = Color.PINK;
        for (int i = 0; i < width; i++) {
            this.cells[i] = new Cell[height];
            for (int j = 0; j < height; j++) {
                Random random = new Random();
                if(random.nextInt()%randomDensity == 0) {
                    this.cells[i][j] = new Cell(colors[Math.abs(random.nextInt()%7)]);
                }
                else
                    this.cells[i][j] = new Cell();
            }
        }

    }

    public CellField(int width, int height) {
        this.cells = new Cell[width][height];
        this.width = width;
        this.height = height;
        int k = 0;
        colors[0] = Color.BLUE;
        colors[1] = Color.ORANGE;
        colors[2] = Color.GREEN;
        colors[3] = Color.RED;
        colors[4] = Color.CYAN;
        colors[5] = Color.YELLOW;
        colors[6] = Color.PINK;
        for (int i = 0; i < width; i++) {
            this.cells[i] = new Cell[height];
            for (int j = 0; j < height; j++) {
                this.cells[i][j] = new Cell();
            }
        }
    }

    public void nextGeneration(){
        Cell[][] newCells = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            newCells[i] = new Cell[height];
            for (int j = 0; j < height; j++) {
                newCells[i][j] = new Cell();
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newCells[i][j] = getNextCellState(cells[i][j], i, j);
            }
        }
        cells = newCells;
    }

    private Cell getNextCellState(Cell cell, int x, int y) {
        int neighbourCount = 0;
        ArrayList<Cell> neighbours = new ArrayList<>();
        if(x-1 >= 0 && y-1 >= 0 && cells[x-1][y-1].isAlive()) {
            neighbourCount++;
            neighbours.add(cells[x-1][y-1]);
        }
        if(x-1 >= 0 && cells[x-1][y].isAlive()) {
            neighbourCount++;
            neighbours.add(cells[x-1][y]);
        }
        if(x-1 >= 0 && y+1 < height && cells[x-1][y+1].isAlive()) {
            neighbourCount++;
            neighbours.add(cells[x-1][y+1]);
        }
        if(y-1 >= 0 && cells[x][y-1].isAlive()) {
            neighbourCount++;
            neighbours.add(cells[x][y-1]);
        }
        if(y+1 < height && cells[x][y+1].isAlive()) {
            neighbourCount++;
            neighbours.add(cells[x][y+1]);
        }
        if(x+1 < width && y-1 >= 0 && cells[x+1][y-1].isAlive()) {
            neighbourCount++;
            neighbours.add(cells[x+1][y-1]);
        }
        if(x+1 < width && cells[x+1][y].isAlive()) {
            neighbourCount++;
            neighbours.add(cells[x+1][y]);
        }
        if(x+1 < width && y+1 < height && cells[x+1][y+1].isAlive()) {
            neighbourCount++;
            neighbours.add(cells[x+1][y+1]);
        }

        if(cell.isAlive()){
            if(neighbourCount > cellCrowded || neighbourCount < cellAlone){
                return new Cell();
            }
            else
                return cells[x][y];
        }
        else{
            if(neighbourCount == cellCount)
                return new Cell(neighbours);
        }
        return new Cell();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell getCell(int i, int j) {
        return cells[i][j];
    }

    public Cell setCell(int i, int j, Color color) {
        if (0 <= i && i < width && 0 <= j && j < width) {
            cells[i][j] = new Cell(color);
            System.out.println(i + " " + j);
            if (color == Color.WHITE) {
                cells[i][j].setAlive(false);
            } else
                cells[i][j].setAlive(true);
        }
        return cells[i][j];
    }
}
