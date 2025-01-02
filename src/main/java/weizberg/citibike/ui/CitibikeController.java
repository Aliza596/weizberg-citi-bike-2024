package weizberg.citibike.ui;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.Waypoint;
import weizberg.citibike.aws.CitibikeRequest;
import weizberg.citibike.aws.CitibikeResponse;
import weizberg.citibike.aws.CoordinateLocation;
import weizberg.citibike.lambda.LambdaServiceFactory;

import java.io.IOException;


public class CitibikeController {

    CitibikeRequest request;
    LambdaServiceFactory serviceFactory;
    CitibikeResponse response;

    public CitibikeController() {
        request = new CitibikeRequest();
        serviceFactory = new LambdaServiceFactory();
    }

    public void retrieveBikeInformation(String strFromLocation, String strToLocation) throws IOException {
        addFromLocation(strFromLocation);
        addToLocation(strToLocation);
        sendRequest();
    }

    public Waypoint startLocation() {
        return new DefaultWaypoint(request.getFrom().getLat(), request.getFrom().getLon());
    }

    public Waypoint endLocation() {
        return new DefaultWaypoint(request.getTo().getLat(), request.getTo().getLon());
    }


    public Waypoint bikePickUpLocation() {
        return new DefaultWaypoint(response.getStart().getLat(), response.getStart().getLon());
    }

    public Waypoint bikeDropOffLocation() {
        return new DefaultWaypoint(response.getEnd().getLat(), response.getEnd().getLon());
    }


    public void sendRequest() throws IOException {
        response = serviceFactory.callLambda(request);
    }

    public void addFromLocation(String strFromLocation) {
        double[] coordinates = stringToCoordinates(strFromLocation);
        request.setFrom(new CoordinateLocation(coordinates[0], coordinates[1]));
    }

    public void addToLocation(String strToLocation) {
        double[] coordinates = stringToCoordinates(strToLocation);
        request.setTo(new CoordinateLocation(coordinates[0], coordinates[1]));
    }

    public double[] stringToCoordinates(String strCoordinates) {
        strCoordinates = strCoordinates.replaceAll("[\\[\\]]", "").trim();
        String[] strCoordinatesArray = strCoordinates.split(",");

        return new double[]{ Double.parseDouble(strCoordinatesArray[0]),
        Double.parseDouble(strCoordinatesArray[1]) };
    }


}
