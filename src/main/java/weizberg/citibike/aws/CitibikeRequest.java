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

    public void setFrom(CoordinateLocation from) {
        this.from = from;
    }

    public void setTo(CoordinateLocation to) {
        this.to = to;
    }
}
