package com.adapters.geometries;

import com.adapters.Adapter;
import com.geometries.Geometries;
import com.geometries.Intersect;
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
        elements.stream().map(adapter -> (Intersect)adapter.build()).forEach(geometries::add);
        return geometries;
    }
}
