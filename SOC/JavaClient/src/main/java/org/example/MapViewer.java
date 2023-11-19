package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapViewer extends JPanel {
    private final JXMapViewer jxMapViewer = new JXMapViewer();
    private final List<GeoPosition> selectedPoints = new ArrayList<>();
    private final Set<Waypoint> waypoints = new HashSet<>();

    public MapViewer() {
        initializeMap();
        setupInteractions();
        setupLayout();
    }

    private void initializeMap() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jxMapViewer.setTileFactory(tileFactory);
        jxMapViewer.setAddressLocation(new GeoPosition(43.6155793, 7.0718748));
    }

    private void setupInteractions() {
        jxMapViewer.addMouseListener(new CenterMapListener(jxMapViewer));
        jxMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(jxMapViewer));
        jxMapViewer.addKeyListener(new PanKeyListener(jxMapViewer));
        PanMouseInputListener pmiListener = new PanMouseInputListener(jxMapViewer);
        jxMapViewer.addMouseListener(pmiListener);
        jxMapViewer.addMouseMotionListener(pmiListener);
        jxMapViewer.addMouseListener(new PointSelectionListener());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(jxMapViewer, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(createButton("Confirm", e -> onConfirm()));
        panel.add(createButton("Reset", e -> resetSelection()));
        return panel;
    }

    private JButton createButton(String title, ActionListener actionListener) {
        JButton button = new JButton(title);
        button.addActionListener(actionListener);
        return button;
    }

    private void onConfirm() {
        if (selectedPoints.size() < 2) {
            JOptionPane.showMessageDialog(this, "Please select two points on the map.");
            return;
        }
        System.out.println("Points chosen: " + selectedPoints);
    }

    private void resetSelection() {
        selectedPoints.clear();
        waypoints.clear();
        jxMapViewer.repaint();
    }

    private void updateWaypoints() {
        waypoints.clear();
        selectedPoints.forEach(pos -> waypoints.add(new DefaultWaypoint(pos)));
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        jxMapViewer.setOverlayPainter(waypointPainter);
    }

    private class PointSelectionListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            GeoPosition geoPosition = jxMapViewer.convertPointToGeoPosition(e.getPoint());
            if (selectedPoints.size() == 2) {
                selectedPoints.clear();
            }
            selectedPoints.add(geoPosition);
            updateWaypoints();
            jxMapViewer.repaint();
        }
    }
}
