package renderer;

import com.playking.elements.AmbientLight;
import com.playking.geometries.Sphere;
import com.playking.geometries.Triangle;
import com.playking.primitives.Color;
import com.playking.primitives.Double3;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;
import com.playking.renderer.Camera;
import com.playking.renderer.ImageWriter;
import com.playking.renderer.RayTracerBasic;
import com.playking.scene.Scene;
import org.junit.jupiter.api.Test;


/**
 * Test rendering a basic image.
 * @author Dan
 */
public class RenderTests {
    private static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * Produce a scene with basic 3D model and render it into a png image with a grid.
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene")
            .setAmbient(new AmbientLight(new Color(255, 191, 191), new Double3(1, 1, 1)))
            .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(new Point(0, 0, -100), 50),
                             new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100),
                                          new Point(-100, 100, -100)),

                             new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                                          new Point(-100, -100, -100)),

                             new Triangle(new Point(100, 0, -100), new Point(0, -100, -100),
                                          new Point(100, -100, -100)));

        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setDistance(100).setSize(500, 500)
            .setImageWriter(new ImageWriter("base render test", 1000, 1000))
            .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, Color.YELLOW);
        camera.writeToImage();
    }

    /**
     * Test for XML based scene.
     */
    @Test
    public void basicRenderXml() {
        Scene scene = new Scene("XML Test scene");
        // enter XML file name and parse from XML file into scene object
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setDistance(100).setSize(500, 500)
            .setImageWriter(new ImageWriter("xml render test", 1000, 600))
            .setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.printGrid(100, Color.YELLOW);
        camera.writeToImage();
    }
}
