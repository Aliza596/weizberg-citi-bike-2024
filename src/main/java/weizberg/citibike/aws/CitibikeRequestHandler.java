package weizberg.citibike.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import weizberg.citibike.StationMethods;
import weizberg.citibike.json.Station;

public class CitibikeRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, CitibikeResponse> {

    @Override
    public CitibikeResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        String body = event.getBody();
        Gson gson = new Gson();
        CitibikeRequest request = gson.fromJson(body, CitibikeRequest.class);

        StationMethods stationMethods = new StationMethods();

        Station start = stationMethods.closestPickUpStation(request.getFrom().getLat(), request.getFrom().getLon());
        Station end = stationMethods.closestDropOffStation(request.getTo().getLat(), request.getTo().getLon());


        return new CitibikeResponse(request.getFrom(), request.getTo(), start, end);
    }

}
