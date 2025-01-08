package weizberg.citibike;


import org.junit.jupiter.api.Test;
import weizberg.citibike.service.MergeStationData;
import weizberg.citibike.json.Station;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;


public class CitibikeServiceTest {


    @Test
    public void stationLocation() {
        //given
        MergeStationData mergeStationData = new MergeStationData();

        //when
        Map<String, Station> stationsMap = mergeStationData.mergeData();
        Station station = stationsMap.get("69717638-5c4a-47a7-bccb-3b42c81eb09f");

        //then
        assertFalse(stationsMap.isEmpty());
        assertNotNull(station);
        assertEquals(40.72368, station.lat);
        assertEquals(-73.90458, station.lon);
    }


    @Test
    public void stationStatus() {
        //given
        MergeStationData mergeStationData = new MergeStationData();

        //when
        Map<String, Station> stationsMap = mergeStationData.mergeData();

        //then
        assertFalse(stationsMap.isEmpty());
        assertNotNull(stationsMap.values().iterator().next());
        assertTrue(stationsMap.values().iterator().next().num_bikes_available >= 0);
        assertTrue(stationsMap.values().iterator().next().num_docks_available >= 0);
    }
}
