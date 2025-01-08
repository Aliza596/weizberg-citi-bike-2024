package weizberg.citibike.service;


import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;
import weizberg.citibike.json.Data;
import weizberg.citibike.json.DataCollection;
import weizberg.citibike.json.Station;
import weizberg.citibike.lambda.StationsCache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MergeStationData {

    CitibikeService citibikeService;
    StationsCache stationsCache = new StationsCache();

    public MergeStationData() {
        CitibikeServiceFactory factory = new CitibikeServiceFactory();
        citibikeService = factory.getCitibikeService();
    }

    public Map<String, Station> mergeData() {
        Data stationStatus = stationsCache.getStationResponse();
        DataCollection stationInfo = citibikeService.stationLocation().blockingGet();

        Map<String, Station> stationMap = new HashMap<>();

        for (int i = 0; i < stationStatus.stations.length; i++) {
            Station station = stationStatus.stations[i];
            stationMap.put(station.station_id, station);
        }

        for (int i = 0; i < stationInfo.data.stations.length; i++) {
            Station status = stationInfo.data.stations[i];
            Station currentStation = stationMap.get(status.station_id);

            if (currentStation != null) {
                currentStation.num_bikes_available = status.num_bikes_available;
                currentStation.num_docks_available = status.num_docks_available;
            } else {
                stationMap.put(status.station_id, status);
            }
        }

        return stationMap;

    }

}

