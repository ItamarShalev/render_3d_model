package com.playking.geometries.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.geometries.Triangle;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Testing Triangle.
 */
public class TriangleTests {

    /**
     * Test method for {@link Triangle#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {

        Point a = new Point(6, 8, 0);
        Point b = new Point(0, 0, 0);
        Point c = new Point(9, 0, 0);

        Triangle triangle = new Triangle(a, b, c);
        Vector normalizeVector = new Vector(0, 0, 1);
        Vector resultNormal = triangle.getNormal(a);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Check normal in specific point. */
        assertTrue(normalizeVector.isSameNormal(resultNormal),
                   "ERROR: getNormal() doesn't work correctly.");
    }

    public void testFindIntersection() {

        Point a = new Point(1, 0, 2);
        Point b = new Point(3, 0, 2);
        Point c = new Point(2, 2, 2);

        Triangle triangle = new Triangle(a, b, c);
        Vector v = new Vector(0, 0, 1);
        Ray ray = new Ray(new Point(2, 1, 0), v);
        Point p = new Point(2, 1, 2); // expected intersect point
        // ============ Equivalence Partitions Tests ==============


        List<Point> result = triangle.findIntersections(ray);

        // TC 1: Ray intersect the triangle inside
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p), result, "Ray intersects the plane");

        // TC 2: Ray not intersect the triangle and goes against edge
        ray = new Ray(new Point(1, -1, 0), v);
        result = triangle.findIntersections(ray);

        assertNull(result, "ray shouldnt intersect, ray against edge");

        // TC 3: Ray not intersect the triangle and goes against vertax
        ray = new Ray(new Point(2, -3, 0), v);
        result = triangle.findIntersections(ray);

        assertNull(result, "ray shouldnt intersect, ray against vertax");

        // =============== Boundary Values Tests ==================

        // TC 10: ray goes through edge
        ray = new Ray(new Point(2, 0, 0), v);
        result = triangle.findIntersections(ray);

        assertNull(result, "ray shouldnt intersect, ray against vertax");

        // TC 11: ray goes through vertax
        ray = new Ray(new Point(3, 0, 0), v);
        result = triangle.findIntersections(ray);

        assertNull(result, "ray shouldnt intersect, ray against vertax");

        // TC 11: ray goes through vertax
        ray = new Ray(new Point(4, 0, 0), v);
        result = triangle.findIntersections(ray);

        assertNull(result, "ray shouldnt intersect, ray against vertax");
    }
}
