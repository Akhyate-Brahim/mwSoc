package org.example;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Map Viewer Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MapViewer());
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
