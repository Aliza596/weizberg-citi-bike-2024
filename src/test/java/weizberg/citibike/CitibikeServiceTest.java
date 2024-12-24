package weizberg.citibike;

import org.junit.jupiter.api.Test;
import weizberg.citibike.json.DataCollection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CitibikeServiceTest {

    @Test
    public void fetchGeneralStationInformation() {

        //given
        CitibikeService service = new CitibikeServiceFactory().getCitibikeService();

        //when
        DataCollection dataCollection = service.stationLocation().blockingGet();

        //then
        assertNotNull(dataCollection.data.stations[0].station_id);
        assertTrue(dataCollection.data.stations[0].lat != 0.0);
        assertTrue(dataCollection.data.stations[0].lon != 0.0);

    }

    @Test
    public void fetchStationAvailability() {
        //given
        CitibikeService service = new CitibikeServiceFactory().getCitibikeService();

        //when
        DataCollection dataCollection = service.stationStatus().blockingGet();

        //then
        assertTrue(dataCollection.data.stations[0].num_bikes_available >= 0);
    }



}
