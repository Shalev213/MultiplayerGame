package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class LevelOne extends AbstractLevel implements KeyListener {
    private Spaceship spaceship1;
    private Spaceship spaceship2;
    private Fuel fuel;
    private Stone stone1;
    private Stone stone2;
    private Stone stone3;
    private Stone stone4;
    private Stone stone5;
    private Stone stone6;
    private Stone stone7;
    private Stone stone8;
    // משתנים בוליאניים לעקוב אחרי מצב הלחיצה של כל מקש
    private boolean downPressed = false;
    private boolean upPressed = false;
    private boolean rightPressed = false;
    private boolean leftPressed = false;

    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private List<Stone> stones;
    private boolean stoneHasCollision = false;
    private byte stoneIndex;
    private int counterOfStoneHits;
    private boolean fuelHasCollision = false;
    private int counterOfFuelHits;



    public LevelOne(int width, int height, String teamName) {
        // אתחול הרקעים קודם לקריאה לבנאי של המחלקה האבסטרקטית
        this.spaceBackgroundOne = new ImageIcon("src/main/java/sources/LevelOne.png");
        this.spaceBackgroundTwo = new ImageIcon("src/main/java/sources/LevelOneMirror.png");

        // קריאה לבנאי של המחלקה האבסטרקטית אחרי אתחול הרקעים
        super.width = width;
        super.height = height;
        super.xOfBackgroundTwo = this.spaceBackgroundOne.getIconWidth();

        this.spaceship1 = new Spaceship("src/main/java/sources/Spaceship1.png");
        this.spaceship1.setY(200);
        this.spaceship2 = new Spaceship("src/main/java/sources/Spaceship2.png");
        this.spaceship2.setY(500);

        this.fuel = new Fuel();
        this.fuel.setRandomX(this.width, this.width * 2);
        this.fuel.setRandomY(0, this.height - this.fuel.getHeight());
        this.fuel.start();

        this.stone1 = new Stone("src/main/java/sources/stone1.png");
        this.stone1.setRandomX(this.width, this.width * 2);
        this.stone1.setRandomY(0, this.height - this.stone1.getHeight());
        this.stone1.start();

        this.stone2 = new Stone("src/main/java/sources/stone2.png");
        this.stone2.setRandomX(this.width, this.width * 2);
        this.stone2.setRandomY(0, this.height - this.stone2.getHeight());
        this.stone2.start();

        this.stone3 = new Stone("src/main/java/sources/stone3.png");
        this.stone3.setRandomX(this.width, this.width * 2);
        this.stone3.setRandomY(0, this.height - this.stone3.getHeight());
        this.stone3.start();


        this.stone4 = new Stone("src/main/java/sources/stone1.png");
        this.stone4.setRandomX(this.width, this.width * 2);
        this.stone4.setRandomY(0, this.height - this.stone4.getHeight());
        this.stone4.start();

        this.stone5 = new Stone("src/main/java/sources/stone2.png");
        this.stone5.setRandomX(this.width, this.width * 2);
        this.stone5.setRandomY(0, this.height - this.stone5.getHeight());
        this.stone5.start();

        this.stone6 = new Stone("src/main/java/sources/stone3.png");
        this.stone6.setRandomX(this.width, this.width * 2);
        this.stone6.setRandomY(0, this.height - this.stone6.getHeight());
        this.stone6.start();

        this.stone7 = new Stone("src/main/java/sources/stone1.png");
        this.stone7.setRandomX(this.width, this.width * 2);
        this.stone7.setRandomY(0, this.height - this.stone7.getHeight());
        this.stone7.start();

        this.stone8 = new Stone("src/main/java/sources/stone2.png");
        this.stone8.setRandomX(this.width, this.width * 2);
        this.stone8.setRandomY(0, this.height - this.stone8.getHeight());
        this.stone8.start();


        stones = new ArrayList<>();
        stones.add(stone1);
        stones.add(stone2);
        stones.add(stone3);
        stones.add(stone4);
        stones.add(stone5);
        stones.add(stone6);
        stones.add(stone7);
        stones.add(stone8);


        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
        this.requestFocusInWindow();
        mainGameLoop();
    }

    @Override
    public int getBackgroundWidth() {
        return spaceBackgroundOne.getIconWidth();
    }

    //    @Override
    public void gameScene() {
        // חללית 1 - תנועה אנכית ואופקית
        if (downPressed && this.spaceship1.getY() <= (height - 1.5 * spaceship1.getHeight())) {
            spaceship1.upDownMove(1);
        }
        if (upPressed && this.spaceship1.getY() >= 0) {
            spaceship1.upDownMove(-1);
        }
        if (rightPressed && this.spaceship1.getX() <= (this.width - 1.2 * spaceship1.getWidth())) {
            spaceship1.leftRightMove(1);
        }
        if (leftPressed && this.spaceship1.getX() >= 0) {
            spaceship1.leftRightMove(-2);
        }

        // חללית 2 - תנועה אנכית ואופקית
        if (sPressed && this.spaceship2.getY() <= (height - 1.75 * spaceship2.getHeight())) {
            spaceship2.upDownMove(1);
        }
        if (wPressed && this.spaceship2.getY() >= 0) {
            spaceship2.upDownMove(-1);
        }
        if (dPressed && this.spaceship2.getX() <= (this.width - 1.2 * spaceship2.getWidth())) {
            spaceship2.leftRightMove(1);
        }
        if (aPressed && this.spaceship2.getX() >= 0) {
            spaceship2.leftRightMove(-2);
        }


        stonesLoop();   //חזרה של האבנים
        checkCollision();  // בדיקת פגיעה בין האבנים לחלליות
        fuelLoop(); //בדיקת פגיעה בין הדלק לחלליות

        // Handle game logic here
        // You can send messages to the server using gameClient.sendMessage(message);
    }


    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        spaceship1.paintSpaceship(graphics);
        spaceship2.paintSpaceship(graphics);
        fuel.paintFuel(graphics);
        stone1.paintStone(graphics);
        stone2.paintStone(graphics);
        stone3.paintStone(graphics);
        stone4.paintStone(graphics);
        stone5.paintStone(graphics);
        stone6.paintStone(graphics);
        stone7.paintStone(graphics);
        stone8.paintStone(graphics);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // עדכון המשתנים הבוליאניים בהתאם למקש שנלחץ
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_A -> aPressed = true;
            case KeyEvent.VK_D -> dPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // עדכון המשתנים הבוליאניים בהתאם למקש ששוחרר
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_D -> dPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // לא בשימוש
    }


    public void stonesLoop() {
        for (int i = 0; i < stones.size(); i++) {
            if (stones.get(i).getX() < -stones.get(i).getWidth()) {
                stones.get(i).setRandomX(this.width, this.width + 600);
                stones.get(i).setRandomY(0, this.height - this.stones.get(i).getHeight());
            }
        }

    }

    public void fuelLoop() {
        if (fuel.getX() < -fuel.getWidth()){
            this.fuel.setRandomX(this.width, this.width * 2);
            this.fuel.setRandomY(0, this.height - this.fuel.getHeight());
        }
    }




    public void checkCollision() {

        if (this.fuel.rectangle().intersects(this.spaceship1.rectangle()) || this.fuel.rectangle().intersects(this.spaceship2.rectangle())){
            fuelHasCollision = true;
        }

        for (int i = 0; i < stones.size(); i++) {
            if (this.stones.get(i).rectangle().intersects(this.spaceship1.rectangle()) || this.stones.get(i).rectangle().intersects(this.spaceship2.rectangle())) {
                stoneHasCollision = true;
                stoneIndex = (byte) i;
            }
        }
        if (stoneHasCollision){
            stones.get(stoneIndex).setRandomX(this.width, this.width + 600);
            stones.get(stoneIndex).setRandomY(0, this.height - this.stone2.getHeight());
            this.counterOfStoneHits++;
            System.out.println("Stones hits: " + counterOfStoneHits);
            stoneHasCollision = false;

            if (counterOfStoneHits == 3){
                System.out.println("game over");
            }

        }

        if (fuelHasCollision){
            this.fuel.setRandomX(this.width, this.width * 2);
            this.fuel.setRandomY(0, this.height - this.fuel.getHeight());
            counterOfFuelHits++;
            System.out.println("Fuel hits: " + counterOfFuelHits);
            fuelHasCollision = false;
        }

    }

}
