package com.playking.adapters.geometries;

import com.playking.adapters.Adapter;
import com.playking.adapters.primitives.PointAdapter;
import com.playking.geometries.Sphere;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "triangle")
@XmlAccessorType(XmlAccessType.FIELD)
public class SphereAdapter implements Adapter<Sphere> {
    @XmlAttribute
    private String center;
    @XmlAttribute
    private String radius;

    @Override
    public Sphere build() {
        return new Sphere(PointAdapter.parsePoint(center), Double.parseDouble(radius));
    }
}
