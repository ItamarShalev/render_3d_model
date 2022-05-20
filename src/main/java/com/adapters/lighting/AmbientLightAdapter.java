package com.adapters.lighting;

import com.lighting.AmbientLight;
import com.adapters.Adapter;
import com.adapters.primitives.ColorAdapter;
import com.primitives.Double3;
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
