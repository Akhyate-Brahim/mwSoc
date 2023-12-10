package com.soap.client.controller;

import com.soap.client.model.CustomRouteSegment;
import com.soap.client.util.Converter;
import com.soap.client.util.Sender;
import com.soap.client.view.InputView;
import com.soap.client.view.MainFrame;
import com.soap.client.view.MapView;
import com.soap.generated.IRoutingService;

import org.jxmapviewer.viewer.GeoPosition;

import javax.jms.JMSException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class RoutePlanningController {
    public static String ROUTESEGMENTS;
    MainFrame mainFrame;
    MapView mapView;
    InputView inputView;
    private List<GeoPosition> routePositions;
    IRoutingService routingService;
    Converter converter = new Converter();

    public RoutePlanningController(MainFrame mainFrame, InputView inputView, MapView mapView, IRoutingService routingService) {
        this.mainFrame = mainFrame;
        this.inputView = inputView;
        this.mapView = mapView;
        setUpListeners();
        mainFrame.add(inputView);
        mainFrame.setVisible(true);
        this.routingService = routingService;
    }

    private void setUpListeners() {
        inputView.getConfirmButton().addActionListener(e -> onConfirmButtonClicked());
        inputView.getConfirmButtonApacheMQ().addActionListener(e -> {
            try {
                onConfirmMQButtonClicked();
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        });
        mapView.getBackButton().addActionListener(e -> onBackButtonClicked());
    }

    private void onBackButtonClicked() {
        mainFrame.changeView(inputView);
    }


    private void onConfirmButtonClicked() {
        List<CustomRouteSegment> routeSegments = Converter.convertArrayOfRouteSegments(routingService.getItinerary(inputView.getDepartureAddress(), inputView.getArrivalAddress()));
        List<GeoPosition> checkpoints = new ArrayList<>();
        if (!routeSegments.isEmpty()) {
            if (!routeSegments.get(0).getRoutePositions().isEmpty()) {
                checkpoints.add(routeSegments.get(0).getRoutePositions().get(0));
            }
            for (CustomRouteSegment segment : routeSegments) {
                List<GeoPosition> positions = segment.getRoutePositions();
                if (!positions.isEmpty()) {
                    checkpoints.add(positions.get(positions.size() - 1));
                }
            }
        }
        mapView.setRouteSegments(routeSegments, checkpoints);
        mainFrame.changeView(mapView);
        mapView.refreshMap();
    }

    private void onConfirmMQButtonClicked() throws JMSException {
        Sender.sendMessage("itineraryDetails", inputView.getDepartureAddress());
        Sender.sendMessage("itineraryDetails", inputView.getArrivalAddress());
    }


}
