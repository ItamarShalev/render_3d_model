package com.playking.lighting;

import com.playking.primitives.Color;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;

public class SpotLight extends PointLight {
    private final Vector direction;

    /**
     * Constructor for SpotLight class.
     * @param intensity The intensity of the light.
     * @param position The position of the light.
     * @param direction The direction of the light.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point point) {
        double powerPoint = Math.max(0, direction.dotProduct(getL(point)));
        return getIntensity().scale(powerPoint).reduce(getReduceFactor(point));
    }
}
