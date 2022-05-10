package com.playking.geometries;

import com.playking.primitives.Ray;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = geometries
            .stream()
            .map(item -> item.findGeoIntersectionsHelper(ray, maxDistance))
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .filter(geoPoint -> ray.getP0().distance(geoPoint.point) <= maxDistance)
            .collect(Collectors.toList());
        return result.isEmpty() ? null : result;
    }
}
