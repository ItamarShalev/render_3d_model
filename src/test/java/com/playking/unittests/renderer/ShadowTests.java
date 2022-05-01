package renderer;

import static java.awt.Color.BLUE;

import com.playking.geometries.Intersect;
import com.playking.geometries.Sphere;
import com.playking.geometries.Triangle;
import com.playking.lighting.AmbientLight;
import com.playking.lighting.SpotLight;
import com.playking.primitives.Color;
import com.playking.primitives.Double3;
import com.playking.primitives.Material;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;
import com.playking.renderer.Camera;
import com.playking.renderer.ImageWriter;
import com.playking.renderer.RayTracerBasic;
import com.playking.scene.Scene;
import org.junit.jupiter.api.Test;

/**
 * Testing basic shadows.
 * @author Dan
 */
public class ShadowTests {

    private final Material trMaterial = new Material().setKD(0.5).setKS(0.5).setShininess(30);
    private final Scene scene = new Scene("Test scene");
    Point point1 = new Point(0, 0, -200);
    private final Intersect sphere = new Sphere(point1, 60d).setEmission(new Color(BLUE))
                                                            .setMaterial(
                                                                new Material().setKD(0.5).setKS(0.5)
                                                                              .setShininess(30));
    Point point2 = new Point(0, 0, 1000);
    Point point3 = null;
    Vector vector1 = new Vector(0, 0, -1);
    Vector vector2 = new Vector(0, 1, 0);
    private final Camera camera = new Camera(point2, vector1, vector2).setSize(200, 200)
                                                                      .setDistance(1000)
                                                                      .setRayTracer(
                                                                          new RayTracerBasic(
                                                                              scene));
    Color color = (new Color(400, 240, 0));
    Triangle triangle1 = null;
    Triangle triangle2 = null;
    Sphere sphere1 = null;

    /**
     * Helper function for the tests in this module.
     * @param pictName Name of the image
     * @param triangle triangle
     * @param spotLocation triangle Location
     */
    void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
        vector1 = new Vector(1, 1, -3);
        scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
        scene.lights.add(new SpotLight(color, spotLocation, vector1).setKL(1E-5).setKQ(1.5E-7));
        camera.setImageWriter(new ImageWriter(pictName, 400, 400)).renderImage().writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade.
     */
    @Test
    public void sphereTriangleInitial() {
        point1 = new Point(-70, -40, 0);
        point2 = new Point(-40, -70, 0);
        point3 = new Point(-68, -68, -4);
        triangle1 = new Triangle(point1, point2, point3);
        point1 = new Point(-100, -100, 200);
        sphereTriangleHelper("shadowSphereTriangleInitial", triangle1, point1);
    }

    /**
     * Sphere-Triangle shading - move triangle up-right.
     */
    @Test
    public void sphereTriangleMove1() {

        point1 = new Point(-62, -32, 0);
        point2 = new Point(-32, -62, 0);
        point3 = new Point(-60, -60, -4);
        triangle1 = new Triangle(point1, point2, point3);
        point1 = new Point(-100, -100, 200);
        sphereTriangleHelper("shadowSphereTriangleMove2", triangle1, point1);
    }

    /**
     * Sphere-Triangle shading - move triangle upper-righter.
     */
    @Test
    public void sphereTriangleMove2() {
        point1 = new Point(-49, -19, 0);
        point2 = new Point(-19, -49, 0);
        point3 = new Point(-47, -47, -4);
        triangle1 = new Triangle(point1, point2, point3);
        point1 = new Point(-100, -100, 200);
        sphereTriangleHelper("shadowSphereTriangleMove1", triangle1, point1);
    }

    /**
     * Sphere-Triangle shading - move spot closer.
     */
    @Test
    public void sphereTriangleSpot1() {
        point1 = new Point(-70, -40, 0);
        point2 = new Point(-40, -70, 0);
        point3 = new Point(-68, -68, -4);
        triangle1 = new Triangle(point1, point2, point3);
        point1 = new Point(-88, -88, 120);
        sphereTriangleHelper("shadowSphereTriangleSpot1", triangle1, point1);
    }

    /**
     * Sphere-Triangle shading - move spot even more close.
     */
    @Test
    public void sphereTriangleSpot2() {
        point1 = new Point(-70, -40, 0);
        point2 = new Point(-40, -70, 0);
        point3 = new Point(-68, -68, -4);
        triangle1 = new Triangle(point1, point2, point3);
        point1 = new Point(-76, -76, 70);
        sphereTriangleHelper("shadowSphereTriangleSpot2", triangle1, point1);
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere.
     * producing a shading
     */
    @Test
    public void trianglesSphere() {

        scene.setAmbient(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));
        point1 = new Point(-150, -150, -115);
        point2 = new Point(150, -150, -135);
        point3 = new Point(75, 75, -150);
        triangle1 = new Triangle(point1, point2, point3);

        point1 = new Point(-150, -150, -115);
        point2 = new Point(-70, 70, -140);
        point3 = new Point(75, 75, -150);
        triangle2 = new Triangle(point1, point2, point3);

        sphere1 = new Sphere(new Point(0, 0, -11), 30d);

        scene.geometries.add(triangle1.setMaterial(new Material().setKS(0.8).setShininess(60)),
                             triangle2.setMaterial(new Material().setKS(0.8).setShininess(60)),
                             sphere1.setEmission(new Color(java.awt.Color.BLUE))
                                    .setMaterial(
                                        new Material().setKD(0.5).setKS(0.5).setShininess(30)));

        color = new Color(700, 400, 400);
        vector1 = new Vector(-1, -1, -4);
        point1 = new Point(40, 40, 115);
        scene.lights.add(new SpotLight(color, point1, vector1).setKL(4E-4).setKQ(2E-5));

        camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
              .renderImage().writeToImage();
    }
}
