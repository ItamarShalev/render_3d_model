package com.adapters.lighting;


import com.adapters.Adapter;
import com.lighting.PointLight;
import com.adapters.primitives.ColorAdapter;
import com.adapters.primitives.PointAdapter;
import com.primitives.Color;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "point-light")
public class PointLightAdapter implements Adapter<PointLight> {

    @XmlAttribute
    private final double kc;

    @XmlAttribute
    private final double kl;

    @XmlAttribute
    private final double kq;

    @XmlAttribute
    private String intensity;

    @XmlAttribute
    private String position;


    public PointLightAdapter() {
        kc = 1;
        kl = 0;
        kq = 0;
    }

    @Override
    public PointLight build() {
        Color color = ColorAdapter.parseColor(intensity);
        PointLight pointLight = new PointLight(color, PointAdapter.parsePoint(position));
        pointLight.setKC(kc).setKL(kl).setKQ(kq);
        return pointLight;
    }
}

