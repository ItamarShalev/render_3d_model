package geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.geometries.Tube;
import com.primitives.Point;
import com.primitives.Ray;
import com.primitives.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Testing Tube.
 */
public class TubeTests {


    /**
     * Test method for {@link Tube#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        Point p0 = new Point(0, 0, 0.5);
        Vector dir = new Vector(0, 0, 1);
        Ray ray = new Ray(p0, dir);
        Tube tube = new Tube(ray, 2);
        Vector exceptedVector = new Vector(0, -1, 0);
        Point point = new Point(0, -2, 2);
        Point point1 = new Point(0, -2, 0.5);

        /* TC01: normal situation normal vector to a point on the tube not paralleled to p0. */
        assertTrue(exceptedVector.isSameNormal(tube.getNormal(point)),
                   "ERROR: getNormal() doesn't work correctly.");

        /* TC02: edge situation normal vector to a point on the tube paralleled to p0. */
        assertTrue(exceptedVector.isSameNormal(tube.getNormal(point1)),
                   "ERROR: getNormal() doesn't work correctly when it's in the edge case.");
    }

    @Test
    @SuppressWarnings("checkstyle:MethodLength")
    void testFindIntersection() {
        Tube tube1 = new Tube(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)), 1d);
        Vector vAxis = new Vector(0, 0, 1);
        Tube tube2 = new Tube(new Ray(new Point(1, 1, 1), vAxis), 1d);
        Ray ray;
        /* =========== Equivalence Partitions Tests ============== */
        /* TC01: Ray's line is outside the tube (0 points) */
        ray = new Ray(new Point(1, 1, 2), new Vector(1, 1, 0));
        assertNull(tube1.findIntersections(ray), "Must not be intersections");
        /* TC02: Ray's crosses the tube (2 points) */
        ray = new Ray(new Point(0, 0, 0), new Vector(2, 1, 1));
        List<Point> result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(0.4, 0.2, 0.2), new Point(2, 1, 1)),
                     result,
                     "Bad intersections");
        /* TC03: Ray's starts within tube and crosses the tube (1 point) */
        ray = new Ray(new Point(1, 0.5, 0.5), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1)), result, "Bad intersections");
        /* =============== Boundary Values Tests ================== */
        /* **** Group: Ray's line is parallel to the axis (0 points) */
        /* TC11: Ray is inside the tube (0 points) */
        ray = new Ray(new Point(0.5, 0.5, 0.5), vAxis);
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC12: Ray is outside the tube */
        ray = new Ray(new Point(0.5, -0.5, 0.5), vAxis);
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC13: Ray is at the tube surface */
        ray = new Ray(new Point(2, 1, 0.5), vAxis);
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC14: Ray is inside the tube and starts against axis head */
        ray = new Ray(new Point(0.5, 0.5, 1), vAxis);
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC15: Ray is outside the tube and starts against axis head */
        ray = new Ray(new Point(0.5, -0.5, 1), vAxis);
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC16: Ray is at the tube surface and starts against axis head */
        ray = new Ray(new Point(2, 1, 1), vAxis);
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC17: Ray is inside the tube and starts at axis head */
        ray = new Ray(new Point(1, 1, 1), vAxis);
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /*--- Group: Ray is orthogonal but does not begin against the axis head --- */
        /* TC21: Ray starts outside and the line is outside (0 points) */
        ray = new Ray(new Point(0, 2, 2), new Vector(1, 1, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC22: The line is tangent and the ray starts before the tube (0 points) */
        ray = new Ray(new Point(0, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC23: The line is tangent and the ray starts at the tube (0 points) */
        ray = new Ray(new Point(1, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC24: The line is tangent and the ray starts after the tube (0 points) */
        ray = new Ray(new Point(2, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC25: Ray starts before (2 points) */
        ray = new Ray(new Point(0, 0, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(0.4, 0.2, 2), new Point(2, 1, 2)),
                     result,
                     "Bad intersections");
        /* TC26: Ray starts at the surface and goes inside (1 point) */
        ray = new Ray(new Point(0.4, 0.2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 2)), result, "Bad intersections");
        /* TC27: Ray starts inside (1 point) */
        ray = new Ray(new Point(1, 0.5, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 2)), result, "Bad intersections");
        /* TC28: Ray starts at the surface and goes outside (0 points) */
        ray = new Ray(new Point(2, 1, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC29: Ray starts after */
        ray = new Ray(new Point(4, 2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC30: Ray starts before and crosses the axis (2 points) */
        ray = new Ray(new Point(1, -1, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(1, 0, 2), new Point(1, 2, 2)), result, "Bad intersections");
        /* TC31: Ray starts at the surface and goes inside and crosses the axis */
        ray = new Ray(new Point(1, 0, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 2)), result, "Bad intersections");
        /* TC32: Ray starts inside and the line crosses the axis (1 point) */
        ray = new Ray(new Point(1, 0.5, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 2)), result, "Bad intersections");
        /* TC33: Ray starts at the surface and goes outside and the line crosses the */
        /* axis (0 points) */
        ray = new Ray(new Point(1, 2, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC34: Ray starts after and crosses the axis (0 points) */
        ray = new Ray(new Point(1, 3, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC35: Ray start at the axis */
        ray = new Ray(new Point(1, 1, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 2)), result, "Bad intersections");
        /*--- Group: Ray is orthogonal to axis and begins against the axis head--- */
        /* TC41: Ray starts outside and the line is outside  */
        ray = new Ray(new Point(0, 2, 1), new Vector(1, 1, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC42: The line is tangent and the ray starts before the tube */
        ray = new Ray(new Point(0, 2, 1), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC43: The line is tangent and the ray starts at the tube */
        ray = new Ray(new Point(1, 2, 1), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC44: The line is tangent and the ray starts after the tube */
        ray = new Ray(new Point(2, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        /* TC45: Ray starts before */
        ray = new Ray(new Point(0, 0, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(0.4, 0.2, 1), new Point(2, 1, 1)),
                     result,
                     "Bad intersections");
        /* TC46: Ray starts at the surface and goes inside */
        ray = new Ray(new Point(0.4, 0.2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1)), result, "Bad intersections");
        /* TC47: Ray starts inside */
        ray = new Ray(new Point(1, 0.5, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1)), result, "Bad intersections");
        /* TC48: Ray starts at the surface and goes outside */
        ray = new Ray(new Point(2, 1, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC49: Ray starts after */
        ray = new Ray(new Point(4, 2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC50: Ray starts before and goes through the axis head */
        ray = new Ray(new Point(1, -1, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(1, 0, 1), new Point(1, 2, 1)), result, "Bad intersections");
        /* TC51: Ray starts at the surface and goes inside and goes through the axis head */
        ray = new Ray(new Point(1, 0, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 1)), result, "Bad intersections");
        /* TC52: Ray starts inside and the line goes through the axis head */
        ray = new Ray(new Point(1, 0.5, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 1)), result, "Bad intersections");
        /* TC53: Ray starts at the surface and the line goes outside and goes through the axis head */
        ray = new Ray(new Point(1, 2, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC54: Ray starts after and the line goes through the axis head */
        ray = new Ray(new Point(1, 3, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC55: Ray start at the axis head */
        ray = new Ray(new Point(1, 1, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 1)), result, "Bad intersections");
        /* **** Group: Ray's line is neither parallel nor orthogonal to the axis and */
        /* begins against axis head */
        Point p0 = new Point(0, 2, 1);
        /* TC61: Ray's line is outside the tube */
        ray = new Ray(p0, new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC62: Ray's line crosses the tube and begins before */
        ray = new Ray(p0, new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(2, 1, 2), new Point(0.4, 1.8, 1.2)),
                     result,
                     "Bad intersections");
        /* TC63: Ray's line crosses the tube and begins at surface and goes inside */
        ray = new Ray(new Point(0.4, 1.8, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1.8)), result, "Bad intersections");
        /* TC64: Ray's line crosses the tube and begins inside */
        ray = new Ray(new Point(1, 1.5, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1.5)), result, "Bad intersections");
        /* TC65: Ray's line crosses the tube and begins at the axis head */
        ray = new Ray(new Point(1, 1, 1), new Vector(0, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 2)), result, "Bad intersections");
        /* TC66: Ray's line crosses the tube and begins at surface and goes outside */
        ray = new Ray(new Point(2, 1, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC67: Ray's line is tangent and begins before */
        ray = new Ray(p0, new Vector(0, 2, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC68: Ray's line is tangent and begins at the tube surface */
        ray = new Ray(new Point(1, 2, 1), new Vector(1, 0, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC69: Ray's line is tangent and begins after */
        ray = new Ray(new Point(2, 2, 1), new Vector(1, 0, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* **** Group: Ray's line is neither parallel nor orthogonal to the axis and */
        /* does not begin against axis head */
        double sqrt2 = Math.sqrt(2);
        double denomSqrt2 = 1 / sqrt2;
        double value1 = 1 - denomSqrt2;
        double value2 = 1 + denomSqrt2;
        /* TC71: Ray's crosses the tube and the axis */
        ray = new Ray(new Point(0, 0, 2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(value1, value1, 2 + value1),
                             new Point(value2, value2, 2 + value2)), result, "Bad intersections");
        /* TC72: Ray's crosses the tube and the axis head */
        ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(value1, value1, value1), new Point(value2, value2, value2)),
                     result,
                     "Bad intersections");
        /* TC73: Ray's begins at the surface and goes inside */
        /* TC74: Ray's begins at the surface and goes inside crossing the axis */
        ray = new Ray(new Point(value1, value1, 2 + value1), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, 2 + value2)), result, "Bad intersections");
        /* TC75: Ray's begins at the surface and goes inside crossing the axis head */
        ray = new Ray(new Point(value1, value1, value1), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, value2)), result, "Bad intersections");
        /* TC76: Ray's begins inside and the line crosses the axis */
        ray = new Ray(new Point(0.5, 0.5, 2.5), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, 2 + value2)), result, "Bad intersections");
        /* TC77: Ray's begins inside and the line crosses the axis head */
        ray = new Ray(new Point(0.5, 0.5, 0.5), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, value2)), result, "Bad intersections");
        /* TC78: Ray's begins at the axis */
        ray = new Ray(new Point(1, 1, 3), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, 2 + value2)), result, "Bad intersections");
        /* TC79: Ray's begins at the surface and goes outside */
        ray = new Ray(new Point(2, 1, 2), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC80: Ray's begins at the surface and goes outside and the line crosses the axis */
        ray = new Ray(new Point(value2, value2, 2 + value2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        /* TC81: Ray's begins at the surface and goes outside and the line crosses the axis head */
        ray = new Ray(new Point(value2, value2, value2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
    }
}
