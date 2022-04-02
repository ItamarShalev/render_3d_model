package com.playking.lighting;

import com.playking.primitives.Color;
import com.playking.primitives.Double3;

/**
 * Class describe Ambient light.
 */
public class AmbientLight extends Light {

    public AmbientLight() {
        super(Color.BLACK);
    }

    public AmbientLight(Color intensity, Double3 kAmbient) {
        super(intensity.scale(kAmbient));
    }
}
