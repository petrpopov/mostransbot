package com.innerman.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

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
}
