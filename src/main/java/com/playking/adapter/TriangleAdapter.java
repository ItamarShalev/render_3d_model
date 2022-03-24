package com.playking.adapter;

import com.playking.geometries.Triangle;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "triangle")
@XmlAccessorType(XmlAccessType.FIELD)
public class TriangleAdapter implements Adapter<Triangle> {
    @XmlAttribute
    String p1;
    @XmlAttribute
    String p2;
    @XmlAttribute
    String p3;

    @Override
    public Triangle build() {
        return new Triangle(PointAdapter.parsePoint(p1), PointAdapter.parsePoint(p2),
                            PointAdapter.parsePoint(p3));
    }
}
