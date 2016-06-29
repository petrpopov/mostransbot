package com.innerman.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.innerman.geo.Polyline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 27/06/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StreetDTO implements Serializable {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Number")
    private Long number;

    @JsonProperty("Cells")
    private CellDTO cells;

    public List<Polyline> getPolylines() {

        List<Polyline> res = new ArrayList<>();

        if(cells == null) {
            return res;
        }

        if(cells.getGeoData() == null) {
            return res;
        }

        if(cells.getGeoData().getPolyline() == null) {
            return res;
        }

        return cells.getGeoData().getPolyline();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public CellDTO getCells() {
        return cells;
    }

    public void setCells(CellDTO cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "StreetDTO{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreetDTO streetDTO = (StreetDTO) o;

        return id != null ? id.equals(streetDTO.id) : streetDTO.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
