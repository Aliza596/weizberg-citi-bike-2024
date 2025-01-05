package weizberg.citibike.lambda;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import weizberg.citibike.aws.CitibikeRequest;
import weizberg.citibike.aws.CitibikeResponse;

import java.io.IOException;

public class LambdaServiceFactory {

    private final LambdaService lambdaService;

    public  LambdaServiceFactory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://b4lmwkya4tdrncijpjhxgtq5su0uyelt.lambda-url.us-east-2.on.aws/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        this.lambdaService = retrofit.create(LambdaService.class);
    }

    public LambdaService getLambdaService() {
        return lambdaService;
    }

}
