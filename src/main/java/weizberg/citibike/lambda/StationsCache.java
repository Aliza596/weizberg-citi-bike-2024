package weizberg.citibike.lambda;

import com.google.gson.Gson;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import weizberg.citibike.aws.CitibikeResponse;
import weizberg.citibike.aws.StationResponse;
import weizberg.citibike.json.Data;
import weizberg.citibike.json.DataCollection;
import weizberg.citibike.json.Station;
import weizberg.citibike.service.CitibikeService;
import weizberg.citibike.service.CitibikeServiceFactory;
import weizberg.citibike.service.MergeStationData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class StationsCache {

    private Instant lastModified;
    private final Region region = Region.US_EAST_2;
    private final S3Client s3Client = S3Client.builder()
            .region(region)
            .build();
    private final Gson gson = new Gson();
    private final CitibikeService citibikeService;
    private Data stations;

    public StationsCache(CitibikeService citibikeService) {
        this.citibikeService = citibikeService;
    }

    public Data getStationResponse() {

        if (stations != null && lastModified != null && Duration.between(lastModified, Instant.now()).toHours() < 1) {
            return stations;
        }

        if (stations != null && lastModified != null && Duration.between(lastModified, Instant.now()).toHours() >= 1) {
            stations = retrieveStationInformation();
            lastModified = Instant.now();
            writingToS3();
        } else if (stations == null && recentUpload()) {
            readingFromS3();
        } else {
            stations = retrieveStationInformation();
            lastModified = Instant.now();
            writingToS3();
        }
        return stations;
    }

    public Data retrieveStationInformation() {
        DataCollection response = citibikeService.stationLocation().blockingGet();
        return response.data;
    }

    public boolean recentUpload() {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket("weizberg.citibike")
                .key("stations.json")
                .build();

        try {
            HeadObjectResponse headObjectResponse = s3Client.headObject(headObjectRequest);

            if (Duration.between(lastModified, Instant.now()).toHours() > 0) {
                lastModified = headObjectResponse.lastModified();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void writingToS3() {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("weizberg.citibike")
                    .key("stations.json")
                    .build();
            DataCollection response = citibikeService.stationLocation().blockingGet();
            String content = gson.toJson(response.data);
            s3Client.putObject(putObjectRequest, RequestBody.fromString(content));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readingFromS3() {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("weizberg.citibike")
                    .key("stations.json")
                    .build();

            InputStream in = s3Client.getObject(getObjectRequest);
            stations = gson.fromJson(new InputStreamReader(in), Data.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
