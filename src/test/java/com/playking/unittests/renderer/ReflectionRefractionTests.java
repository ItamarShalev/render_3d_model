package renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;

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
 * Tests for reflection and transparency functionality, test for partial shadows.
 * (with transparency)
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private final Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light.
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setSize(150, 150)
            .setDistance(1000);

        scene.geometries.add(new Sphere(new Point(0, 0, -50), 50d)
                                 .setEmission(new Color(BLUE))
                                 .setMaterial(new Material()
                                                  .setKD(0.4)
                                                  .setKS(0.3)
                                                  .setShininess(100)
                                                  .setKT(0.3)),
                             new Sphere(new Point(0, 0, -50), 25d)
                                 .setEmission(new Color(RED))
                                 .setMaterial(new Material()
                                                  .setKD(0.5)
                                                  .setKS(0.5)
                                                  .setShininess(100)));
        scene.lights.add(new SpotLight(new Color(1000, 600, 0),
                                       new Point(-100, -100, 500),
                                       new Vector(-1, -1, -2)).setKL(0.0004).setKQ(0.0000006));

        camera
            .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
            .setRayTracer(new RayTracerBasic(scene))
            .renderImage()
            .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light.
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000),
                                   new Vector(0, 0, -1),
                                   new Vector(0, 1, 0)).setSize(2500, 2500).setDistance(10000);

        scene.setAmbient(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

        scene.geometries.add(new Sphere(new Point(-950, -900, -1000), 400d)
                                 .setEmission(new Color(0, 0, 100))
                                 .setMaterial(new Material()
                                                  .setKD(0.25)
                                                  .setKS(0.25)
                                                  .setShininess(20)
                                                  .setKT(0.5)),
                             new Sphere(new Point(-950, -900, -1000), 200d)
                                 .setEmission(new Color(100, 20, 20))
                                 .setMaterial(new Material()
                                                  .setKD(0.25)
                                                  .setKS(0.25)
                                                  .setShininess(20)),
                             new Triangle(new Point(1500, -1500, -1500),
                                          new Point(-1500, 1500, -1500),
                                          new Point(670, 670, 3000))
                                 .setEmission(new Color(20, 20, 20))
                                 .setMaterial(new Material().setKR(1)),
                             new Triangle(new Point(1500, -1500, -1500),
                                          new Point(-1500, 1500, -1500),
                                          new Point(-1500, -1500, -2000))
                                 .setEmission(new Color(20, 20, 20))
                                 .setMaterial(new Material().setKR(0.5)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400),
                                       new Point(-750, -750, -150),
                                       new Vector(-1, -1, -4)).setKL(0.00001).setKQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera
            .setImageWriter(imageWriter)
            .setRayTracer(new RayTracerBasic(scene))
            .renderImage()
            .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadows.
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setSize(200, 200)
            .setDistance(1000);

        scene.setAmbient(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add(new Triangle(new Point(-150, -150, -115),
                                          new Point(150, -150, -135),
                                          new Point(75, 75, -150))
                                 .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)
                                             ),
                             new Triangle(new Point(-150, -150, -115),
                                          new Point(-70, 70, -140),
                                          new Point(75, 75, -150)).setMaterial(
                                 new Material().setKD(0.5).setKS(0.5).setShininess(60)),
                             new Sphere(new Point(60, 50, -50), 30d)
                                 .setEmission(new Color(BLUE))
                                 .setMaterial(new Material()
                                                  .setKD(0.2)
                                                  .setKS(0.2)
                                                  .setShininess(30)
                                                  .setKT(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400),
                                       new Point(60, 50, 0),
                                       new Vector(0, 0, -1)).setKL(4E-5).setKQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera
            .setImageWriter(imageWriter)
            .setRayTracer(new RayTracerBasic(scene))
            .renderImage()
            .writeToImage();
    }
}
