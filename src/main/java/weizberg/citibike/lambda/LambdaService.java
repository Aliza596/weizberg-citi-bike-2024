package weizberg.citibike.lambda;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import weizberg.citibike.aws.CitibikeRequest;
import weizberg.citibike.aws.CitibikeResponse;

public interface LambdaService {

    @POST("/")
    Single<CitibikeResponse> callLambda(@Body CitibikeRequest request);

}
