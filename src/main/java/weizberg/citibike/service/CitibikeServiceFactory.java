package weizberg.citibike.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CitibikeServiceFactory {

    private final CitibikeService citibikeService;

    public CitibikeServiceFactory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gbfs.citibikenyc.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        citibikeService = retrofit.create(CitibikeService.class);
    }

    public CitibikeService getCitibikeService() {
        return citibikeService;
    }
}
