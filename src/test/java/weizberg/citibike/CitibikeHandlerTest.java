package weizberg.citibike;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.jupiter.api.Test;
import weizberg.citibike.aws.CitibikeRequestHandler;
import weizberg.citibike.aws.CitibikeResponse;
import weizberg.citibike.json.Station;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CitibikeHandlerTest {

    @Test
    public void handleRequest() throws IOException {

        //given
        Path path = Path.of("request.json");
        String json = Files.readString(path);

        Context context = mock(Context.class);

        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        requestEvent.setBody(json);
        CitibikeRequestHandler handler = new CitibikeRequestHandler();

        //when
        CitibikeResponse response = handler.handleRequest(requestEvent, context);

        //then
        assertNotNull(response);
        assertEquals(response.getStart().getName(), "Lenox Ave & W 146 St");
        //assertEquals(response.getEnd().getName(), "Berry St & N 8 St");
    }
}
