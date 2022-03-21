package com.playking.unittests.geometries;

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

    @Test
    public void testFindIntersection() {

        List<Point> result;

        Point a = new Point(1, 0, 2);
        Point b = new Point(3, 0, 2);
        Point c = new Point(2, 2, 2);

        Triangle triangle = new Triangle(a, b, c);
        Vector vector = new Vector(0, 0, 1);

        /* TC01 variables */
        Ray rayTC01 = new Ray(new Point(2, 1, 0), vector);
        Point pointTC01 = new Point(2, 1, 2);

        /* TC02 variables */
        Ray rayTC02 = new Ray(new Point(1, -1, 0), vector);

        /* TC03 variables */
        Ray rayTC03 = new Ray(new Point(2, -3, 0), vector);

        /* TC04 variables */
        Ray rayTC04 = new Ray(new Point(2, 0, 0), vector);

        /* TC05 variables */
        Ray rayTC05 = new Ray(new Point(3, 0, 0), vector);

        /* TC06 variables */
        Ray rayTC06 = new Ray(new Point(4, 0, 0), vector);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Ray intersects the triangle inside */
        result = triangle.findIntersections(rayTC01);
        assertEquals(1, result.size(), "ERROR: Wrong number of points");
        assertEquals(List.of(pointTC01), result, "ERROR: Ray intersects the plane");

        /* TC02: Ray not intersect the triangle and goes against edge */
        result = triangle.findIntersections(rayTC02);
        assertNull(result, "ERROR: ray shouldn't intersect, ray against edge");

        /* TC03: Ray not intersect the triangle and goes against vertex */
        result = triangle.findIntersections(rayTC03);
        assertNull(result, "ERROR: ray shouldn't intersect, ray against vertex");

        /* =============== Boundary Values Tests ================== */

        /* TC04: ray goes through edge */
        result = triangle.findIntersections(rayTC04);
        assertNull(result, "ERROR: ray shouldn't intersect, ray against vertex");

        /* TC05: ray goes through vertex */
        result = triangle.findIntersections(rayTC05);
        assertNull(result, "ERROR: ray shouldn't intersect, ray against vertex");

        /* TC06: ray goes through vertex */
        result = triangle.findIntersections(rayTC06);
        assertNull(result, "ERROR: ray shouldn't intersect, ray against vertex");
    }
}
