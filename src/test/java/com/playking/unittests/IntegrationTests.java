package com.playking.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.geometries.Plane;
import com.playking.geometries.Sphere;
import com.playking.geometries.Triangle;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;
import com.playking.renderer.Camera;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Integration test for camera and shapes.
 */
public class IntegrationTests {

    private static Vector vectorTo;
    private static Vector vectorUp;
    private static Camera camera;

    @BeforeAll
    public static void setupAll() {
        vectorTo = new Vector(0, 0, -1);
        vectorUp = new Vector(0, 1, 0);
        camera = new Camera(Point.ZERO, vectorTo, vectorUp);
    }


    @Test
    public void cameraSphereIntersections() {



        /* TC01: sphere with 2 points that intersect */

        /* Variables */
        Sphere sphereTC01 = new Sphere(new Point(0, 0, -3), 1);
        camera.setDistance(1).setSize(3, 3);
        List<Point> result01 = camera.findIntersections(3, 3, sphereTC01);
        List<Point> exceptedResult01 = Arrays.asList(new Point(0, 0, -2), new Point(0, 0, -4));

        /* Asserts */
        Assertions.assertNotNull(result01,
                                 "ERROR: There are no intersection, and except for 2 points");
        assertTrue(
            exceptedResult01.size() == result01.size() && exceptedResult01.containsAll(result01),
            "ERROR: There aren't the same points that intersect");

        /* TC02: sphere with 18 points that intersect */

        /* Variables */
        Sphere sphereTC02 = new Sphere(new Point(0, 0, -2.5), 2.5);
        Camera cameraTC02 = new Camera(new Point(0, 0, 0.5), vectorTo, vectorUp);
        cameraTC02.setDistance(1).setSize(3, 3);
        List<Point> result02 = cameraTC02.findIntersections(3, 3, sphereTC02);

        /* Asserts */
        assertEquals(18, result02.size(), "ERROR: Except for 18 intersect points");

        /*
         * TC03: sphere with 10 points that intersect,
         * part of the sphere is behind the view plane
         */

        /* Variables */
        Sphere sphereTC03 = new Sphere(new Point(0, 0, -2), 2);
        Camera cameraTC03 = new Camera(new Point(0, 0, 0.5), vectorTo, vectorUp);
        cameraTC03.setDistance(1).setSize(3, 3);
        List<Point> result03 = cameraTC03.findIntersections(3, 3, sphereTC03);

        /* Asserts */
        assertEquals(10, result03.size(), "ERROR: Except for 10 intersect points");


        /* TC04: sphere with 9 points that intersect, part of the sphere is behind the camera */

        /* Variables */
        Sphere sphereTC04 = new Sphere(new Point(0, 0, 0), 4);
        Camera cameraTC04 = new Camera(new Point(0, 0, 1), vectorTo, vectorUp);
        cameraTC04.setDistance(1).setSize(3, 3);
        List<Point> result04 = cameraTC04.findIntersections(3, 3, sphereTC04);

        /* Asserts */
        assertEquals(9, result04.size(), "ERROR: Except for 9 intersect points");

        /* TC05: sphere with no intersect, the sphere is behind the camera */

        /* Variables */
        Sphere sphereTC05 = new Sphere(new Point(0, 0, 1), 0.5);
        camera.setDistance(1).setSize(3, 3);
        List<Point> result05 = camera.findIntersections(3, 3, sphereTC05);

        /* Asserts */
        assertNull(result05, "ERROR: Except for null, there are no intersect points");
    }

    @Test
    public void cameraPlaneIntersections() {


        /* TC01: plane with 9 points that intersect */

        /* Variables */
        Plane planeTC01 = new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));
        camera.setDistance(1).setSize(3, 3);
        List<Point> result01 = camera.findIntersections(3, 3, planeTC01);

        /* Asserts */
        assertEquals(9, result01.size(), "ERROR: Except for 9 intersect points");

        /* TC02: plane with 9 points that intersect */

        /* Variables */
        Plane planeTC02 = new Plane(new Point(0, 0, -2), new Vector(0, 0.5, -1));
        camera.setDistance(1).setSize(3, 3);
        List<Point> result02 = camera.findIntersections(3, 3, planeTC02);

        /* Asserts */
        assertEquals(9, result02.size(), "ERROR: Except for 9 intersect points");


        /* TC03: plane with 6 points that intersect */

        /* Variables */
        Plane planeTC03 = new Plane(new Point(0, 0, -2), new Vector(0, 4, -1));
        camera.setDistance(1).setSize(3, 3);
        List<Point> result03 = camera.findIntersections(3, 3, planeTC03);

        /* Asserts */
        assertEquals(6, result03.size(), "ERROR: Except for 9 intersect points");
    }

    @Test
    public void cameraTriangleIntersections() {

        /* TC01: triangle with one point that intersect */

        /* Variables */
        Triangle triangleTC01 = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2),
                                             new Point(-1, -1, -2));
        camera.setDistance(1).setSize(3, 3);
        List<Point> result01 = camera.findIntersections(3, 3, triangleTC01);

        /* Asserts */
        assertEquals(1, result01.size(), "ERROR: Except for one intersect point");

        /* TC02: triangle with 2 points that intersect */

        /* Variables */
        Triangle triangleTC02 = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2),
                                             new Point(-1, -1, -2));
        camera.setDistance(1).setSize(3, 3);
        List<Point> result02 = camera.findIntersections(3, 3, triangleTC02);

        /* Asserts */
        assertEquals(2, result02.size(), "ERROR: Except for 2 intersect points");
    }
}
