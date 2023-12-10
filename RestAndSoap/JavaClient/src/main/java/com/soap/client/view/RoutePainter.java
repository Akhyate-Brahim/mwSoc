package com.soap.client.view;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class RoutePainter implements Painter<JXMapViewer> {

    private List<GeoPosition> route;
    private List<GeoPosition> checkPoints;

    public RoutePainter(List<GeoPosition> route, List<GeoPosition> checkPoints) {
        this.route = route;
        this.checkPoints = checkPoints;
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
        g = (Graphics2D) g.create();

        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color onFootColor = Color.RED;
        Color onBikeColor = Color.magenta;
        Color currentColor = onFootColor;

        // If only two checkpoints are present, we assume the entire route is on foot
        boolean isMonoRoute = checkPoints.size() == 2;

        for (int i = 1; i < route.size(); i++) {
            GeoPosition gp = route.get(i);
            Point2D point = map.getTileFactory().geoToPixel(gp, map.getZoom());
            Point2D prevPoint = map.getTileFactory().geoToPixel(route.get(i - 1), map.getZoom());

            // If it's a mono route, we keep the onFootColor throughout
            if (isMonoRoute) {
                currentColor = onFootColor;
            } else {
                // If more than two checkpoints, determine the color based on the position in the route
                if (route.get(i - 1).equals(checkPoints.get(1)) || gp.equals(checkPoints.get(2))) {
                    currentColor = onBikeColor;
                } else if (gp.equals(checkPoints.get(1)) || route.get(i - 1).equals(checkPoints.get(2))) {
                    currentColor = onFootColor;
                }
            }

            // Draw the line segment
            g.setColor(currentColor);
            g.setStroke(new BasicStroke(4));
            g.drawLine((int) prevPoint.getX(), (int) prevPoint.getY(), (int) point.getX(), (int) point.getY());
        }

        g.dispose();
    }

}
