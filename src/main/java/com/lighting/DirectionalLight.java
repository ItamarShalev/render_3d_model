package com.lighting;

import com.primitives.Color;
import com.primitives.Point;
import com.primitives.Vector;

/**
 * Defines a directional light source.
 */
public class DirectionalLight extends Light implements LightSource {

    private final Vector direction;

    /**
     * Constructs a directional light source.
     * @param intensity The intensity of the light source.
     * @param direction The direction of the light source.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point point) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
