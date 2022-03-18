package com.playking.geometries.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.playking.geometries.Geometries;
import com.playking.geometries.Plane;
import com.playking.geometries.Sphere;
import com.playking.geometries.Triangle;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;

class GeometriesTest {

    @Test
    void findIntsersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: not all shapes are intersected with Ray
        Sphere sphere = new Sphere(new Point(2, 0, 0), 1);
        Plane plane = new Plane(new Point(-5, 0, 0), new Point(0, 0, 4), new Point(0, -4, 0));
        Triangle triangle = new Triangle(new Point(6, 0, 0), new Point(4, 0, 0),
                                         new Point(2.38107, 5.79587, 0));
        Geometries geometries = new Geometries(sphere, plane, triangle);
        Ray ray = new Ray(new Point(0, 4, 0),
                          new Vector(2.732115775904636, -4.492163336027315, 0.470937088518219));
        List<Point> result = geometries.findIntersections(ray);
        assertEquals(3, result.size(), "Wrong number of points");

        // =============== Boundary Values Tests ==================
        // TC11: Collection is empty
        Geometries geometries1 = new Geometries();
        ray = new Ray(new Point(0, 4, 0),
                      new Vector(2.732115775904636, -4.492163336027315, 0.470937088518219));

        assertNull(geometries1.findIntersections(ray), "Collection of shapes is empty");

        // TC12: all shapes intersected with Ray

        triangle = new Triangle(new Point(3.394228564552314, -3.933133206886659, 0),
                                new Point(5.490624249175983, -2.801760059740879, 0),
                                new Point(2.37669835688477, -0.470644059061225, 0.797867481221467));
        geometries = new Geometries(sphere, plane, triangle);
        ray = new Ray(new Point(0, 4, 0),
                      new Vector(2.732115775904636, -4.492163336027315, 0.470937088518219));
        result = geometries.findIntersections(ray);

        assertEquals(4, result.size(), "Wrong number of points");

        // TC13: no shape is intersected with Ray
        sphere = new Sphere(new Point(1, 0, 0), 1);
        plane = new Plane(new Point(0, 0, 4), new Point(0, 0, 0), new Point(-3, 0, 0));
        triangle = new Triangle(new Point(0, -1, 0), new Point(0, -2, 0), new Point(0, -2, 2));
        geometries = new Geometries(sphere, plane, triangle);
        ray = new Ray(new Point(4, 0, 0), new Vector(1, 0, 0));

        assertNull(geometries.findIntersections(ray), "no shape is intersected with Ray");

        // TC14: only 1 shape is intersected
        ray = new Ray(new Point(4, 0, 0), new Vector(-40, -1, -1));
        List<Point> result2 = geometries.findIntersections(ray);

        assertEquals(2, result2.size(), "Wrong number of points");
    }
}
