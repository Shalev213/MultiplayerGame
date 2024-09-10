package org.example;

import db.JDBC;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private SignInPanel signInPanel;
    private SignUpPanel signUpPanel;
    private LevelsPanel levelsPanel;
    private LevelOne levelOne;
    private final int width = 1100;
    private final int height = 750;

    public Window () {
        this.setSize(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        signInPanel = new SignInPanel(width, height);
        this.add(signInPanel);
        signUpPanel = new SignUpPanel(width, height);
        this.add(signUpPanel);
        signUpPanel.setVisible(false);
        levelsPanel = new LevelsPanel(width, height);
        this.add(levelsPanel);
        levelsPanel.setVisible(false);

        levelOne = new LevelOne(width, height);
//        this.add(levelOne);
//        levelOne.setVisible(false);



        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 20));
        UIManager.put("Button.background", Color.darkGray);
        UIManager.put("Button.foreground", Color.white);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 18));

        this.signInPanel.getEnterButton().addActionListener(e -> {
            String teamName = signInPanel.getTeamName();
//            String teamNamePassword = signInPanel.getPassword();  //  הוספתי לבנתיים במידה ויצטרך לפונקציית-validate
            if (this.signInPanel.hasEmptyField()) {
                JOptionPane.showMessageDialog(null, "One or more of your fields is empty, \nplease fill in these fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (JDBC.isExist(teamName)) {      //שינינו ל-validate?
                    System.out.println("login success");
                    this.signInPanel.setVisible(false);
                    this.signInPanel.restartPanel();
                    this.levelsPanel.setVisible(true);
                }else {
                    JOptionPane.showMessageDialog(null, "Your team name is not exist, \nplease correct it or create a new team", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }


        });
        this.levelsPanel.getReturnButton().addActionListener(e -> {
            this.levelsPanel.setVisible(false);
            this.signInPanel.setVisible(true);
        });
        this.signInPanel.getSignUpButton().addActionListener(e -> {
            this.signInPanel.setVisible(false);
            this.signInPanel.restartPanel();
            this.signUpPanel.setVisible(true);
        });
        this.signUpPanel.getRegisterButton().addActionListener(e -> {
            String teamName = signUpPanel.getTeamName();
            String teamPassword = signUpPanel.getTeamPassword();

            if (this.signUpPanel.isVerifiedPassword()) {
                if (!this.signUpPanel.hasEmptyField()) {
                    this.signUpPanel.setVisible(false);
//                this.signUpPanel.restartPanel();
                    this.signInPanel.setVisible(true);
                    JDBC.register(teamName, teamPassword);
                }else {
                    JOptionPane.showMessageDialog(null, "One or more of your fields is empty, \nplease fill in these fields", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Your passwords are not similar or empty, \nplease correct it", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.levelsPanel.getLevelButton1().addActionListener(e -> {
            this.levelsPanel.setVisible(false);
            this.add(levelOne);
            levelOne.setVisible(true);
        });

    }
    public void showWindow () {
        this.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
