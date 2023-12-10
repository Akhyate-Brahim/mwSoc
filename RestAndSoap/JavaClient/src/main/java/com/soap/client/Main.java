package com.soap.client;

import com.soap.client.controller.RoutePlanningController;
import com.soap.client.view.InputView;
import com.soap.client.view.MainFrame;
import com.soap.client.view.MapView;
import com.soap.generated.IRoutingService;
import com.soap.generated.RoutingService;
public class Main {
    public static void main(String[] args) {
            RoutingService service = new RoutingService();
            IRoutingService port = service.getBasicHttpBindingIRoutingService();
            MainFrame mainFrame = new MainFrame();
            InputView inputView = new InputView();
            MapView mapView = new MapView();
            new RoutePlanningController(mainFrame, inputView, mapView, port);
    }
}
