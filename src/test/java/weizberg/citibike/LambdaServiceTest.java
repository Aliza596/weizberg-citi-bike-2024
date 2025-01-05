package weizberg.citibike;

import org.junit.jupiter.api.Test;
import weizberg.citibike.aws.CitibikeRequest;
import weizberg.citibike.aws.CitibikeResponse;
import weizberg.citibike.aws.CoordinateLocation;
import weizberg.citibike.lambda.LambdaService;
import weizberg.citibike.lambda.LambdaServiceFactory;
import weizberg.citibike.ui.CitibikeController;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LambdaServiceTest {

    @Test
    public void callLambda() throws IOException {
        //given
        LambdaServiceFactory factory = new LambdaServiceFactory();
        LambdaService service = factory.getLambdaService();
        CitibikeRequest request = new CitibikeRequest();
        CoordinateLocation from = new CoordinateLocation(40.8211, -73.9359);
        CoordinateLocation to = new CoordinateLocation(40.7190,  -73.9585);
        request.setFrom(from);
        request.setTo(to);

        //when
        CitibikeResponse response = service.callLambda(request).blockingGet();

        //then
        assertEquals(response.getStart().getName(), "Lenox Ave & W 146 St");
        //assertEquals(response.getEnd().getName(), "Berry St & N 8 St");
    }

}
