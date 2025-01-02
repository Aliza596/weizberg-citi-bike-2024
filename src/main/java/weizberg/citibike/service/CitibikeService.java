package weizberg.citibike.service;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import weizberg.citibike.json.DataCollection;

public interface CitibikeService {

    @GET("/gbfs/en/station_information.json")
    Single<DataCollection> stationLocation();

    @GET("gbfs/en/station_status.json")
    Single<DataCollection> stationStatus();
}
