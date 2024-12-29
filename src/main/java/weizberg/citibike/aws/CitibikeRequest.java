package weizberg.citibike.aws;

public class CitibikeRequest {

    private CoordinateLocation from;
    private CoordinateLocation to;

    public CoordinateLocation getFrom() {
        return from;
    }

    public CoordinateLocation getTo() {
        return to;
    }
}
