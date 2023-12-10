package com.soap.client.view;

import javax.swing.*;
import java.awt.*;

public class InputView extends JPanel {
    private JTextField departureAddressField;
    private JTextField arrivalAddressField;
    private JButton confirmButton;
    private JButton confirmButtonApacheMQ;
    public InputView() {
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create labels with French text
        JLabel departureLabel = new JLabel("Adresse de départ:");
        JLabel arrivalLabel = new JLabel("Adresse d'arrivée:");

        // Create text fields
        departureAddressField = new JTextField(20);
        arrivalAddressField = new JTextField(20);

        // Create confirm buttons
        confirmButton = new JButton("use Soap");
        confirmButtonApacheMQ = new JButton("use ApacheMQ");

        // Layout constraints for departure label
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(departureLabel, gbc);

// Layout constraints for departure text field
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(departureAddressField, gbc);

// Layout constraints for arrival label
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(arrivalLabel, gbc);

// Layout constraints for arrival text field
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(arrivalAddressField, gbc);

// Layout constraints for confirmButton
        gbc.gridx = 0; // X position
        gbc.gridy = 2; // Y position (below the text fields)
        gbc.gridwidth = 2; // Span across 2 columns
        gbc.anchor = GridBagConstraints.CENTER;
        add(confirmButton, gbc);

// Layout constraints for confirmButtonApacheMQ
        gbc.gridx = 0; // X position
        gbc.gridy = 3; // Y position (below the confirmButton)
        gbc.gridwidth = 2; // Span across 2 columns
        gbc.anchor = GridBagConstraints.CENTER;
        add(confirmButtonApacheMQ, gbc);

    }

    public String getDepartureAddress() {
        return departureAddressField.getText();
    }

    public String getArrivalAddress() {
        return arrivalAddressField.getText();
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
    public JButton getConfirmButtonApacheMQ(){return confirmButtonApacheMQ;}
}
