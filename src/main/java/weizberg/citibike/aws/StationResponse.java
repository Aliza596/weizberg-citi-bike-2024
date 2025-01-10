package weizberg.citibike.aws;

import weizberg.citibike.json.Station;

public class StationResponse {

    private double lat;
    private double lon;
    private String name;

    //CHECKSTYLE:OFF
    private String station_id;
    //CHECKSTYLE:ON

    public StationResponse(Station station) {
        this.lat = station.lat;
        this.lon = station.lon;
        this.name = station.name;
        this.station_id = station.station_id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

}
