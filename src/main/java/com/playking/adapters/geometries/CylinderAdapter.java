package com.playking.adapters.geometries;

import com.playking.adapters.Adapter;
import com.playking.geometries.Cylinder;
import com.playking.geometries.Tube;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cylinder")
public class CylinderAdapter implements Adapter<Cylinder> {
    @XmlElement
    private TubeAdapter tube;

    @XmlAttribute
    private String height;

    @Override
    public Cylinder build() {
        Tube tube = this.tube.build();
        return new Cylinder(tube.getAxisRay(), tube.getRadius(), Double.parseDouble(height));
    }
}
