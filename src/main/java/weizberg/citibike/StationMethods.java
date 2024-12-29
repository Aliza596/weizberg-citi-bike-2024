package weizberg.citibike;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import weizberg.citibike.json.DataCollection;
import weizberg.citibike.json.Station;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StationMethods {

    public void stationStatus(String stationId, Map<String, Station> stations) {
        stations.get(stationId);
    }


    public Single<DataCollection> mergeData(CitibikeService citibikeService) {
        Single<DataCollection> stationInfo = citibikeService.stationLocation();
        Single<DataCollection> stationStatus = citibikeService.stationStatus();

        return Single.zip(stationInfo, stationStatus, (info, status) -> {
                    info.mergeData(status);
                    return info;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single());
    }

    public Map<String, Station> getStationsMap(CitibikeService service) {
        try {
            return mergeData(service).timeout(10, TimeUnit.SECONDS).blockingGet().getStationsMap();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String closestPickUpStation(double lat, double lon, Map<String, Station> stations) {
        double closestDistance = 0;
        String closestStationId = "";

        for (int i = 0; i < stations.values().size(); i++) {
            Station station = (Station) stations.values().toArray()[i];

            double stationLon = station.lon;
            double stationLat = station.lat;
            double distance = distance(lat, lon, stationLat, stationLon);

            if ((distance < closestDistance || closestDistance == 0) && station.num_bikes_available > 0) {
                closestDistance = distance;
                closestStationId = station.station_id;
            }
        }

        return closestStationId;
    }

    public String closestDropOffStation(double lat, double lon, Map<String, Station> stations) {
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
