package com.soap.client.util;
import com.soap.client.model.CustomRouteSegment;
import com.soap.client.model.CustomStep;
import com.soap.generated.*;
import org.jxmapviewer.viewer.GeoPosition;
import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    public static CustomStep convertStep(Step step) {
        CustomStep customStep = new CustomStep();

        customStep.setDistance(step.getDistance());
        customStep.setDuration(step.getDuration());
        customStep.setInstruction(step.getInstruction().getValue());
        customStep.setName(step.getName().getValue());
        customStep.setType(step.getType());
        customStep.setWayPoints(step.getWayPoints().getValue().getInt());

        return customStep;
    }
    public static CustomRouteSegment convertRouteSegment(RouteSegment jaxbRouteSegment) {
        CustomRouteSegment customRouteSegment = new CustomRouteSegment();

        customRouteSegment.setDistance(jaxbRouteSegment.getDistance());
        customRouteSegment.setDuration(jaxbRouteSegment.getDuration());
        customRouteSegment.setRoutePositions(convertArrayOfPositionToList(jaxbRouteSegment.getRoutePositions()));
        customRouteSegment.setSteps(convertArrayOfStepToList(jaxbRouteSegment.getSteps()));

        return customRouteSegment;
    }

    private static List<GeoPosition> convertArrayOfPositionToList(JAXBElement<ArrayOfPosition> jaxbElement) {
        if (jaxbElement != null && jaxbElement.getValue() != null) {
            return jaxbElement.getValue().getPosition().stream()
                    .map(pos -> new GeoPosition(pos.getLat(), pos.getLng()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static List<CustomStep> convertArrayOfStepToList(JAXBElement<ArrayOfStep> jaxbElement) {
        if (jaxbElement != null && jaxbElement.getValue() != null) {
            return jaxbElement.getValue().getStep().stream()
                    .map(Converter::convertStep)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    public static List<CustomRouteSegment> convertArrayOfRouteSegments(ArrayOfRouteSegment arrayOfRouteSegments) {
        if (arrayOfRouteSegments != null && arrayOfRouteSegments.getRouteSegment() != null) {
            return arrayOfRouteSegments.getRouteSegment().stream()
                    .map(Converter::convertRouteSegment)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
