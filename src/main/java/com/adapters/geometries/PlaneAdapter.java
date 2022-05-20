package com.adapters.geometries;

import com.adapters.Adapter;
import com.geometries.Plane;
import com.adapters.primitives.ColorAdapter;
import com.adapters.primitives.Double3Adapter;
import com.adapters.primitives.PointAdapter;
import com.primitives.Vector;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plane")
public class PlaneAdapter implements Adapter<Plane> {

    @XmlAttribute
    private String p0;

    @XmlAttribute
    private String normal;

    @XmlAttribute
    private String color;

    @Override
    public Plane build() {
        return (Plane)new Plane(PointAdapter.parsePoint(p0),
                                new Vector(Double3Adapter.parseDouble3(normal))).setEmission(
            ColorAdapter.parseColor(color));
    }
}
