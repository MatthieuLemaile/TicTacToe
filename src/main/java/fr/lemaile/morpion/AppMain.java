package fr.lemaile.morpion;

import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class AppMain {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(200, 200);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(jFrame.getParent());
        jFrame.setVisible(true);
//        new Board(new BoardUi())
    }
}
