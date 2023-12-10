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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MapViewerExample {
    public static void main(String[] args) {
        // Create a JXMapViewer
        JXMapViewer mapViewer = new JXMapViewer();

        // Tile Factory Information
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Enable interactions
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));
        PanMouseInputListener panMouseInputListener = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(panMouseInputListener);
        mapViewer.addMouseMotionListener(panMouseInputListener);

        // Set the focus
        GeoPosition initGeoPosition = new GeoPosition(40.714, -74.006); // Example coordinates (New York City)
        mapViewer.setAddressLocation(initGeoPosition);

        // List to store selected positions
        List<GeoPosition> selectedPositions = new ArrayList<>();

        // Add mouse listener for selecting points
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Convert to GeoPosition
                GeoPosition geoPosition = mapViewer.convertPointToGeoPosition(e.getPoint());
                System.out.println("Selected GeoPosition: " + geoPosition);
                selectedPositions.add(geoPosition);

                // TODO: Add visual markers or handle selected positions as needed
            }
        });

        // Display map viewer in a JFrame
        JFrame frame = new JFrame("Map Viewer Example");
        frame.getContentPane().add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
