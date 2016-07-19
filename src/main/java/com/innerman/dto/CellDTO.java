package com.innerman.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiLineString;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by petrpopov on 27/06/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CellDTO implements Serializable {

    @JsonProperty("global_id")
    private Long globalId;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("AdmArea")
    private List<String> admArea;

    @JsonProperty("District")
    private List<String> district;

    @JsonProperty("Event_type")
    private String eventType;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Requester")
    private String requester;

    @JsonDeserialize(using = JsonDateDeserializer.class, as = Date.class)
    @JsonProperty("TS_from")
    private Date tsFrom;

    @JsonDeserialize(using = JsonDateDeserializer.class, as = Date.class)
    @JsonProperty("TS_to")
    private Date tsTo;

    @JsonProperty("Lanes_closed")
    private String lanesClosed;

    @JsonProperty("Descr")
    private String descr;

    @JsonProperty("geoData")
    @GeoSpatialIndexed
    private GeoJsonMultiLineString geoData;

    public Long getGlobalId() {
        return globalId;
    }

    public void setGlobalId(Long globalId) {
        this.globalId = globalId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getAdmArea() {
        return admArea;
    }

    public void setAdmArea(List<String> admArea) {
        this.admArea = admArea;
    }

    public List<String> getDistrict() {
        return district;
    }

    public void setDistrict(List<String> district) {
        this.district = district;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public Date getTsFrom() {
        return tsFrom;
    }

    public void setTsFrom(Date tsFrom) {
        this.tsFrom = tsFrom;
    }

    public Date getTsTo() {
        return tsTo;
    }

    public void setTsTo(Date tsTo) {
        this.tsTo = tsTo;
    }

    public String getLanesClosed() {
        return lanesClosed;
    }

    public void setLanesClosed(String lanesClosed) {
        this.lanesClosed = lanesClosed;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public GeoJsonMultiLineString getGeoData() {
        return geoData;
    }

    public void setGeoData(GeoJsonMultiLineString geoData) {
        this.geoData = geoData;
    }
}
