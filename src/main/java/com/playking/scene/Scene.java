package com.playking.scene;

import com.playking.elements.AmbientLight;
import com.playking.geometries.Geometries;
import com.playking.geometries.Intersect;
import com.playking.primitives.Color;
import com.playking.primitives.Double3;

/**
 * PDS class describe scene all the data about the scene.
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    private Scene(String name) {
        this.name = name;
        background = Color.BLACK;
        ambientLight = new AmbientLight();
        geometries = new Geometries();
    }

    public static class Builder {
        private Scene scene = null;

        public Builder(String name) {
            scene = new Scene(name);
        }

        public Builder reset(String name) {
            scene = new Scene(name);
            return this;
        }

        public Builder setBackground(Color color) {
            scene.background = color;
            return this;
        }

        public Builder setAmbient(Color color, Double3 kAmbient) {
            scene.ambientLight = new AmbientLight(color, kAmbient);
            return this;
        }

        public Builder addGeometry(Intersect geometries) {
            scene.geometries.add(geometries);
            return this;
        }

        public Scene build() {
            return scene;
        }
    }
}
