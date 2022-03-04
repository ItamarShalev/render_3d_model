package com.playking.unittests;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.geometries.Plane;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;
import org.junit.jupiter.api.Test;

/**
 * Testing Plane.
 */
public class PlaneTests {

    /**
     * Test method for {@link Plane#Plane(Point, Point, Point)}.
     */
    @Test
    public void testConstructor() {

        Point x1 = new Point(1, 0, 0);
        Point y1 = new Point(2, 0, 0);
        Point z1 = new Point(3, 0, 0);

        Point xy2 = new Point(1, 0, 0);
        Point z2 = new Point(3, 4, 0);

        /* =============== Boundary Values Tests ================== */

        /* TC01: some points on the same line. */
        assertThrows(IllegalArgumentException.class, () -> new Plane(x1, y1, z1),
                     "ERROR: Plane constructor should throw exception when some points on the same line");

        /* TC02: some points are the same. */
        assertThrows(IllegalArgumentException.class, () -> new Plane(xy2, xy2, z2),
                     "ERROR: Plane constructor should throw exception when some points are the same");
    }

    /**
     * Test method for {@link Plane#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {

        Point a = new Point(1, 2, 3);
        Point b = new Point(2, 1, 4);
        Point c = new Point(2, 1, 1);

        Plane plane = new Plane(a, b, c);
        Vector expectedVector = new Vector(3, 3, 0).normalize();

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Check normal in specific point. */
        assertTrue(expectedVector.isSameNormal(plane.getNormal(a)),
                   "ERROR: getNormal() doesn't work correctly.");
    }
}
