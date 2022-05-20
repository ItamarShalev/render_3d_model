package com.adapters.scene;

import com.adapters.Adapter;
import com.adapters.lighting.AmbientLightAdapter;
import com.adapters.lighting.LightsAdapter;
import com.adapters.geometries.GeometriesAdapter;
import com.adapters.primitives.ColorAdapter;
import com.scene.Scene;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "scene")
public class SceneAdapter implements Adapter<Scene> {
    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "background")
    private String background;

    @XmlElement(name = "ambient-light")
    private AmbientLightAdapter ambientLight;

    @XmlElement(name = "geometries")
    private GeometriesAdapter geometries;

    @XmlElement(name = "lights")
    private LightsAdapter lights;


    @Override
    public Scene build() {
        Scene scene = new Scene(name);
        scene
            .setBackground(ColorAdapter.parseColor(background))
            .setAmbient(ambientLight.build())
            .addGeometry(geometries.build());
        return scene;
    }
}
