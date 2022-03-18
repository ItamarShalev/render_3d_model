package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Composite class for all geometries.
 */
public class Geometries implements Intersect {

    private final List<Intersect> geometries;

    public Geometries() {
        geometries = new LinkedList<>();
    }

    public Geometries(Intersect... geometries) {
        this.geometries = new LinkedList<>(Arrays.asList(geometries));
    }

    /**
     * func to add new items.
     * @param geometries Geometries to add to current list
     */
    private void add(Intersect... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = new LinkedList<>();

        for (Intersect item : geometries) {
            List<Point> itemIntersectionPoints = item.findIntersections(ray);
            if (itemIntersectionPoints != null) {
                result.addAll(itemIntersectionPoints);
            }
        }
        result = result.isEmpty() ? null : result;

        return result;
    }
}
