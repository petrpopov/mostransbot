package com.innerman.service;

import com.innerman.geo.Line;
import com.innerman.geo.Location;
import com.innerman.geo.Polyline;
import org.springframework.stereotype.Service;

/**
 * Created by petrpopov on 27/06/16.
 */

@Service
public class GeoMeter {

    private static final double degr = Math.PI * 2 / 360;
    private static final double radian = 1 / degr;

    public boolean intersects(Location loc, Polyline line, Double radius) {

        for(Line l : line.getLines()) {
            if(intersects(loc, l, radius)) {
                return true;
            }
        }

        return false;
    }

    //https://gis.stackexchange.com/questions/36841/line-intersection-with-circle-on-a-sphere-globe-or-earth
    public boolean intersects(Location loc, Line line, Double radius) {

        Location center = loc.toRadians();

        Location a0 = line.getStart().toRadians();
        Location b0 = line.getEnd().toRadians();

        Location A = a0.toProjection(center);
        Location B = b0.toProjection(center);
        Location C = center.toProjection(center);

        Location v = A.minus(C);
        Location u = B.minus(A);

        //square equation coefs
        Location alpha = u.square();
        Location beta = u.multiply(v);
        Location gamma = v.square().minus(radius*radius);

        Location t = getSquareEquationResults(alpha, beta, gamma);
        if(t == null) {
            return false;
        }

        Location intersect = a0.plus(b0.minus(a0).divide(t)).multiply(radian);
        System.out.println(intersect);
        return true;
    }

    private Location getSquareEquationResults(Location alpha, Location beta, Location gamma) {

        Location D = beta.square().minus(alpha.multiply(4.0).multiply(gamma));
        if(!D.sqrtable()) {
            return null;
        }

        Location res1 = beta.multiply(-1.0).plus(D.sqrt()).divide(alpha.multiply(2.0));
        Location res2 = beta.multiply(-1.0).minus(D.sqrt()).divide(alpha.multiply(2.0));
        return res1;
    }
}
