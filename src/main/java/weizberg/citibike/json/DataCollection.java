package weizberg.citibike.json;

import java.util.HashMap;
import java.util.Map;

public class DataCollection {

    public Data data;
    Map<String, Stations> stationsMap = new HashMap<>();

    public void mergeData(DataCollection collection) {

        for (int i = 0; i < collection.data.stations.length; i++) {
            Stations status = collection.data.stations[i];
            Stations station = stationsMap.get(status.station_id);

            if (station != null) {
                station.num_bikes_available = status.num_bikes_available;
                station.num_docks_available = status.num_docks_available;
            }
        }

        for (int i = 0; i < this.data.stations.length; i++) {
            Stations stations = this.data.stations[i];
            stationsMap.put(stations.station_id, stations);


        }
    }

    public Map<String, Stations> getStationsMap() {
        return stationsMap;
    }

}
