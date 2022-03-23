package com.playking.elements;

import com.playking.primitives.Color;
import com.playking.primitives.Double3;

/**
 * Class describe Ambient light.
 */
public class AmbientLight {
    Color intensity;

    public AmbientLight() {
        intensity = Color.BLACK;
    }

    public AmbientLight(Color intensity, Double3 kAmbient) {
        this.intensity = intensity.scale(kAmbient);
    }

    public Color getIntensity() {
        return intensity;
    }
}
