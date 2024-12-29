package weizberg.citibike;


import org.junit.jupiter.api.Test;
import weizberg.citibike.json.Station;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class StationMethodsTest {




    @Test
    public void stationStatus() {
        //given
        MergeStationData mergeStationData = new MergeStationData();
        Map<String, Station> stationMap = mergeStationData.getStationsMap();


        //when
        StationMethods stationMethods = new StationMethods();
        Station station = stationMap.get("69717638-5c4a-47a7-bccb-3b42c81eb09f");
        int numDocks = station.num_docks_available;
        int numBikes = station.num_bikes_available;
        stationMethods.stationStatus("69717638-5c4a-47a7-bccb-3b42c81eb09f");


        //then
        assertEquals(numDocks, stationMethods.getNumDocksAvailable());
        assertEquals(numBikes, stationMethods.getNumBikesAvailable());
    }


    @Test
    public void stationStatus() {
        //given
        StationMethods stationMethods = new StationMethods();
        Map<String, Station> stationMap = stationMethods.getStationsMap();

        //when
        Station station = stationMap.get("69717638-5c4a-47a7-bccb-3b42c81eb09f");
        int numDocks = station.num_docks_available;
        int numBikes = station.num_bikes_available;
        stationMethods.stationStatus("69717638-5c4a-47a7-bccb-3b42c81eb09f", stationMap);

        //then
        assertEquals(numDocks, stationMethods.getNumDocksAvailable());
        assertEquals(numBikes, stationMethods.getNumBikesAvailable());
    }

    @Test
    public void closestPickUp() {
        //given
        MergeStationData mergeStationData = new MergeStationData();
        Map<String, Station> stationsMap = mergeStationData.getStationsMap();


        //when
        StationMethods stationMethods = new StationMethods();
        String closestId = stationMethods.closestPickUpStation(40.72368, -73.90458);
        System.out.println("ID: " + closestId);


        //then
        assertEquals(closestId, "69717638-5c4a-47a7-bccb-3b42c81eb09f");
    }
}
