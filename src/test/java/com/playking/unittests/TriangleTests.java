package com.playking.unittests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.geometries.Triangle;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;
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
}
