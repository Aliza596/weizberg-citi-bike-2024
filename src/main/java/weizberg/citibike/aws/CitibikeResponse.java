package weizberg.citibike.aws;

import weizberg.citibike.json.Station;

public class CitibikeResponse {

    private CoordinateLocation from;
    private CoordinateLocation to;
    private Station start;
    private Station end;

    public CitibikeResponse(CoordinateLocation from, CoordinateLocation to, Station start, Station end) {
        this.from = from;
        this.to = to;
        this.start = start;
        this.end = end;
    }


    public Station getStart() {
        return start;
    }

    public Station getEnd() {
        return end;
    }
}
