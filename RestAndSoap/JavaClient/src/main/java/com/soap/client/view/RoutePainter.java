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

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(4));

        Point2D prevPoint = null;
        for(GeoPosition gp : route) {
            Point2D point = map.getTileFactory().geoToPixel(gp, map.getZoom());

            if(prevPoint != null) {
                g.drawLine((int) prevPoint.getX(), (int) prevPoint.getY(), (int) point.getX(), (int) point.getY());
            }
            prevPoint = point;
        }
        g.dispose();
    }
}
