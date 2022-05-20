package com.adapters.lighting;

import com.adapters.Adapter;
import com.lighting.DirectionalLight;
import com.adapters.primitives.ColorAdapter;
import com.adapters.primitives.Double3Adapter;
import com.primitives.Vector;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "directional-light")
public class DirectionalLightAdapter implements Adapter<DirectionalLight> {

    @XmlAttribute
    private String intensity;

    @XmlAttribute
    private String direction;


    @Override
    public DirectionalLight build() {
        return new DirectionalLight(ColorAdapter.parseColor(intensity),
                                    new Vector(Double3Adapter.parseDouble3(direction)));
    }
}
