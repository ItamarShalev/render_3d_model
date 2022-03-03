package com.playking.unittests;

import static com.playking.primitives.Util.isZero;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.primitives.Double3;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;
import org.junit.jupiter.api.Test;

/**
 * Testing Vector.
 */
public class VectorTests {

    /**
     * Test method for {@link Vector#Vector(Double3)}.
     */
    @Test
    public void testConstructor() {
        /* =============== Boundary Values Tests ================== */

        /* TC01: Check if the zero vector result handle correctly */
        assertThrows(IllegalArgumentException.class, () -> new Vector(new Double3(0, 0, 0)),
                     "ERROR: zero vector doesn't throw exception");
    }

    /**
     * Test method for {@link Vector#add(Vector)}.
     */
    @Test
    public void testAdd() {

        Vector p1 = new Vector(1, 2, 3);
        Vector p2 = new Vector(-1, -2, -3);
        Vector p3 = new Vector(2, 4, 7);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Add valid point to valid vector. */
        assertEquals(new Vector(3, 6, 10), p1.add(p3),
                     "ERROR: Vector + Vector does not work correctly");

        /* =============== Boundary Values Tests ================== */

        /* TC02: Check if the zero vector result handle correctly */
        assertThrows(IllegalArgumentException.class, () -> p1.add(p2),
                     "ERROR: Vector + Vector doesn't work correctly in zero vector");
    }

    /**
     * Test method for {@link Vector#subtract(Vector)}.
     */
    @Test
    public void testSubtract() {

        Vector p1 = new Vector(1, 2, 3);
        Vector p3 = new Vector(2, 4, 7);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Subtract valid point to valid vector. */
        assertEquals(new Vector(1, 2, 4), p3.subtract(p1),
                     "ERROR: Vector - Vector does not work correctly");

        /* =============== Boundary Values Tests ================== */

        /* TC02: Check if the zero vector result handle correctly */
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
                     "ERROR: Vector - Vector doesn't work correctly in zero vector");
    }

    /**
     * Test method for {@link Vector#scale(double)}.
     */
    @Test
    public void testScale() {

        Vector p1 = new Vector(1, 2, 3);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Check correct values. */
        assertEquals(new Vector(3.2, 6.4, 9.6), p1.scale(3.2),
                     "ERROR: Vector * scalar does not work correctly");

        /* =============== Boundary Values Tests ================== */

        /* TC02: Check if the zero vector result handle correctly */
        assertThrows(IllegalArgumentException.class, () -> p1.scale(0),
                     "ERROR: Vector * scalar doesn't work correctly in zero vector");
    }


    /**
     * Test method for {@link Vector#dotProduct(Vector)}.
     */
    @Test
    public void testDotProduct() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        Vector v4 = new Vector(7, 3, 1);

        Point a = new Point(1, 3, 9);
        Point b = new Point(-4, -7, 0);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Check correct values. */
        assertTrue(isZero(v1.dotProduct(v4) - 16),
                   "ERROR: dotProduct() failed with valid vectors wrong value");

        /* =============== Boundary Values Tests ================== */

        /* TC02: parallel vectors. */
        assertTrue(isZero(v1.dotProduct(v2) + 28),
                   "ERROR: dotProduct() failed with parallel vectors wrong value");

        /* TC03: vectors with same direction. */
        Vector u = v1.normalize();
        assertFalse(v1.dotProduct(u) < 0,
                    "ERROR: the normalized vector is opposite to the original one");


        /* TC04: vectors with reverse directions. */
        Vector va1 = a.subtract(b);
        Vector va2 = b.subtract(a);
        assertTrue(isZero(va1.dotProduct(va2) + 206),
                   "ERROR: dotProduct() reverse direction vectors wrong value");


        /* TC05: vectors with obtuse angle between them. */
        assertTrue(isZero(v3.dotProduct(new Vector(1, 1, 3)) + 3),
                   "ERROR: dotProduct() obtuse angle between vectors wrong value");


        /* TC06: Orthogonal vectors. */
        assertTrue(isZero(v1.dotProduct(v3)),
                   "ERROR: dotProduct() for orthogonal vectors is not zero");

        /* TC07: vectors with sharp angle between them. */
        assertTrue(isZero(v3.dotProduct(new Vector(1, 2, -6)) - 18),
                   "ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    public void testLength() {
        Vector v1 = new Vector(0, 3, 4);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: check length of vector. */
        assertTrue(isZero(v1.length() - 5), "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: check length of square vector. */
        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    public void testNormalize() {
        Vector v1 = new Vector(1, 2, 3);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: check if normalize of valid vector. */
        Vector u = v1.normalize();
        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");
    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Point a = new Point(1, 3, 9);
        Point b = new Point(-4, -7, 0);

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);
        Vector v3 = new Vector(0, 3, -2);

        Vector vr = v1.crossProduct(v3);
        Vector u = v1.normalize();

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: dot product with sharp angle between vectors. */

        assertTrue(isZero(vr.length() - v1.length() * v3.length()),
                   "ERROR: crossProduct() wrong result length");

        assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v3)),
                   "ERROR: crossProduct() result is not orthogonal to its operands");

        /* =============== Boundary Values Tests ================== */

        /* TC02: vectors with reverse directions. */
        Vector va1 = a.subtract(b);
        Vector va2 = b.subtract(a);
        assertThrows(IllegalArgumentException.class, () -> va1.crossProduct(va2),
                     "ERROR: the normalized vector is not parallel to the original one");


        /* TC03: orthogonal vectors. */
        assertEquals(new Vector(-13, 2, 3), v1.crossProduct(v3),
                     "ERROR: crossProduct() wrong value");


        /* TC04: vectors with obtuse angle between them. */
        assertEquals(new Vector(11, -2, -3), v3.crossProduct(new Vector(1, 1, 3)),
                     "ERROR: crossProduct() wrong value");

        /* TC05: vectors with sharp angle between them. */
        assertEquals(new Vector(-14, -2, -3), v3.crossProduct(new Vector(1, 2, -6)),
                     "ERROR: crossProduct() wrong value");

        /* TC06: vectors with same direction. */
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(u),
                     "ERROR: the normalized vector is not parallel to the original one");


        /* TC07: parallel vectors. */
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                     "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }
}
