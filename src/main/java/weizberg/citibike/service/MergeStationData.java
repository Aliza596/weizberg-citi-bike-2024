package weizberg.citibike.service;


import weizberg.citibike.json.Data;
import weizberg.citibike.json.DataCollection;
import weizberg.citibike.json.Station;
import weizberg.citibike.lambda.StationsCache;


import java.util.HashMap;
import java.util.Map;


public class MergeStationData {

    CitibikeService citibikeService;
    StationsCache stationsCache;

    public MergeStationData() {
        CitibikeServiceFactory factory = new CitibikeServiceFactory();
        citibikeService = factory.getCitibikeService();
        stationsCache = new StationsCache(citibikeService);
    }

    public Map<String, Station> mergeData() {
        DataCollection stationStatus = citibikeService.stationStatus().blockingGet();
        Data stationInfo = stationsCache.getStationResponse();

        Map<String, Station> stationMap = new HashMap<>();

        for (Station station : stationStatus.data.stations) {
            stationMap.put(station.station_id, station);
        }

        for (Station stationLocation : stationInfo.stations) {
            Station stationInformation = stationMap.get(stationLocation.station_id);

            if (stationLocation != null) {
                stationLocation.num_docks_available = stationInformation.num_docks_available;
                stationLocation.num_bikes_available = stationInformation.num_bikes_available;
            } else {
                stationMap.put(stationLocation.station_id, stationLocation);
            }
            stationMap.put(stationLocation.station_id, stationLocation);
        }

        return stationMap;

    }

}

