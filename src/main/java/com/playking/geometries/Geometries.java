package com.playking.geometries;

import com.playking.primitives.Ray;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Composite class for all geometries.
 */
public class Geometries extends Intersect {

    private final List<Intersect> geometries;

    public Geometries() {
        geometries = new LinkedList<>();
    }

    public Geometries(Intersect... geometries) {
        this.geometries = new LinkedList<>(Arrays.asList(geometries));
    }

    /**
     * Add more geometries to the list.
     * @param geometries Geometries to add to current list
     */
    public void add(Intersect... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = new LinkedList<>();

        for (Intersect item : geometries) {
            List<GeoPoint> itemIntersectionPoints = item.findGeoIntersections(ray);
            if (itemIntersectionPoints != null) {
                result.addAll(itemIntersectionPoints);
            }
        }
        result = result.isEmpty() ? null : result;

        return result;
    }
}
