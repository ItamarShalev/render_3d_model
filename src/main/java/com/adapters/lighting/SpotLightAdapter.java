package com.adapters.lighting;

import com.adapters.Adapter;
import com.lighting.SpotLight;
import com.adapters.primitives.ColorAdapter;
import com.adapters.primitives.Double3Adapter;
import com.adapters.primitives.PointAdapter;
import com.primitives.Color;
import com.primitives.Vector;
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
