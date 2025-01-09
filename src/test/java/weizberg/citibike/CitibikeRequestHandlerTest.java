package weizberg.citibike;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.jupiter.api.Test;
import weizberg.citibike.aws.CitibikeResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class CitibikeRequestHandlerTest {

    @Test
    public void handleRequest() throws IOException {

        //given
        Path path = Path.of("request.json");
        String json = Files.readString(path);

        Context context = mock(Context.class);

        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        requestEvent.setBody(json);
        weizberg.citibike.aws.CitibikeRequestHandler handler = new weizberg.citibike.aws.CitibikeRequestHandler();

        //when
        CitibikeResponse response = handler.handleRequest(requestEvent, context);

        //then
        assertNotNull(response);
        assertEquals("Lenox Ave & W 146 St", response.getStart().getName());
        assertEquals("Berry St & N 8 St", response.getEnd().getName());
    }
}
