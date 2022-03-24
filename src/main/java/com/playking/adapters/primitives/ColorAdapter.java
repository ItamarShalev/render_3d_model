package com.playking.adapters.primitives;

import com.playking.adapters.Adapter;
import com.playking.primitives.Color;
import com.playking.primitives.Double3;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "color")
public class ColorAdapter implements Adapter<Color> {
    @XmlAttribute
    private String data;

    public static Color parseColor(String data) {
        Double3 double3 = Double3Adapter.parseDouble3(data);
        return new Color(double3.d1, double3.d2, double3.d3);
    }

    @Override
    public Color build() {
        return parseColor(data);
    }
}
