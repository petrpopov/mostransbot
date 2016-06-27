package com.innerman.geo;

import java.io.Serializable;

/**
 * Created by petrpopov on 27/06/16.
 */
public class Line implements Serializable {

    private Location start;
    private Location end;

    public Line() {
    }

    public Line(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
    }
}
