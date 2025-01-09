package weizberg.citibike.service;


import weizberg.citibike.json.Station;


import java.util.Map;


public class StationMethods {


    private final Map<String, Station> stations;


    public StationMethods(Map<String, Station> stations) {
        this.stations = stations;
    }

    public int getNumDocks(String stationId) {
        Station station = stations.get(stationId);
        return station.num_docks_available;
    }

    public int getNumBikes(String stationId) {
        Station station = stations.get(stationId);
        return station.num_bikes_available;
    }


    public Station closestPickUpStation(double lat, double lon) {
        double closestDistance = Double.MAX_VALUE;
        Station closestStation = null;

        for (Station station : stations.values()) {
            double stationLon = station.lon;
            double stationLat = station.lat;
            double distance = distance(lat, lon, stationLat, stationLon);

            int bikes = station.num_bikes_available;

            if (distance < closestDistance && bikes > 0) {
                closestDistance = distance;
                closestStation = station;
            }
        }

        return closestStation;
    }


    public Station closestDropOffStation(double lat, double lon) {
        double closestDistance = Double.MAX_VALUE;
        Station closestStation = null;

        for (Station station : stations.values()) {
            double stationLon = station.lon;
            double stationLat = station.lat;
            double distance = distance(lat, lon, stationLat, stationLon);
            int docks = station.num_docks_available;

            if (distance < closestDistance && docks > 0) {
                closestDistance = distance;
                closestStation = station;
            }
        }

        return closestStation;
    }


    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double xSquared = (lon2 - lon1) * (lon2 - lon1);
        double ySquared = (lat2 - lat1) * (lat2 - lat1);
        return Math.sqrt(xSquared + ySquared);
    }


}
