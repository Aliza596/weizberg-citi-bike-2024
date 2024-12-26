package weizberg.citibike;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import weizberg.citibike.json.DataCollection;
import weizberg.citibike.json.Stations;

import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    public Single<DataCollection> mergeData() {
        Single<DataCollection> stationInfo = citibikeService.stationLocation();
        Single<DataCollection> stationStatus = citibikeService.stationStatus();

        return Single.zip(stationInfo, stationStatus, (info, status) -> {
                    info.mergeData(status);
                    return info;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single());
    }

    public Map<String, Stations> getStationsMap() {
        try {
            return mergeData().timeout(10, TimeUnit.SECONDS).blockingGet().getStationsMap();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
