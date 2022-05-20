package renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.GRAY;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.geometries.Sphere;
import com.geometries.Triangle;
import com.lighting.AmbientLight;
import com.primitives.Axis;
import com.primitives.Color;
import com.primitives.Double3;
import com.primitives.Point;
import com.primitives.Vector;
import com.primitives.XmlHelper;
import com.renderer.Camera;
import com.renderer.GifSequenceWriter;
import com.renderer.ImageWriter;
import com.renderer.RayTracerBasic;
import com.scene.Scene;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;


/**
 * Test rendering a basic image.
 * @author Dan
 */
public class RenderTests {
    private static final String FOLDER_PATH = new File(System.getProperty("user.dir"),
                                                       "images").getAbsolutePath();


    /**
     * Produce a scene with basic 3D model and render it into a png image with a grid.
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene")
            .setAmbient(new AmbientLight(new Color(255, 191, 191), new Double3(1, 1, 1)))
            .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(new Point(0, 0, -100), 50),
                             new Triangle(new Point(-100, 0, -100),
                                          new Point(0, 100, -100),
                                          new Point(-100, 100, -100)),

                             new Triangle(new Point(-100, 0, -100),
                                          new Point(0, -100, -100),
                                          new Point(-100, -100, -100)),

                             new Triangle(new Point(100, 0, -100),
                                          new Point(0, -100, -100),
                                          new Point(100, -100, -100)));

        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setDistance(100)
            .setSize(500, 500)
            .setImageWriter(new ImageWriter("base render test", 1000, 1000))
            .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, Color.YELLOW);
        camera.writeToImage();
    }

    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid.
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene");
        scene.setAmbient(new AmbientLight(new Color(WHITE), new Double3(0.2)));

        scene.geometries.add(new Sphere(new Point(0, 0, -100), 50).setEmission(new Color(GRAY)),
                             // up left
                             new Triangle(new Point(-100, 0, -100),
                                          new Point(0, 100, -100),
                                          new Point(-100, 100, -100)).setEmission(new Color(GREEN)),
                             // down left
                             new Triangle(new Point(-100, 0, -100),
                                          new Point(0, -100, -100),
                                          new Point(-100, -100, -100)).setEmission(new Color(RED)),
                             // down right
                             new Triangle(new Point(100, 0, -100),
                                          new Point(0, -100, -100),
                                          new Point(100, -100, -100)).setEmission(new Color(BLUE)));

        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setDistance(100)
            .setSize(500, 500)
            .setImageWriter(new ImageWriter("color render test", 1000, 1000))
            .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(WHITE));
        camera.writeToImage();
    }

    /**
     * Simple X axis rotation.
     * @throws IOException if some file isn't exist
     * @throws JAXBException if error in xml occurs
     */
    @Test
    public void rotationX() throws IOException, JAXBException {
        basicGifXml("rotationX", Axis.X);
    }

    /**
     * Simple Y axis rotation.
     * @throws IOException if some file isn't exist
     * @throws JAXBException if error in xml occurs
     */
    @Test
    public void rotationY() throws IOException, JAXBException {
        basicGifXml("rotationY", Axis.Y);
    }

    /**
     * Simple Z axis rotation.
     * @throws IOException if some file isn't exist
     * @throws JAXBException if error in xml occurs
     */
    @Test
    public void rotationZ() throws IOException, JAXBException {
        basicGifXml("rotationZ", Axis.Z);
    }

    /**
     * Create a simple rotation gif.
     * @param name the name of the gif file
     * @param axis which axis to rotate
     * @throws IOException if some file isn't exist
     * @throws JAXBException if error in xml occurs
     */
    public void basicGifXml(String name, Axis axis) throws IOException, JAXBException {
        Scene scene = XmlHelper.readSceneObjectFromXml("basicGif.xml");
        assertNotNull(scene);
        int interval = 5;
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new RayTracerBasic(scene))
            .setDistance(200)
            .setSize(500, 500);
        camera.setDistance(100);
        GifSequenceWriter writer = new GifSequenceWriter(FOLDER_PATH + "/" + name + "BasicGif.gif");

        for (int i = 0; i < interval; i++) {
            ImageWriter imageWriter = new ImageWriter("basicXmlGif", 500, 500);
            camera.setImageWriter(imageWriter);
            camera.renderImage();
            writer.writeToSequence(camera.printGridToImage(100, Color.YELLOW).getImage());
            writer.writeToSequence(imageWriter.getImage());
            camera.rotation(360d / interval, axis);
        }
        writer.close();
    }

    /**
     * Test for XML based scene.
     * @throws IOException when the file isn't exist
     * @throws JAXBException if error in xml occurs
     */
    @Test
    public void basicRenderXml() throws IOException, JAXBException {
        Scene scene = XmlHelper.readSceneObjectFromXml("basicRenderTestTwoColors.xml");
        assertNotNull(scene);
        scene.name = "XML Test scene";
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setDistance(100)
            .setSize(500, 500)
            .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
            .setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.printGrid(100, Color.YELLOW);
        camera.writeToImage();
    }
}
