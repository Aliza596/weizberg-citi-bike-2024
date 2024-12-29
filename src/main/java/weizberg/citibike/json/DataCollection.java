package weizberg.citibike.json;


import java.util.HashMap;
import java.util.Map;


public class DataCollection {


    public Data data;
    Map<String, Station> stationsMap = new HashMap<>();


    public void mergeData(DataCollection collection) {

        for (int i = 0; i < this.data.stations.length; i++) {
            Station station = this.data.stations[i];
            stationsMap.put(station.station_id, station);
        }

        for (int i = 0; i < collection.data.stations.length; i++) {
            Station status = collection.data.stations[i];
            Station station = stationsMap.get(status.station_id);

            if (station != null) {
                station.num_bikes_available = status.num_bikes_available;
                station.num_docks_available = status.num_docks_available;
            }
        }
    }


    public Map<String, Station> getStationsMap() {
        return stationsMap;
    }


}
