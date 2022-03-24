package com.playking.adapters.geometries;

import com.playking.adapters.Adapter;
import com.playking.adapters.primitives.RayAdapter;
import com.playking.geometries.Tube;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tube")
public class TubeAdapter implements Adapter<Tube> {
    @XmlElement
    private RayAdapter ray;

    @XmlElement
    private String radius;

    @Override
    public Tube build() {
        return new Tube(ray.build(), Double.parseDouble(radius));
    }
}
