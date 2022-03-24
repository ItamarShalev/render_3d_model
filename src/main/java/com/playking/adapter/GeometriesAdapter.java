package com.playking.adapter;

import com.playking.geometries.Geometries;
import com.playking.geometries.Intersect;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class GeometriesAdapter implements Adapter<Geometries> {
    @XmlElements({
        @XmlElement(name = "triangle", type = TriangleAdapter.class),
        @XmlElement(name = "sphere", type = SphereAdapter.class),
    })
    public List elements;

    @Override
    public Geometries build() {
        Geometries geometries = new Geometries();
        Adapter<Intersect> adapter;
        for (Object obj : elements) {
            adapter = (Adapter<Intersect>)obj;
            geometries.add(adapter.build());
        }
        return geometries;
    }
}
