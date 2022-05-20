package com.adapters.geometries;

import com.adapters.Adapter;
import com.geometries.Polygon;
import com.adapters.primitives.ColorAdapter;
import com.adapters.primitives.PointAdapter;
import com.primitives.Point;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "polygon")
public class PolygonAdapter implements Adapter<Polygon> {
    @XmlElements({
        @XmlElement(name = "point", type = PointAdapter.class)
    })
    private List<PointAdapter> points;

    @XmlAttribute
    private String color;

    @Override
    public Polygon build() {
        Point[] pointsArray;
        pointsArray = points.stream().map(PointAdapter::build).toArray(Point[]::new);
        return (Polygon)new Polygon(pointsArray).setEmission(ColorAdapter.parseColor(color));
    }
}
