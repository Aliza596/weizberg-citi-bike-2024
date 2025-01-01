package weizberg.citibike;


import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import weizberg.citibike.json.DataCollection;
import weizberg.citibike.json.Station;


import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MergeStationData {
    CitibikeService citibikeService;


    public MergeStationData() {
        CitibikeServiceFactory factory = new CitibikeServiceFactory();
        citibikeService = factory.getCitibikeService();
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


    public Map<String, Station> getStationsMap() {
        try {
            return mergeData().timeout(10, TimeUnit.SECONDS).blockingGet().getStationsMap();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}

