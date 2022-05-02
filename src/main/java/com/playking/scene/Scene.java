package com.playking.scene;

import com.playking.geometries.Geometries;
import com.playking.geometries.Intersect;
import com.playking.lighting.AmbientLight;
import com.playking.lighting.LightSource;
import com.playking.primitives.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * PDS class describe scene all the data about the scene.
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights;

    public Scene(String name) {
        this.name = name;
        background = Color.BLACK;
        ambientLight = new AmbientLight();
        geometries = new Geometries();
        lights = new LinkedList<>();
    }


    public Scene setBackground(Color color) {
        background = color;
        return this;
    }


    public Scene setAmbient(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene addGeometry(Intersect geometries) {
        Optional.ofNullable(geometries).ifPresent(this.geometries::add);
        return this;
    }

    public Scene addLights(LightSource... lightSources) {
        Optional.ofNullable(lightSources).map(List::of).ifPresent(lights::addAll);
        return this;
    }
}
