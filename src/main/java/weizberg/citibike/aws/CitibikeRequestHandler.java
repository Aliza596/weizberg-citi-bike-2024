package weizberg.citibike.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import weizberg.citibike.service.MergeStationData;
import weizberg.citibike.service.StationMethods;
import weizberg.citibike.json.Station;

import java.util.Map;

public class CitibikeRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, CitibikeResponse> {

    @Override
    public CitibikeResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        String body = event.getBody();
        Gson gson = new Gson();
        CitibikeRequest request = gson.fromJson(body, CitibikeRequest.class);

        MergeStationData mergeStationData = new MergeStationData();
        Map<String, Station> stationsMap = mergeStationData.getStationsMap();
        StationMethods stationMethods = new StationMethods(stationsMap);

        Station start = stationMethods.closestPickUpStation(request.getFrom().getLat(), request.getFrom().getLon());
        Station end = stationMethods.closestDropOffStation(request.getTo().getLat(), request.getTo().getLon());

        return new CitibikeResponse(request.getFrom(), request.getTo(), start, end);
    }

}
