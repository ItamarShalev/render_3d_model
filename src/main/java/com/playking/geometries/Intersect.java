package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import java.util.List;

/**
 * Interface of intersection of light rays with objects.
 */
public interface Intersect {

    /**
     * Find all Intersections points from the ray.
     * @param ray MUST be not null, The ray tested at the intersection of the object
     * @return List of points that intersection with the object
     */
    List<Point> findIntersections(Ray ray);
}
