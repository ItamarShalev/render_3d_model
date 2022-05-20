package com.adapters.primitives;

import com.adapters.Adapter;
import com.primitives.Point;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "point")
public class PointAdapter implements Adapter<Point> {
    @XmlAttribute
    private String p;

    public static Point parsePoint(String data) {
        return new Point(Double3Adapter.parseDouble3(data));
    }

    @Override
    public Point build() {
        return parsePoint(p);
    }
}
