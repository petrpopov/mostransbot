package com.innerman.service;

import com.innerman.geo.Line;
import com.innerman.geo.LocationEntity;
import com.innerman.geo.Polyline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by petrpopov on 27/06/16.
 */

@Service
public class GeoMeter {

    private static final double degr = Math.PI * 2 / 360;
    private static final double radian = 1 / degr;

    @Value("${search.radius}")
    private Double defaultRadius;

    public boolean intersects(LocationEntity loc, Polyline line) {
        return intersects(loc, line, defaultRadius);
    }

    public boolean intersects(LocationEntity loc, Polyline line, Double radius) {

        for(Line l : line.getLines()) {
            if(intersects(loc, l, radius)) {
                return true;
            }
        }

        return false;
    }

    //https://gis.stackexchange.com/questions/36841/line-intersection-with-circle-on-a-sphere-globe-or-earth
    public boolean intersects(LocationEntity loc, Line line, Double radius) {

        LocationEntity center = loc.toRadians();

        LocationEntity a0 = line.getStart().toRadians();
        LocationEntity b0 = line.getEnd().toRadians();

        LocationEntity A = a0.toProjection(center);
        LocationEntity B = b0.toProjection(center);
        LocationEntity C = center.toProjection(center);

        LocationEntity v = A.minus(C);
        LocationEntity u = B.minus(A);

        //square equation coefs
        LocationEntity alpha = u.square();
        LocationEntity beta = u.multiply(v);
        LocationEntity gamma = v.square().minus(radius*radius);

        LocationEntity t = getSquareEquationResults(alpha, beta, gamma);
        if(t == null) {
            return false;
        }

        LocationEntity intersect = a0.plus(b0.minus(a0).divide(t)).multiply(radian);
        System.out.println(intersect);
        return true;
    }

    private LocationEntity getSquareEquationResults(LocationEntity alpha, LocationEntity beta, LocationEntity gamma) {

        LocationEntity D = beta.square().minus(alpha.multiply(4.0).multiply(gamma));
        if(!D.sqrtable()) {
            return null;
        }

        LocationEntity res1 = beta.multiply(-1.0).plus(D.sqrt()).divide(alpha.multiply(2.0));
        LocationEntity res2 = beta.multiply(-1.0).minus(D.sqrt()).divide(alpha.multiply(2.0));
        return res1;
    }
}
