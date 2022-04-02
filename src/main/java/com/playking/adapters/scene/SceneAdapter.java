package com.playking.adapters.scene;

import com.playking.adapters.Adapter;
import com.playking.adapters.geometries.GeometriesAdapter;
import com.playking.adapters.lighting.AmbientLightAdapter;
import com.playking.adapters.primitives.ColorAdapter;
import com.playking.scene.Scene;
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


    @Override
    public Scene build() {
        Scene scene = new Scene(name);
        scene.setBackground(ColorAdapter.parseColor(background)).setAmbient(ambientLight.build())
             .addGeometry(geometries.build());
        return scene;
    }
}
