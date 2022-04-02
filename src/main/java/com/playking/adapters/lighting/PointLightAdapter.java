package com.playking.adapters.lighting;


import com.playking.adapters.Adapter;
import com.playking.adapters.primitives.ColorAdapter;
import com.playking.adapters.primitives.PointAdapter;
import com.playking.lighting.PointLight;
import com.playking.primitives.Color;
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

