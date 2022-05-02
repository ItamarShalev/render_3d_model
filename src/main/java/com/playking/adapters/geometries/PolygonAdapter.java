package com.playking.adapters.geometries;

import com.playking.adapters.Adapter;
import com.playking.adapters.primitives.ColorAdapter;
import com.playking.adapters.primitives.PointAdapter;
import com.playking.geometries.Polygon;
import com.playking.primitives.Point;
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
