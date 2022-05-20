package com.adapters.geometries;

import com.adapters.Adapter;
import com.geometries.Sphere;
import com.adapters.primitives.ColorAdapter;
import com.adapters.primitives.PointAdapter;
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
                                  Double.parseDouble(radius)).setEmission(ColorAdapter.parseColor(
            color));
    }
}
