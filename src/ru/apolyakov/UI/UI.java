package ru.apolyakov.UI;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import ru.apolyakov.GameEntities.Cell;
import ru.apolyakov.GameEntities.CellField;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.*;

/**
 * Created by Anatoliy.Polyakov on 23.02.2019.
 */
public class UI extends JFrame {
    public FieldPanel fieldPanel;
    public JPanel buttonPanel;
    public JButton startButton;
    public JButton stopButton;
    public JButton randomButton;
    public GameThread gameThread;
    public Cell lastCellClicked;

    private CellField cellField;
    private JComboBox<Color> colorComboBox;
    private JSpinner randomDensity;
    private JSpinner fieldWidth;
    private JSpinner fieldHeight;
    private JLabel cellCountText;
    private JSpinner cellCount;
    private JLabel cellAloneText;
    private JSpinner cellAlone;
    private JLabel cellCrowdedText;
    private JSpinner cellCrowded;

    public UI(){
        super("Life game");
        FormLayout layout = new FormLayout("2dlu, fill:436dlu, 2dlu", // columns
                "2dlu, fill:400dlu:grow, 2dlu,  fill:100dlu, 2dlu");
        PanelBuilder builder = new PanelBuilder(layout);
        this.setMinimumSize(new Dimension(800,800));
        fieldPanel = new FieldPanel();
        fieldPanel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int i = e.getWheelRotation();
                int currentIndex = colorComboBox.getSelectedIndex();
                int newIndex = currentIndex + i;
                if(newIndex >=0 && newIndex<=6)
                colorComboBox.setSelectedIndex(newIndex);
            }
        });
        fieldPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if(!lastCellClicked.equals(cellField.getCell(x/fieldPanel.cellWidth,(fieldPanel.getHeight() - y)/fieldPanel.cellHeight + 1))){
                    lastCellClicked = cellField.setCell(x/fieldPanel.cellWidth,(fieldPanel.getHeight() - y)/fieldPanel.cellHeight + 1,(Color)colorComboBox.getSelectedItem());
                }
                fieldPanel.repaint();
            }
        });
        fieldPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                lastCellClicked = cellField.setCell(x/fieldPanel.cellWidth,(fieldPanel.getHeight() - y)/fieldPanel.cellHeight + 1,(Color)colorComboBox.getSelectedItem());
                fieldPanel.repaint();
            }
        });
        this.setSize(800,800);
        builder.add(fieldPanel, CC.xy(2,2));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameThread = new GameThread(fieldPanel);
        buttonPanel = new JPanel();

        startButton = new JButton("Старт");
        startButton.addActionListener(e -> gameThread.start());

        stopButton = new JButton("Стоп");
        stopButton.addActionListener(e -> gameThread.pause());

        randomButton = new JButton("Случайное поле");
        randomButton.addActionListener(e -> initField());

        colorComboBox = new JComboBox<>();
        colorComboBox.addItem(Color.BLUE);
        colorComboBox.addItem(Color.ORANGE);
        colorComboBox.addItem(Color.GREEN);
        colorComboBox.addItem(Color.RED);
        colorComboBox.addItem(Color.CYAN);
        colorComboBox.addItem(Color.YELLOW);
        colorComboBox.addItem(Color.PINK);

        randomDensity = new JSpinner();
        randomDensity.setModel(new SpinnerNumberModel(3,1,100,1));

        fieldWidth = new JSpinner();
        fieldWidth.setModel(new SpinnerNumberModel(100, 5, 500, 1));

        fieldHeight = new JSpinner();
        fieldHeight.setModel(new SpinnerNumberModel(100, 5, 500, 1));

        cellCountText = new JLabel();
        cellCountText.setText("Потомство");
        cellCount = new JSpinner();
        cellCount.setModel(new SpinnerNumberModel(3,1,8,1));

        cellAloneText = new JLabel();
        cellAloneText.setText("Одинок");
        cellAlone = new JSpinner();
        cellAlone.setModel(new SpinnerNumberModel(2,1,8,1));

        cellCrowdedText = new JLabel();
        cellCrowdedText.setText("Толпа");
        cellCrowded = new JSpinner();
        cellCrowded.setModel(new SpinnerNumberModel(3,1,8,1));

        buttonPanel.add(fieldWidth);
        buttonPanel.add(fieldHeight);

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        buttonPanel.add(randomButton);
        buttonPanel.add(randomDensity);

        buttonPanel.add(cellCountText);
        buttonPanel.add(cellCount);
        buttonPanel.add(cellAloneText);
        buttonPanel.add(cellAlone);
        buttonPanel.add(cellCrowdedText);
        buttonPanel.add(cellCrowded);

        buttonPanel.add(colorComboBox);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        builder.add(buttonPanel, CC.xy(2,4));
        add(builder.getPanel());

        cellField = new CellField((int)fieldWidth.getValue(), (int)fieldHeight.getValue());
        gameThread.setCellField(cellField);
        fieldPanel.setCells(cellField);
        fieldPanel.repaint();
        this.setVisible(true);
    }

    private void stop(){
        gameThread.interrupt();
    }

    public void initField() {
        cellField = new CellField((int)fieldWidth.getValue(), (int)fieldHeight.getValue(), (int)randomDensity.getValue(), (int)cellCount.getValue(), (int)cellAlone.getValue(), (int)cellCrowded.getValue());
        gameThread.setCellField(cellField);
        fieldPanel.setCells(cellField);
        fieldPanel.repaint();
    }

}
