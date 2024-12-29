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


        //then
        assertFalse(stationsMap.isEmpty());
        assertNotNull(stationsMap.values().iterator().next());
        assertTrue(stationsMap.values().iterator().next().lat != 0);
        assertTrue(stationsMap.values().iterator().next().lon != 0);
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
