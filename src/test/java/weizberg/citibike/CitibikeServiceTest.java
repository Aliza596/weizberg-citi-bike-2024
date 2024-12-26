package weizberg.citibike;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import org.junit.jupiter.api.Test;
import weizberg.citibike.json.DataCollection;
import weizberg.citibike.json.Stations;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CitibikeServiceTest {

    @Test
    public void getStationInformation() {
        //given
        CitibikeServiceFactory factory = new CitibikeServiceFactory();

        //when
        Map<String, Stations> stationsMap = factory.getStationsMap();

        //then
        assertNotNull(stationsMap.values().iterator().next());
        assertTrue(stationsMap.values().iterator().next().lat != 0);
        assertTrue(stationsMap.values().iterator().next().lon != 0);
    }

    @Test
    public void getStationStatus() {
        //given
        CitibikeServiceFactory factory = new CitibikeServiceFactory();

        //when
        Map<String, Stations> stationsMap = factory.getStationsMap();

        //then
        assertNotNull(stationsMap.values().iterator().next());
        assertTrue(stationsMap.values().iterator().next().num_bikes_available >= 0);
        assertTrue(stationsMap.values().iterator().next().num_docks_available >= 0);
    }
}
