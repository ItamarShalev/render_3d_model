package com.playking.unittests.geometries;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.playking.geometries.Polygon;
import com.playking.primitives.Point;
import com.playking.primitives.Vector;
import org.junit.jupiter.api.Test;


/**
 * Testing Polygons.
 * @author Dan
 */
public class PolygonTests {

    /**
     * Test method for {@link Polygon#Polygon(Point...)}.
     */
    @Test
    public void testConstructor() {
        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Correct concave quadrangular with vertices in correct order */
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("ERROR: Failed constructing a correct polygon");
        }

        /* TC02: Wrong vertices order */
        assertThrows(IllegalArgumentException.class,
                     () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0),
                                       new Point(-1, 1, 1)),
                     "ERROR: Constructed a polygon with wrong order of vertices");

        /* TC03: Not in the same plane */
        assertThrows(IllegalArgumentException.class,
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                       new Point(0, 2, 2)),
                     "ERROR: Constructed a polygon with vertices that are not in the same plane");

        /* TC04: Concave quadrangular */
        assertThrows(IllegalArgumentException.class,
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                       new Point(0.5, 0.25, 0.5)),
                     "ERROR: Constructed a concave polygon");

        /* =============== Boundary Values Tests ================== */

        /* TC05: Vertex on a side of a quadrangular */
        assertThrows(IllegalArgumentException.class,
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                       new Point(0, 0.5, 0.5)),
                     "ERROR: Constructed a polygon with vertix on a side");

        /* TC06: Last point = first point */
        assertThrows(IllegalArgumentException.class,
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                       new Point(0, 0, 1)),
                     "ERROR: Constructed a polygon with vertice on a side");

        /* TC07: Co-located points */
        assertThrows(IllegalArgumentException.class,
                     () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                       new Point(0, 1, 0)),
                     "ERROR: Constructed a polygon with vertice on a side");
    }

    /**
     * Test method for {@link Polygon#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {

        Point a = new Point(0, 0, 1);
        Point b = new Point(1, 0, 0);
        Point c = new Point(0, 1, 0);
        Point d = new Point(-1, 1, 1);
        Polygon pl = new Polygon(a, b, c, d);
        double sqrt3 = Math.sqrt(1d / 3);
        Vector expectedVector = new Vector(sqrt3, sqrt3, sqrt3);


        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: Check normal in specific point. */
        assertTrue(expectedVector.isSameNormal(pl.getNormal(a)),
                   "ERROR: getNormal() doesn't work correctly.");
    }
}
