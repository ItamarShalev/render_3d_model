package com.playking.lighting;

import static com.playking.primitives.Util.alignZero;

import com.playking.primitives.Color;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;

public class SpotLight extends PointLight {
    private final Vector direction;
    private int narrowBeam;

    /**
     * Constructor for SpotLight class.
     * @param intensity The intensity of the light.
     * @param position The position of the light.
     * @param direction The direction of the light.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
        narrowBeam = 1;
    }

    @Override
    public Color getIntensity(Point point) {
        double projection = alignZero(direction.dotProduct(getL(point)));
        Color result = Color.BLACK;
        if (projection > 0) {
            projection = narrowBeam != 1 ? Math.pow(projection, narrowBeam) : projection;
            result = super.getIntensity(point).scale(projection);
        }
        return result;
    }

    /**
     * Sets the narrow beam of the light.
     * As much as the narrow beam is bigger, the light will be more focused.
     * @param narrowBeam The narrow beam of the light.
     * @return The light.
     */
    public SpotLight setNarrowBeam(int narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}
