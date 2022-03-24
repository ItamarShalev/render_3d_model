package com.playking.elements;

import com.playking.primitives.Color;
import com.playking.primitives.Double3;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class describe Ambient light.
 */
@XmlRootElement(name = "ambient-light")
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
