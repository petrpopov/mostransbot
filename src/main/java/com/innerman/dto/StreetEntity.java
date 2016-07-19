package com.innerman.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created by petrpopov on 27/06/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "streets")
public class StreetEntity implements Serializable {

    @Id
    private String mongoId;

    @JsonProperty("Id")
    @Field("inner_id")
    private String id;

    @JsonProperty("Number")
    @Indexed(unique = true)
    private Long number;

    @JsonProperty("Cells")
    private CellDTO cells;
//
//    @Transient
//    public List<Polyline> getPolylines() {
//
//        List<Polyline> res = new ArrayList<>();
//
//        if(cells == null) {
//            return res;
//        }
//
//        if(cells.getGeoData() == null) {
//            return res;
//        }
//
//        if(cells.getGeoData().getPolyline() == null) {
//            return res;
//        }
//
//        return cells.getGeoData().getPolyline();
//    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
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
        return "StreetEntity{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreetEntity streetEntity = (StreetEntity) o;

        return id != null ? id.equals(streetEntity.id) : streetEntity.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
