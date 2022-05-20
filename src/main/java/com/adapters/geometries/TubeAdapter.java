package com.adapters.geometries;

import com.adapters.Adapter;
import com.geometries.Tube;
import com.adapters.primitives.ColorAdapter;
import com.adapters.primitives.RayAdapter;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tube")
public class TubeAdapter implements Adapter<Tube> {
    @XmlElement
    private RayAdapter ray;

    @XmlElement
    private String radius;

    @XmlAttribute
    private String color;

    @Override
    public Tube build() {
        ColorAdapter colorAdapter = new ColorAdapter(color);
        return (Tube)new Tube(ray.build(),
                              Double.parseDouble(radius)).setEmission(colorAdapter.build());
    }
}
