package com.playking.adapters.geometries;

import com.playking.adapters.Adapter;
import com.playking.adapters.primitives.Double3Adapter;
import com.playking.adapters.primitives.PointAdapter;
import com.playking.geometries.Plane;
import com.playking.primitives.Vector;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plane")
public class PlaneAdapter implements Adapter<Plane> {

    @XmlAttribute
    private String p0;

    @XmlAttribute
    private String normal;

    @Override
    public Plane build() {
        return new Plane(PointAdapter.parsePoint(p0),
                         new Vector(Double3Adapter.parseDouble3(normal)));
    }
}
