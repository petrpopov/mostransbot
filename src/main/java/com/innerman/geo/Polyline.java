package com.innerman.geo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 27/06/16.
 */
public class Polyline implements Serializable {

    private List<Location> points = new ArrayList<>();

    public void addPoint(Double lat, Double lng) {
        points.add(new Location(lat, lng));
    }

    public List<Line> getLines() {

        List<Line> res = new ArrayList<>();

        for(int i = 1; i < points.size(); i++) {
            res.add(new Line(points.get(i-1), points.get(i)));
        }

        return res;
    }

    public List<Location> getPoints() {
        return points;
    }

    public void setPoints(List<Location> points) {
        this.points = points;
    }
}
