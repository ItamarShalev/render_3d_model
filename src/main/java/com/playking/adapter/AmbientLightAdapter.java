package com.playking.adapter;

import com.playking.elements.AmbientLight;
import com.playking.primitives.Double3;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ambient-light")
public class AmbientLightAdapter implements Adapter<AmbientLight> {
    @XmlAttribute
    String color;

    @Override
    public AmbientLight build() {
        return new AmbientLight(ColorAdapter.parseColor(color), new Double3(1, 1, 1));
    }
}
