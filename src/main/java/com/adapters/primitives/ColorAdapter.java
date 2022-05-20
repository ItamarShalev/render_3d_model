package com.adapters.primitives;

import com.adapters.Adapter;
import com.primitives.Color;
import com.primitives.Double3;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "color")
public class ColorAdapter implements Adapter<Color> {

    public static final String DEFAULT_COLOR = "0 0 0";

    @XmlAttribute
    private final String color;

    public ColorAdapter() {
        this.color = DEFAULT_COLOR;
    }


    public ColorAdapter(String color) {
        this.color = color;
    }

    public static Color parseColor(String data) {
        data = data == null ? DEFAULT_COLOR : data;
        Double3 double3 = Double3Adapter.parseDouble3(data);
        return new Color(double3.d1, double3.d2, double3.d3);
    }

    @Override
    public Color build() {
        return parseColor(color);
    }
}
