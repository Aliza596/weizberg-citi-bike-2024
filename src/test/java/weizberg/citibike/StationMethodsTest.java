package weizberg.citibike;


import org.junit.jupiter.api.Test;
import weizberg.citibike.service.MergeStationData;
import weizberg.citibike.service.StationMethods;
import weizberg.citibike.json.Station;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class StationMethodsTest {

    @Test
    public void stationStatus() {
        //given
        MergeStationData mergeStationData = new MergeStationData();
        Map<String, Station> stationMap = mergeStationData.mergeData();

        //when
        StationMethods stationMethods = new StationMethods(stationMap);
        Station station = stationMap.get("69717638-5c4a-47a7-bccb-3b42c81eb09f");
        int numDocks = station.num_docks_available;
        int numBikes = station.num_bikes_available;
        int actualBikes = stationMethods.getNumBikes("69717638-5c4a-47a7-bccb-3b42c81eb09f");
        int actualDocks = stationMethods.getNumDocks("69717638-5c4a-47a7-bccb-3b42c81eb09f");

        //then
        assertEquals(numDocks, actualDocks);
        assertEquals(numBikes, actualBikes);
    }


    @Test
    public void closestPickUpStation() {
        //given
        MergeStationData mergeStationData = new MergeStationData();
        Map<String, Station> stationsMap = mergeStationData.mergeData();
        StationMethods stationMethods = new StationMethods(stationsMap);

        //when
        Station actualClosestStation = stationMethods.closestPickUpStation(40.72368, -73.90458);

        //then
        assertEquals("56 Dr & 61 St", actualClosestStation.name);
    }

    @Test
    public void closestDropOffStation() {
        //given
        MergeStationData mergeStationData = new MergeStationData();
        Map<String, Station> stationsMap = mergeStationData.mergeData();
        StationMethods stationMethods = new StationMethods(stationsMap);

        //when
        Station actualClosestStation = stationMethods.closestDropOffStation(40.72368, -73.90458);

        //then
        assertEquals("56 Dr & 61 St", actualClosestStation.name);
    }
}
