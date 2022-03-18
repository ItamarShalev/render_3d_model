package com.playking.geometries.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.geometries.Plane;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.List;
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
                     "ERROR: Plane constructor should throw exception "
                     + "when some points on the same line");

        /* TC02: some points are the same. */
        assertThrows(IllegalArgumentException.class, () -> new Plane(xy2, xy2, z2),
                     "ERROR: Plane constructor should throw exception "
                     + "when some points are the same");
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

    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // Ray is neither orthogonal nor parallel to the plane
        Plane plane = new Plane(new Point(2, 0, 0), new Point(0, 2, 0), new Point(0, 0, 2));
        Point p = new Point(-1.142857142857143, 3.142857142857143, 0);

        Ray ray = null;
        Vector v = null;

        // TC01: Ray intersects the plane (1 point)
        ray = new Ray(new Point(0, 4, 0), new Vector(-4, -3, 0));
        List<Point> result = plane.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p), result, "Ray intersects the plane");

        // TC02: Ray does not intersect the plane (0 points)
        ray = new Ray(new Point(-4, 1, 0), new Vector(-4, -3, 0));

        assertNull(plane.findIntersections(ray), "Ray starts after plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane

        // TC11: the ray included in the plane (0 points)
        p = new Point(0.657087677596453, -1.051994262427145, 2.394906584830692);
        v = new Vector(-1.706405988359289, -0.621475834191163, 2.327881822550452);
        ray = new Ray(p, v);

        assertNull(plane.findIntersections(ray), "Ray included in plane");

        // TC12: the ray is not included in the plane (0 points)
        ray = new Ray(new Point(0, 4, 0), new Vector(-2, 0, 2));

        assertNull(plane.findIntersections(ray), "Ray parallel with plane");

        // **** Group: Ray is orthogonal to the plane
        // TC13: – according to p0 (before) (1 point)
        p = new Point(0.33333333333333333, 1.33333333333333333, 0.33333333333333333);
        ray = new Ray(new Point(0, 1, 0), new Vector(1, 1, 1));
        result = plane.findIntersections(ray);

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p), result, "Ray intersects the plane");

        // TC14: – according to p0 (in) (0 points)
        ray = new Ray(new Point(0, 2, 0), new Vector(1, 1, 1));

        assertNull(plane.findIntersections(ray), "Ray starts at plane");

        // TC15: – according to p0 (after the plane) (0 points)
        ray = new Ray(new Point(0.327523993905339, -0.403942564427651, 4.408486769939391),
                      new Vector(1, 1, 1));

        assertNull(plane.findIntersections(ray), "Ray starts after plane");

        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane
        //(p0 is in the plane, but not the ray) (0 points)
        ray = new Ray(new Point(-2.227529894658485, -0.144919886756481, 4.372449781414965),
                      new Vector(0, 1, 1));

        assertNull(plane.findIntersections(ray), "Ray starts at plane");

        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane (Q) (0 points)
        ray = new Ray(new Point(2, 0, 0), new Vector(0, 1, 1));
        assertNull(plane.findIntersections(ray), "Ray starts at plane");
    }
}
