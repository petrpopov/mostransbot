package com.innerman.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innerman.geo.Polyline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 27/06/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoDataDTO implements Serializable {

    private String type;
    private List<Object> coordinates;
    private List<Polyline> polylines = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public void createPolyline() {

        if(coordinates == null || coordinates.isEmpty() ) {
            return;
        }

        for (Object coordinate : coordinates) {
            if(!(coordinate instanceof List)) {
                continue;
            }

            List c1 = (List) coordinate;
            if(c1.isEmpty()) {
                continue;
            }

            Polyline line = new Polyline();
            for (Object points : c1) {
                if(!(points instanceof List)) {
                    continue;
                }

                List<Double> pointList = (List<Double>) points;
                line.addPoint(pointList.get(1), pointList.get(0));
            }
            polylines.add(line);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Object> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Object> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Polyline> getPolyline() {
        return polylines;
    }

    public void setPolyline(List<Polyline> polyline) {
        this.polylines = polyline;
    }
}
