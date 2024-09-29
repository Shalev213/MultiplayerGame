package org.example;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LevelTwo extends AbstractLevel implements KeyListener {
    private final JButton riddleButton;
    private final JButton fakeRiddleButton;
    private ImageIcon spaceshipBackground;
    private Astronaut astronaut;
    private int xOfBackground = 0;
    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private int fakeButtonWidth = 200;
    private int xOfAstronaut;
    private RiddlePanel riddlePanel;
    private int xOfRiddlePanel;
    private int yOfRiddlePanel;



    public LevelTwo(int width, int height) {
        // קריאה לבנאי של המחלקה האבסטרקטית אחרי אתחול הרקעים
        super.windowWidth = width;
        super.windowHeight = height;


        this.astronaut = new Astronaut();
        this.astronaut.setY(((windowHeight - this.astronaut.getHeight()) / 2) + 60);
        this.xOfAstronaut = (int) ((windowWidth - this.astronaut.getWidth()) / 2);
        this.astronaut.setX(xOfAstronaut);
        // אתחול הרקעים קודם לקריאה לבנאי של המחלקה האבסטרקטית
        this.spaceBackgroundOne = null;
        this.spaceBackgroundTwo = null;
        this.spaceshipBackground = new ImageIcon("src/main/java/resources/levelTwoBackground.png");
        this.xOfBackground = -(this.spaceshipBackground.getIconWidth() - this.windowWidth) / 2;

        this.riddlePanel = new RiddlePanel();

        this.xOfRiddlePanel = (this.windowWidth - this.riddlePanel.getWidth()) / 2;
        this.yOfRiddlePanel = (this.windowHeight - this.riddlePanel.getHeight()) / 2;
        this.riddlePanel.setBounds(this.xOfRiddlePanel, this.yOfRiddlePanel, this.riddlePanel.getWidth(), this.riddlePanel.getHeight());
        this.add(riddlePanel);

        this.riddleButton = new JButton("?");
        this.riddleButton.setFont(new Font("Arial", Font.BOLD, 25));
        this.riddleButton.setContentAreaFilled(false);
        this.riddleButton.setBorderPainted(false);
        this.riddleButton.setFocusPainted(false);
        this.riddleButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.riddleButton.setFocusable(false);
        this.riddleButton.setForeground(new Color(142, 109, 4, 224));

        this.riddleButton.addActionListener(e -> {
            System.out.println("you clicked on: showRiddleButton");
//            this.riddlePanel = new RiddlePanel();
//
//            this.xOfRiddlePanel = (this.windowWidth - this.riddlePanel.getWidth()) / 2;
//            this.yOfRiddlePanel = (this.windowHeight - this.riddlePanel.getHeight()) / 2;
////            this.riddlePanel.setX((this.windowWidth - this.riddlePanel.getWidth()) / 2);
////            this.riddlePanel.setY((this.windowHeight - this.riddlePanel.getHeight()) / 2);
////            this.riddlePanel.setBounds();
//            this.riddlePanel.setBounds(this.xOfRiddlePanel, this.yOfRiddlePanel, this.riddlePanel.getWidth(), this.riddlePanel.getHeight());
//            this.add(riddlePanel);
            this.riddlePanel.setVisible(true);
            this.riddlePanel.setFocusable(true);
            this.riddlePanel.requestFocus();
            this.riddlePanel.requestFocusInWindow();

        });

        this.add(riddleButton);

        this.fakeRiddleButton = new JButton("click here");
        this.fakeRiddleButton.setFont(new Font("Arial", Font.BOLD, 30));
        this.fakeRiddleButton.setContentAreaFilled(false);
        this.fakeRiddleButton.setBorderPainted(false);
        this.fakeRiddleButton.setFocusPainted(false);
        this.fakeRiddleButton.setFocusable(false);
        this.fakeRiddleButton.setForeground(new Color(60, 124, 144, 255));

        this.fakeRiddleButton.addActionListener(e -> {
            System.out.println("hahahaha you clicked on: fakeRiddleButton");
        });

        this.add(fakeRiddleButton);




        this.setFocusable(true);
        this.addKeyListener(this);
        this.requestFocus();
        this.requestFocusInWindow();
        mainGameLoop();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (spaceshipBackground != null) {
            this.spaceshipBackground.paintIcon(null, graphics, xOfBackground, 0);
            this.riddleButton.setBounds(xOfBackground + 1320,140,28,45);
            this.fakeRiddleButton.setBounds(xOfBackground + (spaceshipBackground.getIconWidth() - this.fakeButtonWidth) / 2,260,this.fakeButtonWidth,45);
        }
        astronaut.paintAstronaut(graphics);
//        this.riddlePanel.setX((this.windowWidth - this.riddlePanel.getWidth()) / 2);
//        this.riddlePanel.setY((this.windowHeight - this.riddlePanel.getHeight()) / 2);
    }

    @Override
    public int getBackgroundWidth() {
        return spaceshipBackground.getIconWidth();
    }

    @Override
    public void gameScene() {
        if (rightPressed && (this.xOfBackground + this.getBackgroundWidth()) > this.windowWidth && !(xOfAstronaut > this.astronaut.getX()) ) {
            takeBackgroundLeft();
        } else if (rightPressed && (this.astronaut.getX() + this.astronaut.getWidth()) < this.windowWidth) {
            this.astronaut.leftRightMove(1);
        }
        if (leftPressed && this.xOfBackground < 0 && !(xOfAstronaut < this.astronaut.getX())) {
            takeBackgroundRight();
        }else if (leftPressed && this.astronaut.getX() > 0) {
            this.astronaut.leftRightMove(-1);
        }
        repaint();
    }

    @Override
    public void gameOver() {

    }
    public void takeBackgroundRight() {
        this.xOfBackground += 1;
    }
    public void takeBackgroundLeft() {
        this.xOfBackground -= 1;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT -> {
                this.rightPressed = true;
                this.astronaut.setMirrorChosen(false);
            }
            case KeyEvent.VK_LEFT -> {
                this.leftPressed = true;
                this.astronaut.setMirrorChosen(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT -> this.rightPressed = false;
            case KeyEvent.VK_LEFT -> this.leftPressed = false;
        }
    }
}