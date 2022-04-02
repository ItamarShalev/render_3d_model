package com.playking.adapters.geometries;

import com.playking.adapters.Adapter;
import com.playking.adapters.primitives.ColorAdapter;
import com.playking.adapters.primitives.PointAdapter;
import com.playking.geometries.Triangle;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "triangle")
@XmlAccessorType(XmlAccessType.FIELD)
public class TriangleAdapter implements Adapter<Triangle> {
    @XmlAttribute
    private String p1;

    @XmlAttribute
    private String p2;

    @XmlAttribute
    private String p3;

    @XmlAttribute
    private String color;

    @Override
    public Triangle build() {
        return (Triangle)new Triangle(PointAdapter.parsePoint(p1), PointAdapter.parsePoint(p2),
                                      PointAdapter.parsePoint(p3)).setEmission(
            ColorAdapter.parseColor(color));
    }
}
