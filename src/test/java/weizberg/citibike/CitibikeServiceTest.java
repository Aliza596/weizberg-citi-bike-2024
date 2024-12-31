package weizberg.citibike;


import org.junit.jupiter.api.Test;
import weizberg.citibike.json.Station;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;


public class CitibikeServiceTest {


    @Test
    public void stationLocation() {
        //given
        MergeStationData mergeStationData = new MergeStationData();

        //when
        Map<String, Station> stationsMap = mergeStationData.getStationsMap();
        Station station = stationsMap.get("69717638-5c4a-47a7-bccb-3b42c81eb09f");

        //then
        assertFalse(stationsMap.isEmpty());
        assertNotNull(station);
        assertEquals(station.lat, 40.72368);
        assertEquals(station.lon, -73.90458);
    }


    @Test
    public void stationStatus() {
        //given
        MergeStationData mergeStationData = new MergeStationData();


        //when
        Map<String, Station> stationsMap = mergeStationData.getStationsMap();


        //then
        assertFalse(stationsMap.isEmpty());
        assertNotNull(stationsMap.values().iterator().next());
        assertTrue(stationsMap.values().iterator().next().num_bikes_available >= 0);
        assertTrue(stationsMap.values().iterator().next().num_docks_available >= 0);
    }
}
