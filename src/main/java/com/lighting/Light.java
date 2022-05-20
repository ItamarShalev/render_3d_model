package com.lighting;


import com.primitives.Color;

/**
 * Defines a light source.
 */
abstract class Light {

    private final Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
