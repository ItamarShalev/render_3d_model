package com.playking.adapter;

import com.playking.scene.Scene;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "scene")
public class SceneAdapter implements Adapter<Scene> {
    @XmlAttribute(name = "background")
    String background;
    @XmlElement(name = "ambient-light")
    AmbientLightAdapter ambientLight;
    @XmlElement(name = "geometries")
    GeometriesAdapter geometries;


    @Override
    public Scene build() {
        Scene scene = new Scene("");
        scene.setBackground(ColorAdapter.parseColor(background)).setAmbient(ambientLight.build())
             .addGeometry(geometries.build());
        return scene;
    }
}
