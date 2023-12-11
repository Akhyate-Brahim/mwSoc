package com.soap.client.view;
import com.soap.client.model.CustomRouteSegment;
import com.soap.client.model.CustomStep;
import com.soap.generated.Step;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MapView extends JPanel {
    private final JXMapViewer jxMapViewer = new JXMapViewer();
    private JTextArea instructionsArea;
    private List<CustomRouteSegment> routeSegments;
    private List<GeoPosition> checkpoints;
    private JButton backButton;
    private JButton updateButton;

    public MapView(){

        //initialize the map
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory defaultTileFactory = new DefaultTileFactory(info);
        jxMapViewer.setTileFactory(defaultTileFactory);

        backButton = new JButton("Back to Input");
        this.add(backButton, BorderLayout.SOUTH);


        // initialize interactions
        jxMapViewer.addMouseListener(new CenterMapListener(jxMapViewer));
        jxMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(jxMapViewer));
        jxMapViewer.addKeyListener(new PanKeyListener(jxMapViewer));
        PanMouseInputListener pmiListener = new PanMouseInputListener(jxMapViewer);
        jxMapViewer.addMouseListener(pmiListener);
        jxMapViewer.addMouseMotionListener(pmiListener);

        // Add the JXMapViewer to this panel
        this.setLayout(new BorderLayout());
        this.add(jxMapViewer, BorderLayout.CENTER);


        // Initialize the instructions area
        instructionsArea = new JTextArea(5, 30);
        instructionsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(instructionsArea);
        this.add(scrollPane, BorderLayout.EAST);

        backButton = new JButton("Back to Input");
        backButton.setVisible(true); // Make sure the button is visible

        // Add the back button at the bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);
        updateButton = new JButton("Next steps");
        bottomPanel.add(updateButton);
        updateButton.setVisible(false);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }
    public void refreshMap() {
        // Setting the address location
        jxMapViewer.setAddressLocation(routeSegments.get(0).getRoutePositions().get(0));

        // Creating the route painter
        List<GeoPosition> positions = routeSegments.stream()
                .flatMap(segment -> segment.getRoutePositions().stream())
                .collect(Collectors.toList());
        RoutePainter routePainter = new RoutePainter(positions, checkpoints);

        // checkpoints marking
        Set<Waypoint> waypoints = new HashSet<>();
        waypoints.add(new DefaultWaypoint(checkpoints.get(0)));
        waypoints.add(new DefaultWaypoint(checkpoints.get(checkpoints.size()-1)));
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>();
        compoundPainter.setPainters(routePainter, waypointPainter);
        jxMapViewer.setOverlayPainter(compoundPainter);
        updateInstructions();
    }

    private void updateInstructions() {
        StringBuilder instructions = new StringBuilder();
        for (CustomRouteSegment segment : routeSegments) {
            for (CustomStep step : segment.getSteps()) {
                instructions.append(step.getInstruction()).append("\n");
            }
        }
        instructionsArea.setText(instructions.toString());
    }
    public void setRouteSegments(List<CustomRouteSegment> routeSegments, List<GeoPosition> checkpoints) {
        this.routeSegments = routeSegments;
        this.checkpoints = checkpoints;
        refreshMap();
    }

    public JButton getBackButton() {
        return backButton;
    }
    public JButton getUpdateButton() {
        return updateButton;
    }

}
