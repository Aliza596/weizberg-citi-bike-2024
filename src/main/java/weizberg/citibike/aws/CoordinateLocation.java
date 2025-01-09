package weizberg.citibike.aws;

public class CoordinateLocation {

    private double lat;
    private double lon;

    public CoordinateLocation(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

}
