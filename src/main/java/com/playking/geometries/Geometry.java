package com.playking.geometries;

import com.playking.primitives.Color;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;

/**
 * Interface for Geometry.
 */
public abstract class Geometry extends Intersect {

    protected Color emission;

    /**
     * Initialize the emission color to black as a default.
     */
    public Geometry() {
        this.emission = Color.BLACK;
    }

    public Color getEmission() {
        return emission;
    }

    /**
     * Set the emission color.
     * @param emission the emission color
     * @return the geometry itself
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Calculate the normal vector by the point.
     * @param point the point to calculate there the normal
     * @return normal vector from the location of the point on the surface of the geometry
     */
    public abstract Vector getNormal(Point point);
}
