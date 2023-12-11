package com.soap.client.view;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("JCDecauxGPS");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void changeView(JPanel newView) {
        getContentPane().removeAll();
        getContentPane().add(newView);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

}
