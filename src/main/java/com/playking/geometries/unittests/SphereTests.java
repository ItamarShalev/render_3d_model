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

        /* TC01 variables */
        Sphere sphereTC01 = new Sphere(new Point(1, 0, 0), 1d);
        Ray rayTC01 = new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0));

        /* TC02 variables */
        Point point1TC02 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point pointTC02 = new Point(1.53484692283495, 0.844948974278318, 0);
        Ray rayTC02 = new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0));

        /* TC03 variables */
        Point pointTC03 = new Point(1, 1, 0);
        Ray rayTC03 = new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));

        /* TC04 variables */
        Ray rayTC04 = new Ray(new Point(1, 1.5, 0), new Vector(0, 1, 0));

        /* TC05 variables */
        Point pointTC05 = new Point(1.25, -0.9682458365519, 0);
        Ray rayTC05 = new Ray(new Point(1.25, 0.9682458365519, 0), new Vector(0, -1, 0));

        /* TC06 variables */
        Ray rayTC06 = new Ray(new Point(1.25, 0.9682458365519, 0), new Vector(0, 1, 0));

        /* TC07 variables */
        Point pointTC07 = new Point(1, -1, 0);
        Point point1TC07 = new Point(1, 1, 0);
        Ray rayTC07 = new Ray(new Point(1, -2, 0), new Vector(0, 1, 0));

        /* TC08 variables */
        Point pointTC08 = new Point(1, 1, 0);
        Ray rayTC08 = new Ray(new Point(1, -1, 0), new Vector(0, 1, 0));

        /* TC09 variables */
        Ray rayTC09 = new Ray(new Point(1, -0.5, 0), new Vector(0, 1, 0));

        /* TC10 variables */
        Ray rayTC10 = new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));

        /* TC11 variables */
        Ray rayTC11 = new Ray(new Point(1, 1, 0), new Vector(0, 1, 0));

        /* TC12 variables */
        Ray rayTC12 = new Ray(new Point(1, 2, 0), new Vector(0, 1, 0));

        /* TC13 variables */
        Ray rayTC13 = new Ray(new Point(2, -1, 0), new Vector(0, 1, 0));

        /* TC14 variables */
        Ray rayTC14 = new Ray(new Point(2, 0, 0), new Vector(0, 1, 0));

        /* TC15 variables */
        Ray rayTC15 = new Ray(new Point(2, 1, 0), new Vector(0, 1, 0));

        /* TC16 variables */
        Ray rayTC16 = new Ray(new Point(3, 0, 0), new Vector(0, 1, 0));


        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Ray's line is outside the sphere (0 points) */
        assertNull(sphereTC01.findIntersections(rayTC01), "ERROR: Ray's line out of sphere");

        /* TC02: Ray starts before and crosses the sphere (2 points) */
        List<Point> result = sphereTC01.findIntersections(rayTC02);

        assertEquals(2, result.size(), "ERROR: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(point1TC02, pointTC02), result, "ERROR: Ray crosses sphere");

        /* TC03: Ray starts inside the sphere (1 point) */
        result = sphereTC01.findIntersections(rayTC03);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(pointTC03), result, "ERROR: Ray crosses sphere");

        /* TC04: Ray starts after the sphere (0 points) */
        assertNull(sphereTC01.findIntersections(rayTC04),
                   "ERROR: there should not be intersection point");

        /* =============== Boundary Values Tests ================== */

        /* **** Group: Ray's line crosses the sphere (but not the center) */

        /* TC05: Ray starts at sphere and goes inside (1 points) */
        result = sphereTC01.findIntersections(rayTC05);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(pointTC05), result, "ERROR: Ray crosses sphere");

        /*
         * TC06: Ray starts at sphere and goes outside (0 points)
         * like TC05 but the vector go to other side
         */
        assertNull(sphereTC01.findIntersections(rayTC06),
                   "ERROR: there should not be intersection point");

        /* **** Group: Ray's line goes through the center */

        /* TC07: Ray starts before the sphere (2 points) */
        result = sphereTC01.findIntersections(rayTC07);

        assertEquals(2, result.size(), "ERROR: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(pointTC07, point1TC07), result, "ERROR: Ray crosses sphere");

        /* TC08: Ray starts at sphere and goes inside (1 points) */
        result = sphereTC01.findIntersections(rayTC08);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(pointTC08), result, "ERROR: Ray crosses sphere");

        /* TC09: Ray starts inside (1 points) */
        result = sphereTC01.findIntersections(rayTC09);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(pointTC08), result, "ERROR: Ray crosses sphere");

        /* TC10: Ray starts at the center (1 points) */
        result = sphereTC01.findIntersections(rayTC10);

        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(pointTC08), result, "ERROR: Ray crosses sphere");

        /* TC11: Ray starts at sphere and goes outside (0 points) */
        assertNull(sphereTC01.findIntersections(rayTC11),
                   "ERROR: there should not be intersection point");

        /* TC12: Ray starts after sphere (0 points) */
        assertNull(sphereTC01.findIntersections(rayTC12),
                   "ERROR: there should not be intersection point");

        /* **** Group: Ray's line is tangent to the sphere (all tests 0 points) */

        /* TC13: Ray starts before the tangent point */
        assertNull(sphereTC01.findIntersections(rayTC13),
                   "ERROR: there should not be intersection point");

        /* TC14: Ray starts at the tangent point */
        assertNull(sphereTC01.findIntersections(rayTC14),
                   "ERROR: there should not be intersection point");

        /* TC15: Ray starts after the tangent point */
        assertNull(sphereTC01.findIntersections(rayTC15),
                   "ERROR: there should not be intersection point");

        /* **** Group: Special cases */

        /* TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's center line */
        assertNull(sphereTC01.findIntersections(rayTC16),
                   "ERROR: there should not be intersection point");
    }
}

