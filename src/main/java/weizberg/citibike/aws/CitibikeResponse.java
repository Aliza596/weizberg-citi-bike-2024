package weizberg.citibike.aws;

import weizberg.citibike.json.Station;

public class CitibikeResponse {

    private final CoordinateLocation from;
    private final CoordinateLocation to;
    private final StationResponse start;
    private final StationResponse end;

    public CitibikeResponse(CoordinateLocation from, CoordinateLocation to, Station start, Station end) {
        this.from = from;
        this.to = to;
        this.start = new StationResponse(start);
        this.end = new StationResponse(end);
    }

    public CoordinateLocation getFrom() {
        return from;
    }

    public CoordinateLocation getTo() {
        return to;
    }

    public StationResponse getStart() {
        return start;
    }

    public StationResponse getEnd() {
        return end;
    }
}
