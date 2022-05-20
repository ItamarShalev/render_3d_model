package com.adapters.primitives;

import com.adapters.Adapter;
import com.primitives.Ray;
import com.primitives.Vector;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ray")
public class RayAdapter implements Adapter<Ray> {
    @XmlAttribute
    private String p0;

    @XmlAttribute
    private String dir;

    public static Ray parseRay(String p0, String dir) {
        return new Ray(PointAdapter.parsePoint(p0), new Vector(Double3Adapter.parseDouble3(dir)));
    }

    @Override
    public Ray build() {
        return parseRay(p0, dir);
    }
}
