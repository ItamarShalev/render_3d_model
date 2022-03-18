package com.playking.geometries.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.geometries.Sphere;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Testing Sphere.
 */
public class SphereTests {

    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {

        Sphere sphere = new Sphere(new Point(0, 0, 0), 2);
        Vector normalizeVector = new Vector(1.73, 0, 1).normalize();
        Vector resultNormal = sphere.getNormal(new Point(1.73, 0, 1));

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Check normal in specific point. */
        assertTrue(normalizeVector.isSameNormal(resultNormal),
                   "ERROR: getNormal() doesn't work correctly.");
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
        Ray ray = new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0));
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(ray), "ERROR: Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        ray = new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0));
        List<Point> result = sphere.findIntersections(ray);

        assertEquals(2, result.size(), "ERROR: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "ERROR: Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        p1 = new Point(1, 1, 0);
        ray = new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));
        result = sphere.findIntersections(ray);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(p1), result, "ERROR: Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        ray = new Ray(new Point(1, 1.5, 0), new Vector(0, 1, 0));

        assertNull(sphere.findIntersections(ray), "ERROR: there should not be intersection point");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        p1 = new Point(1.25, -0.9682458365519, 0);
        ray = new Ray(new Point(1.25, 0.9682458365519, 0), new Vector(0, -1, 0));
        result = sphere.findIntersections(ray);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(p1), result, "ERROR: Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        // like TC11 but the vector go to other side
        ray = new Ray(new Point(1.25, 0.9682458365519, 0), new Vector(0, 1, 0));

        assertNull(sphere.findIntersections(ray), "ERROR: there should not be intersection point");

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point(1, -1, 0);
        p2 = new Point(1, 1, 0);
        ray = new Ray(new Point(1, -2, 0), new Vector(0, 1, 0));
        result = sphere.findIntersections(ray);

        assertEquals(2, result.size(), "ERROR: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(p1, p2), result, "ERROR: Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        p1 = new Point(1, 1, 0);
        ray = new Ray(new Point(1, -1, 0), new Vector(0, 1, 0));
        result = sphere.findIntersections(ray);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(p1), result, "ERROR: Ray crosses sphere");

        // TC15: Ray starts inside (1 points)
        ray = new Ray(new Point(1, -0.5, 0), new Vector(0, 1, 0));
        result = sphere.findIntersections(ray);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(p1), result, "ERROR: Ray crosses sphere");

        // TC16: Ray starts at the center (1 points)
        ray = new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));
        result = sphere.findIntersections(ray);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(p1), result, "ERROR: Ray crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point(1, 1, 0), new Vector(0, 1, 0));

        assertNull(sphere.findIntersections(ray), "ERROR: there should not be intersection point");

        // TC18: Ray starts after sphere (0 points)
        ray = new Ray(new Point(1, 2, 0), new Vector(0, 1, 0));

        assertNull(sphere.findIntersections(ray), "ERROR: there should not be intersection point");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        ray = new Ray(new Point(2, -1, 0), new Vector(0, 1, 0));

        assertNull(sphere.findIntersections(ray), "ERROR: there should not be intersection point");

        // TC20: Ray starts at the tangent point
        ray = new Ray(new Point(2, 0, 0), new Vector(0, 1, 0));

        assertNull(sphere.findIntersections(ray), "ERROR: there should not be intersection point");

        // TC21: Ray starts after the tangent point
        ray = new Ray(new Point(2, 1, 0), new Vector(0, 1, 0));

        assertNull(sphere.findIntersections(ray), "ERROR: there should not be intersection point");

        // **** Group: Special cases

        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        ray = new Ray(new Point(3, 0, 0), new Vector(0, 1, 0));
        assertNull(sphere.findIntersections(ray), "ERROR: there should not be intersection point");
    }
}

