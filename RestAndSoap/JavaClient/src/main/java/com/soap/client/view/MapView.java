package com.soap.client.view;
import com.soap.client.model.CustomRouteSegment;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MapView extends JPanel {
    private final JXMapViewer jxMapViewer = new JXMapViewer();
    private List<CustomRouteSegment> routeSegments;
    private List<GeoPosition> checkpoints;

    public MapView(){

        //initialize the map
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory defaultTileFactory = new DefaultTileFactory(info);
        jxMapViewer.setTileFactory(defaultTileFactory);


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
    }
    public void refreshMap() {
        jxMapViewer.setAddressLocation(routeSegments.get(0).getRoutePositions().get(0));
        List<GeoPosition> positions = routeSegments.stream()
                .flatMap(segment -> segment.getRoutePositions().stream())
                .collect(Collectors.toList());
        RoutePainter routePainter = new RoutePainter(positions, checkpoints);
        jxMapViewer.setOverlayPainter(routePainter);
    }


    public void setRouteSegments(List<CustomRouteSegment> routeSegments, List<GeoPosition> checkpoints) {
        this.routeSegments = routeSegments;
        this.checkpoints = checkpoints;
        refreshMap();
    }
}
