package weizberg.citibike;


import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import weizberg.citibike.json.DataCollection;
import weizberg.citibike.json.Station;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class StationMethods {


    private final Map<String, Station> stations;


    public StationMethods() {
        MergeStationData mergeStationData = new MergeStationData();
        stations = mergeStationData.getStationsMap();
    }

    public int getNumDocks(String stationId) {
        Station station = stations.get(stationId);
        return station.num_docks_available;
    }

    public int getNumBikes(String stationId) {
        Station station = stations.get(stationId);
        return station.num_bikes_available;
    }


    public String closestPickUpStation(double lat, double lon) {
        double closestDistance = 0;
        String closestStationId = "";


        for (int i = 0; i < stations.values().size(); i++) {
            Station station = (Station) stations.values().toArray()[i];


            double stationLon = station.lon;
            double stationLat = station.lat;
            double distance = distance(lat, lon, stationLat, stationLon);
            int bikes = station.num_bikes_available;

            if ((i == 0) || (distance < closestDistance && bikes > 0)) {
                closestDistance = distance;
                closestStationId = station.station_id;
            }


        }


        return closestStationId;
    }


    public String closestDropOffStation(double lat, double lon) {
        double closestDistance = 0;
        String closestStationId = "";


        for (int i = 0; i < stations.values().size(); i++) {
            Station station = (Station) stations.values().toArray()[i];
            double stationLon = station.lon;
            double stationLat = station.lat;


            double distance = distance(lat, lon, stationLat, stationLon);


            if ((distance < closestDistance || closestDistance == 0) && station.num_docks_available > 0) {
                closestDistance = distance;
                closestStationId = station.station_id;
            }
        }


        return closestStationId;
    }


    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double xSquared = (lon2 - lon1) * (lon2 - lon1);
        double ySquared = (lat2 - lat1) * (lat2 - lat1);
        return Math.sqrt(xSquared + ySquared);
    }


}
