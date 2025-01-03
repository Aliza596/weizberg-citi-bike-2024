package weizberg.citibike.ui;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
        sendRequest().subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);
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


    public Single<CitibikeResponse> sendRequest() throws IOException {
        return serviceFactory.callLambda(request)
                .doOnSuccess(this::handleResponse)
                .doOnError(this::handleError);
    }

    private void handleResponse(CitibikeResponse response) {
        this.response = response;
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
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
