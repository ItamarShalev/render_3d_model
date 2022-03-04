package com.playking.unittests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.geometries.Sphere;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;
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
}
