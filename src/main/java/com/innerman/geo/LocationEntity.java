package com.innerman.geo;

import java.io.Serializable;

/**
 * Created by petrpopov on 27/06/16.
 */
public class LocationEntity implements Serializable {

    private static final Double EARTH = 6371007.2;

    private Double lat;
    private Double lng;

    public LocationEntity() {
    }

    public LocationEntity(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LocationEntity toRadians() {

        LocationEntity loc = new LocationEntity();
        loc.setLat(Math.toRadians(getLat()));
        loc.setLng(Math.toRadians(getLng()));
        return loc;
    }

    public LocationEntity toProjection(LocationEntity center) {

        LocationEntity loc = new LocationEntity();
        loc.setLat( getLat()*EARTH );
        loc.setLng( getLng()*Math.cos(center.getLng())*EARTH);
        return loc;
    }

    public LocationEntity minus(LocationEntity loc) {

        LocationEntity res = new LocationEntity();
        res.setLat(getLat() - loc.getLat());
        res.setLng(getLng() - loc.getLng());
        return res;
    }

    public LocationEntity plus(LocationEntity loc) {

        LocationEntity res = new LocationEntity();
        res.setLat(getLat()+loc.getLat());
        res.setLng(getLng()+loc.getLng());
        return res;
    }

    public LocationEntity minus(Double val) {

        LocationEntity res = new LocationEntity();
        res.setLat(getLat()-val);
        res.setLng(getLng()-val);
        return res;
    }

    public LocationEntity multiply(LocationEntity loc) {

        LocationEntity res = new LocationEntity();
        res.setLat(getLat()*loc.getLat());
        res.setLng(getLng()*loc.getLng());
        return res;
    }

    public LocationEntity multiply(Double val) {

        LocationEntity res = new LocationEntity();
        res.setLat(getLat()*val);
        res.setLng(getLng()*val);
        return res;
    }

    public LocationEntity divide(LocationEntity loc) {

        LocationEntity res = new LocationEntity();
        res.setLat(getLat()/loc.getLat());
        res.setLng(getLng()/loc.getLng());
        return res;
    }

    public LocationEntity square() {
        return this.multiply(this);
    }

    public LocationEntity sqrt() {

        LocationEntity res = new LocationEntity();
        res.setLat( Math.sqrt(getLat()));
        res.setLng( Math.sqrt(getLng()));
        return res;
    }

    public boolean sqrtable() {

        if(getLat() < 0 && getLng() < 0) {
            return false;
        }
        return true;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "LocationEntity{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationEntity locationEntity = (LocationEntity) o;

        if (lat != null ? !lat.equals(locationEntity.lat) : locationEntity.lat != null) return false;
        return lng != null ? lng.equals(locationEntity.lng) : locationEntity.lng == null;

    }

    @Override
    public int hashCode() {
        int result = lat != null ? lat.hashCode() : 0;
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        return result;
    }
}
