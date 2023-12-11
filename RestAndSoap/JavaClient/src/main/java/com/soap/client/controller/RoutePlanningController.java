package com.soap.client.controller;

import com.soap.client.model.CustomRouteSegment;
import com.soap.client.util.ActiveMQConsumer;
import com.soap.client.util.Converter;
import com.soap.client.util.JsonParser;
import com.soap.client.view.InputView;
import com.soap.client.view.MainFrame;
import com.soap.client.view.MapView;
import com.soap.generated.IRoutingService;

import org.jxmapviewer.viewer.GeoPosition;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

import java.util.ArrayList;
import java.util.List;

public class RoutePlanningController {
    MainFrame mainFrame;
    MapView mapView;
    InputView inputView;
    IRoutingService routingService;
    String queueName;
    ActiveMQConsumer MQconsumer;
    MessageConsumer consumer;

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
            onConfirmMQButtonClicked();
        });
        mapView.getBackButton().addActionListener(e -> onBackButtonClicked());
        mapView.getUpdateButton().addActionListener(e -> onUpdateButtonClicked());
    }

    private void onBackButtonClicked() {
        mainFrame.changeView(inputView);
    }


    private void updateMapViewWithRouteSegments(List<CustomRouteSegment> routeSegments) {
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

    private void onConfirmButtonClicked() {
        List<CustomRouteSegment> routeSegments = Converter.convertArrayOfRouteSegments(
                routingService.getItinerary(inputView.getDepartureAddress(), inputView.getArrivalAddress()));
        mapView.getUpdateButton().setVisible(false);
        updateMapViewWithRouteSegments(routeSegments);
    }

    public void onConfirmMQButtonClicked() {
        mapView.getUpdateButton().setVisible(true);
        try {
            queueName = routingService.getQueue(inputView.getDepartureAddress(), inputView.getArrivalAddress());
            MQconsumer = new ActiveMQConsumer("tcp://localhost:61616", queueName);
            MQconsumer.start();
            consumer = MQconsumer.getConsumer();

            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        List<CustomRouteSegment> routeSegments = JsonParser.parseRouteSegments(textMessage.getText());
                        updateMapViewWithRouteSegments(routeSegments);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    private void onUpdateButtonClicked() {
        routingService.requestMoreSteps(queueName);
    }




}
