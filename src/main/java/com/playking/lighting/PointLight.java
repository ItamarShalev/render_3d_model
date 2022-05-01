package com.playking.lighting;

import com.playking.primitives.Color;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;

public class PointLight extends Light implements LightSource {
    protected final Point position;
    private double kC;
    private double kL;
    private double kQ;

    /**
     * Default constructor for PointLight class.
     * @param intensity Color of the light.
     * @param position Position of the light.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.kC = 1d;
        this.kL = 0d;
        this.kQ = 0d;
    }

    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Calculates the factor to reduce the intensity of the light.
     * @param point Point to calculate the factor for.
     * @return Factor to reduce the intensity of the light.
     */
    protected double getReduceFactor(Point point) {
        double distance = position.distance(point);
        return kC + kL * distance + kQ * distance * distance;
    }

    @Override
    public Color getIntensity(Point point) {
        return getIntensity().reduce(getReduceFactor(point));
    }

    @Override
    public Vector getL(Point point) {
        if (point == null) {
            return null;
        }
        return point.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
