package com.playking.adapters.geometries;

import com.playking.adapters.Adapter;
import com.playking.adapters.primitives.PointAdapter;
import com.playking.geometries.Polygon;
import com.playking.primitives.Point;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "polygon")
public class PolygonAdapter implements Adapter<Polygon> {
    @XmlElements({
        @XmlElement(name = "point", type = PointAdapter.class)
    })
    private List<PointAdapter> points;

    @Override
    public Polygon build() {
        List<Point> pointList = new LinkedList<>();
        Point[] pointsArray = new Point[points.size()];
        for (PointAdapter point : points) {
            pointList.add(point.build());
        }
        pointList.toArray(pointsArray);
        return new Polygon(pointsArray);
    }
}
