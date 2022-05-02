package com.playking.adapters.lighting;

import com.playking.adapters.Adapter;
import com.playking.adapters.primitives.ColorAdapter;
import com.playking.adapters.primitives.Double3Adapter;
import com.playking.adapters.primitives.PointAdapter;
import com.playking.lighting.SpotLight;
import com.playking.primitives.Color;
import com.playking.primitives.Vector;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "spot-light")
public class SpotLightAdapter implements Adapter<SpotLight> {

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

    @XmlAttribute
    private String direction;

    public SpotLightAdapter() {
        kc = 1;
        kl = 0;
        kq = 0;
    }

    @Override
    public SpotLight build() {
        Color color = ColorAdapter.parseColor(intensity);
        SpotLight spotLight = new SpotLight(color,
            PointAdapter.parsePoint(position),
            new Vector(Double3Adapter.parseDouble3(direction)));
        spotLight.setKC(kc).setKL(kl).setKQ(kq);
        return spotLight;
    }
}
