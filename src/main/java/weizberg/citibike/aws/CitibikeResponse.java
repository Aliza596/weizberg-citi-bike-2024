package weizberg.citibike.aws;

import weizberg.citibike.json.Station;

public class CitibikeResponse {

    private CoordinateLocation from;
    private CoordinateLocation to;
    private StationResponse start;
    private StationResponse end;

    public CitibikeResponse(CoordinateLocation from, CoordinateLocation to, Station start, Station end) {
        this.from = from;
        this.to = to;
        this.start = new StationResponse(start);
        this.end = new StationResponse(end);
    }


    public StationResponse getStart() {
        return start;
    }

    public StationResponse getEnd() {
        return end;
    }
}
