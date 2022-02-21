package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Vector;

/**
 * Interface for Geometry.
 */
public interface Geometry {

    /**
     * Calculate the normal vector by the point.
     * @param point the point to calculate there the normal
     * @return normal vector from the location of the point on the surface of the geometry
     */
    Vector getNormal(Point point);
}
