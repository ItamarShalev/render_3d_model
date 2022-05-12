package project;

import com.playking.geometries.Sphere;
import com.playking.geometries.Triangle;
import com.playking.lighting.AmbientLight;
import com.playking.lighting.DirectionalLight;
import com.playking.lighting.PointLight;
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

public class MiniProjectTests {

    /**
     * Test for mini-project, build abalone model picture.
     */
    @Test
    public void testMiniProject1() {
        Scene scene = new Scene("Test scene");

        scene.lights.add(new SpotLight(new Color(300, 0, 0),
                                       new Point(0, 50, -750),
                                       new Vector(0, -50, -1)).setKL(4E-5).setKQ(2E-7));
        scene.lights.add(new PointLight(new Color(0, 0, 200), new Point(250, 25, -600))
                             .setKL(0.00001)
                             .setKQ(0.000001));

        scene.lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(0, 0, -1)));

        Camera camera = new Camera(new Point(0, -750, 0),
                                   new Vector(0, 0.35, -0.35),
                                   new Vector(0, 0.35, 0.35)).setSize(150, 150).setDistance(700);

        Point center = new Point(0, 50, -800);
        Point p1 = new Point(0, 170, -800);
        Point p2 = new Point(104.4, 110, -800);
        Point p3 = new Point(104.4, -10, -800);
        Point p4 = new Point(0, -70, -800);
        Point p5 = new Point(-104.4, -10, -800);
        Point p6 = new Point(-104.4, 110, -800);

        Sphere downSpr = new Sphere(new Point(0, 50, -870), 60);
        downSpr
            .setEmission(new Color(0, 0, 0))
            .setMaterial(new Material().setKT(0.5).setShininess(100).setKS(0.5).setKD(0.5));

        Color tableCol = new Color(20, 20, 20);
        Material triangleMat = new Material()
            .setKR(0.4)
            .setKD(0.1)
            .setKS(1)
            .setShininess(50)
            .setKT(0.2);
        Triangle t1 = new Triangle(center, p1, p2);
        Triangle t2 = new Triangle(center, p2, p3);
        Triangle t3 = new Triangle(center, p3, p4);
        Triangle t4 = new Triangle(center, p4, p5);
        Triangle t5 = new Triangle(center, p5, p6);
        Triangle t6 = new Triangle(center, p6, p1);
        t1.setEmission(tableCol).setMaterial(triangleMat);
        t2.setEmission(tableCol).setMaterial(triangleMat);
        t3.setEmission(tableCol).setMaterial(triangleMat);
        t4.setEmission(tableCol).setMaterial(triangleMat);
        t5.setEmission(tableCol).setMaterial(triangleMat);
        t6.setEmission(tableCol).setMaterial(triangleMat);

        Material firstMat = new Material().setKR(0.2).setKD(0.1).setKS(1).setShininess(100);

        double depthSp = -793;
        Sphere s1 = new Sphere(new Point(0, 151, depthSp), 10);
        Sphere s2 = new Sphere(new Point(-22, 138.5, depthSp), 10);
        Sphere s3 = new Sphere(new Point(-44, 126, depthSp), 10);
        Sphere s4 = new Sphere(new Point(-66, 113.5, depthSp), 10);
        Sphere s5 = new Sphere(new Point(-88, 101, depthSp), 10);

        s1.setMaterial(firstMat);
        s2.setMaterial(firstMat);
        s3.setMaterial(firstMat);
        s4.setMaterial(firstMat);
        s5.setMaterial(firstMat);

        Sphere s16 = new Sphere(new Point(-88, 77, depthSp), 10);
        Sphere s15 = new Sphere(new Point(-66, 89.5, depthSp), 10);
        Sphere s14 = new Sphere(new Point(-44, 102, depthSp), 10);
        Sphere s13 = new Sphere(new Point(-22, 114.5, depthSp), 10);
        Sphere s12 = new Sphere(new Point(0, 127, depthSp), 10);
        Sphere s11 = new Sphere(new Point(22, 139.5, depthSp), 10);

        s11.setMaterial(firstMat);
        s12.setMaterial(firstMat);
        s13.setMaterial(firstMat);
        s14.setMaterial(firstMat);
        s15.setMaterial(firstMat);
        s16.setMaterial(firstMat);

        Sphere s21 = new Sphere(new Point(0, 101, depthSp), 10);
        Sphere s22 = new Sphere(new Point(-22, 89.5, depthSp), 10);
        Sphere s23 = new Sphere(new Point(-44, 77, depthSp), 10);

        s21.setMaterial(firstMat);
        s22.setMaterial(firstMat);
        s23.setMaterial(firstMat);

        Sphere s71 = new Sphere(new Point(0, -1, depthSp), 10);
        Sphere s72 = new Sphere(new Point(22, 10.5, depthSp), 10);
        Sphere s73 = new Sphere(new Point(44, 23, depthSp), 10);

        Color secColor = new Color(150, 150, 150);

        s71.setEmission(secColor).setMaterial(firstMat);
        s72.setEmission(secColor).setMaterial(firstMat);
        s73.setEmission(secColor).setMaterial(firstMat);

        Sphere s81 = new Sphere(new Point(88, 23, depthSp), 10);
        Sphere s82 = new Sphere(new Point(66, 10.5, depthSp), 10);
        Sphere s83 = new Sphere(new Point(44, -2, depthSp), 10);
        Sphere s84 = new Sphere(new Point(22, -14.5, depthSp), 10);
        Sphere s85 = new Sphere(new Point(0, -27, depthSp), 10);
        Sphere s86 = new Sphere(new Point(-22, -39.5, depthSp), 10);
        s81.setEmission(secColor).setMaterial(firstMat);
        s82.setEmission(secColor).setMaterial(firstMat);
        s83.setEmission(secColor).setMaterial(firstMat);
        s84.setEmission(secColor).setMaterial(firstMat);
        s85.setEmission(secColor).setMaterial(firstMat);
        s86.setEmission(secColor).setMaterial(firstMat);

        Sphere s91 = new Sphere(new Point(88, -1, depthSp), 10);
        Sphere s92 = new Sphere(new Point(66, -13.5, depthSp), 10);
        Sphere s93 = new Sphere(new Point(44, -26, depthSp), 10);
        Sphere s94 = new Sphere(new Point(22, -38.5, depthSp), 10);
        Sphere s95 = new Sphere(new Point(0, -51, depthSp), 10);
        s91.setEmission(secColor).setMaterial(firstMat);
        s92.setEmission(secColor).setMaterial(firstMat);
        s93.setEmission(secColor).setMaterial(firstMat);
        s94.setEmission(secColor).setMaterial(firstMat);
        s95.setEmission(secColor).setMaterial(firstMat);


        scene.setAmbient(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));
        scene.geometries.add(downSpr, t1, t2, t3, t4, t5, t6, //
                             s1, s2, s3, s4, s5, //
                             s11, s12, s13, s14, s15, s16, //
                             s21, s22, s23, //
                             s71, s72, s73, //
                             s81, s82, s83, s84, s85, s86, //
                             s91, s92, s93, s94, s95);

        ImageWriter imageWriter = new ImageWriter("MiniProjectLevel1", 1000, 1000);
        camera.setImageWriter(imageWriter);
        camera.setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.writeToImage();
    }
}
