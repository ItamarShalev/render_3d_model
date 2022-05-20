package com.adapters.geometries;

import com.adapters.Adapter;
import com.geometries.Cylinder;
import com.geometries.Tube;
import com.adapters.primitives.ColorAdapter;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cylinder")
public class CylinderAdapter implements Adapter<Cylinder> {
    @XmlElement
    private TubeAdapter tube;

    @XmlAttribute
    private String height;

    @XmlAttribute
    private String color;

    @Override
    public Cylinder build() {
        Tube tube = this.tube.build();
        return (Cylinder)new Cylinder(tube.getAxisRay(),
                                      tube.getRadius(),
                                      Double.parseDouble(height)).setEmission(ColorAdapter.parseColor(
            color));
    }
}
