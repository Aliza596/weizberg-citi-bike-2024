package weizberg.citibike;


import org.junit.jupiter.api.Test;
import weizberg.citibike.json.Station;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


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
    public void closestPickUpStation() {
        //given
        StationMethods stationMethods = new StationMethods();

        //when
        String closestId = stationMethods.closestPickUpStation(40.66619, -73.93162).station_id;

        //then
        stationMethods.stationStatus(closestId);
        if (stationMethods.getNumBikesAvailable() > 0) {
            assertEquals(closestId, "922763d6-d73c-4678-8bcd-f42ee3a236db");
        } else {
            assertNotEquals(closestId, "922763d6-d73c-4678-8bcd-f42ee3a236db");
        }
    }

    @Test
    public void closestDropOffStation() {
        //given
        StationMethods stationMethods = new StationMethods();

        //when
        String closestId = stationMethods.closestDropOffStation(40.66619, -73.93162).station_id;

        //then
        stationMethods.stationStatus(closestId);
        if (stationMethods.getNumDocksAvailable() > 0) {
            assertEquals(closestId, "922763d6-d73c-4678-8bcd-f42ee3a236db");
        } else {
            assertNotEquals(closestId, "922763d6-d73c-4678-8bcd-f42ee3a236db");
        }
    }

}
