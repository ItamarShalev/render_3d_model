package com.playking.adapters.geometries;

import com.playking.adapters.Adapter;
import com.playking.adapters.primitives.ColorAdapter;
import com.playking.adapters.primitives.PointAdapter;
import com.playking.geometries.Sphere;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sphere")
@XmlAccessorType(XmlAccessType.FIELD)
public class SphereAdapter implements Adapter<Sphere> {
    @XmlAttribute
    private String center;

    @XmlAttribute
    private String radius;

    @XmlAttribute
    private String color;

    @Override
    public Sphere build() {
        return (Sphere)new Sphere(PointAdapter.parsePoint(center),
                                  Double.parseDouble(radius)).setEmission(
            ColorAdapter.parseColor(color));
    }
}
