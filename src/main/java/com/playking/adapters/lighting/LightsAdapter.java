package com.playking.adapters.lighting;

import com.playking.adapters.Adapter;
import com.playking.lighting.LightSource;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lights")
public class LightsAdapter implements Adapter<List<LightSource>> {
    @XmlElements({
        @XmlElement(name = "spot-light", type = SpotLightAdapter.class),
        @XmlElement(name = "directional-light", type = DirectionalLightAdapter.class),
        @XmlElement(name = "point-light", type = PointLightAdapter.class),
    })
    private List<Adapter<Object>> lights;


    @Override
    public List<LightSource> build() {
        return lights
            .stream()
            .map(adapter -> (LightSource)adapter.build())
            .collect(Collectors.toList());
    }
}
