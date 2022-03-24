package com.playking.adapter;

import com.playking.geometries.Sphere;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "triangle")
@XmlAccessorType(XmlAccessType.FIELD)
public class SphereAdapter implements Adapter<Sphere> {
    @XmlAttribute
    String center;
    @XmlAttribute
    String radius;

    @Override
    public Sphere build() {
        return new Sphere(PointAdapter.parsePoint(center), Double.parseDouble(radius));
    }
}
