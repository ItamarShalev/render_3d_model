package com.playking.lighting;

import com.playking.primitives.Color;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;

/**
 * Defines a light source.
 */
public interface LightSource {

    /**
     * Returns the color of the light source by the given point.
     * @param point The point to calculate the color by.
     * @return The color of the light source by the given point.
     */
    Color getIntensity(Point point);

    /**
     * Returns the direction of the light source.
     * @param point The point to calculate the direction by.
     * @return The direction of the light source.
     */
    Vector getL(Point point);


    double getDistance(Point point);
}
