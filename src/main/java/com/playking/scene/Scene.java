package com.playking.scene;

import com.playking.geometries.Geometries;
import com.playking.geometries.Intersect;
import com.playking.lighting.AmbientLight;
import com.playking.primitives.Color;

/**
 * PDS class describe scene all the data about the scene.
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    public Scene(String name) {
        this.name = name;
        background = Color.BLACK;
        ambientLight = new AmbientLight();
        geometries = new Geometries();
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
        if (geometries != null) {
            this.geometries.add(geometries);
        }
        return this;
    }
}
