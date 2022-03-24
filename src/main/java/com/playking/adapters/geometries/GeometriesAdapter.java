package com.playking.adapters.geometries;

import com.playking.adapters.Adapter;
import com.playking.geometries.Geometries;
import com.playking.geometries.Intersect;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "geometries")
public class GeometriesAdapter implements Adapter<Geometries> {
    @XmlElements({
        @XmlElement(name = "triangle", type = TriangleAdapter.class),
        @XmlElement(name = "sphere", type = SphereAdapter.class),
        @XmlElement(name = "polygon", type = PolygonAdapter.class),
        @XmlElement(name = "tube", type = TubeAdapter.class),
        @XmlElement(name = "cylinder", type = CylinderAdapter.class),
        @XmlElement(name = "plane", type = PlaneAdapter.class),
    })
    private List<Adapter<Object>> elements;

    @Override
    public Geometries build() {
        Geometries geometries = new Geometries();
        for (Adapter<Object> adapter : elements) {
            geometries.add((Intersect)adapter.build());
        }
        return geometries;
    }
}
