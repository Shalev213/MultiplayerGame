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


    public LevelTwo(int width, int height) {
        // קריאה לבנאי של המחלקה האבסטרקטית אחרי אתחול הרקעים
        super.windowWidth = width;
        super.windowHeight = height;
        // אתחול הרקעים קודם לקריאה לבנאי של המחלקה האבסטרקטית
        this.spaceBackgroundOne = null;
        this.spaceBackgroundTwo = null;
        this.spaceshipBackground = new ImageIcon("src/main/java/resources/levelTwoBackground.png");
        this.xOfBackground = -(this.spaceshipBackground.getIconWidth() - this.windowWidth) / 2;

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
        });

        this.add(riddleButton);

        this.fakeRiddleButton = new JButton("click here");
        this.fakeRiddleButton.setFont(new Font("Arial", Font.BOLD, 30));
        this.fakeRiddleButton.setContentAreaFilled(false);
        this.fakeRiddleButton.setBorderPainted(false);
        this.fakeRiddleButton.setFocusPainted(false);
//        this.fakeRiddleButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.fakeRiddleButton.setFocusable(false);
        this.fakeRiddleButton.setForeground(new Color(60, 124, 144, 255));

        this.fakeRiddleButton.addActionListener(e -> {
            System.out.println("hahahaha you clicked on: fakeRiddleButton");
        });

        this.add(fakeRiddleButton);


        this.astronaut = new Astronaut();
        this.astronaut.setX((int) ((windowWidth - this.astronaut.getWidth()) / 2));
        this.astronaut.setY(((windowHeight - this.astronaut.getHeight()) / 2) + 60);


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
    }

    @Override
    public int getBackgroundWidth() {
        return spaceshipBackground.getIconWidth();
    }

    @Override
    public void gameScene() {
        if (rightPressed && (this.xOfBackground + this.getBackgroundWidth()) > this.windowWidth ) {
            takeBackgroundLeft();
        }
        if (leftPressed && this.xOfBackground < 0) {
            takeBackgroundRight();
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

//    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            System.out.println("right");
////            this.rightPressed = true; // עדכון הדגל
//        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT){
//            System.out.println("left");
////            this.leftPressed = true; // עדכון הדגל
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//
//    }

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