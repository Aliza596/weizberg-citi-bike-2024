package weizberg.citibike;

import org.junit.jupiter.api.Test;
import weizberg.citibike.json.Station;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StationMethodsTest {


    @Test
    public void closestPickUp() {
        //given
        CitibikeServiceFactory factory = new CitibikeServiceFactory();
        StationMethods stationMethods = new StationMethods();
        Map<String, Station> stationsMap = stationMethods.getStationsMap(factory.getCitibikeService());

        //when
        String closestId = stationMethods.closestPickUpStation(40.72368, -73.90458, stationsMap);

        //then
        assertEquals(closestId, "69717638-5c4a-47a7-bccb-3b42c81eb09f");
    }


}
