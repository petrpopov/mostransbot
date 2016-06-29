package com.innerman.geo;

import java.io.Serializable;

/**
 * Created by petrpopov on 27/06/16.
 */
public class Line implements Serializable {

    private LocationEntity start;
    private LocationEntity end;

    public Line() {
    }

    public Line(LocationEntity start, LocationEntity end) {
        this.start = start;
        this.end = end;
    }

    public LocationEntity getStart() {
        return start;
    }

    public void setStart(LocationEntity start) {
        this.start = start;
    }

    public LocationEntity getEnd() {
        return end;
    }

    public void setEnd(LocationEntity end) {
        this.end = end;
    }
}
