package com.playking.adapter;

import com.playking.primitives.Double3;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "double3")
public class Double3Adapter implements Adapter<Double3> {
    @XmlElement
    private String data;

    public Double3Adapter() {
    }

    public Double3Adapter(String data) {
        this.data = data;
    }

    public static Double3 parseDouble3(String data) {
        String[] values = data.split(" ");
        double d1 = Double.parseDouble(values[0]);
        double d2 = Double.parseDouble(values[1]);
        double d3 = Double.parseDouble(values[2]);
        return new Double3(d1, d2, d3);
    }

    @Override
    public Double3 build() {
        return parseDouble3(data);
    }
}
