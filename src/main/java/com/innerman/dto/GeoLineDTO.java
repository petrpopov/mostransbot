package com.innerman.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 01/07/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLineDTO implements Serializable {

    private static String TYPE = "LineString";
    private List<PointDTO> coordinates = new ArrayList<>();

    public static String getTYPE() {
        return TYPE;
    }

    public static void setTYPE(String TYPE) {
        GeoLineDTO.TYPE = TYPE;
    }

    public static String getType() {
        return TYPE;
    }
}
