package com.playking.unittests;

import static com.playking.primitives.Util.isZero;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.primitives.Point;
import com.playking.primitives.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class to test cases in point class.
 */
class VectorTest {

    private static Vector v1;
    private static Vector v2;
    private static Vector v3;

    @BeforeAll
    static void setUp() {
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(-2, -4, -6);
        v3 = new Vector(0, 3, -2);
    }

    @Test
    public void vectorZeroTest() {
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
                     "ERROR: zero vector does not throw an exception");
    }

    @Test
    public void lengthTest() {
        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");
    }

    @Test
    public void productParallelTest() {
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                     "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }

    @Test
    public void productSameDirectionTest() {
        Vector u = v1.normalize();
        assertFalse(v1.dotProduct(u) < 0,
                    "ERROR: the normalized vector is opposite to the original one");
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(u),
                     "ERROR: the normalized vector is not parallel to the original one");
    }

    @Test
    public void crossProductTest() {
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()),
                   "ERROR: crossProduct() wrong result length");
        assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v3)),
                   "ERROR: crossProduct() result is not orthogonal to its operands");
    }

    @Test
    public void productSharpAngleTest() {
        assertTrue(isZero(v3.dotProduct(new Vector(1, 2, -6)) - 18),
                   "ERROR: dotProduct() wrong value");

        assertEquals(new Vector(-14, -2, -3), v3.crossProduct(new Vector(1, 2, -6)),
                     "ERROR: crossProduct() wrong value");
    }

    @Test
    public void productObtuseAngleTest() {
        assertTrue(isZero(v3.dotProduct(new Vector(1, 1, 3)) + 3),
                   "ERROR: dotProduct() wrong value");

        assertEquals(new Vector(11, -2, -3), v3.crossProduct(new Vector(1, 1, 3)),
                     "ERROR: crossProduct() wrong value");
    }

    @Test
    public void productOrthogonalTest() {
        assertTrue(isZero(v1.dotProduct(v3)),
                   "ERROR: dotProduct() for orthogonal vectors is not zero");

        assertEquals(new Vector(-13, 2, 3), v1.crossProduct(v3),
                     "ERROR: crossProduct() wrong value");
    }

    @Test
    public void productReverseDirectionsTest() {
        Point a = new Point(1, 3, 9);
        Point b = new Point(-4, -7, 0);

        Vector v1 = a.subtract(b);
        Vector v2 = b.subtract(a);
        assertTrue(isZero(v1.dotProduct(v2) + 206), "ERROR: dotProduct() wrong value");
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                     "ERROR: the normalized vector is not parallel to the original one");
    }

    @Test
    public void normalizeTest() {
        Vector u = v1.normalize();
        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");
    }
}
