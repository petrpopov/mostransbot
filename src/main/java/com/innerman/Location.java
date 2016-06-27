package com.innerman;

import java.io.Serializable;

/**
 * Created by petrpopov on 27/06/16.
 */
public class Location implements Serializable {

    private static final Double EARTH = 6371007.2;

    private Double lat;
    private Double lng;

    public Location() {
    }

    public Location(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Location toRadians() {

        Location loc = new Location();
        loc.setLat(Math.toRadians(getLat()));
        loc.setLng(Math.toRadians(getLng()));
        return loc;
    }

    public Location toProjection(Location center) {

        Location loc = new Location();
        loc.setLat( getLat()*EARTH );
        loc.setLng( getLng()*Math.cos(center.getLng())*EARTH);
        return loc;
    }

    public Location minus(Location loc) {

        Location res = new Location();
        res.setLat(getLat() - loc.getLat());
        res.setLng(getLng() - loc.getLng());
        return res;
    }

    public Location plus(Location loc) {

        Location res = new Location();
        res.setLat(getLat()+loc.getLat());
        res.setLng(getLng()+loc.getLng());
        return res;
    }

    public Location minus(Double val) {

        Location res = new Location();
        res.setLat(getLat()-val);
        res.setLng(getLng()-val);
        return res;
    }

    public Location multiply(Location loc) {

        Location res = new Location();
        res.setLat(getLat()*loc.getLat());
        res.setLng(getLng()*loc.getLng());
        return res;
    }

    public Location multiply(Double val) {

        Location res = new Location();
        res.setLat(getLat()*val);
        res.setLng(getLng()*val);
        return res;
    }

    public Location divide(Location loc) {

        Location res = new Location();
        res.setLat(getLat()/loc.getLat());
        res.setLng(getLng()/loc.getLng());
        return res;
    }

    public Location square() {
        return this.multiply(this);
    }

    public Location sqrt() {

        Location res = new Location();
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
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (lat != null ? !lat.equals(location.lat) : location.lat != null) return false;
        return lng != null ? lng.equals(location.lng) : location.lng == null;

    }

    @Override
    public int hashCode() {
        int result = lat != null ? lat.hashCode() : 0;
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        return result;
    }
}
