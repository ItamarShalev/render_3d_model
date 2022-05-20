package geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.geometries.Cylinder;
import com.primitives.Point;
import com.primitives.Ray;
import com.primitives.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Testing Cylinder.
 */
public class CylinderTests {

    /**
     * Test method for {@link Cylinder#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {

        Vector dir = new Vector(0, 0, 1);
        Point p0 = new Point(0, 0, -1);
        Ray axisRay = new Ray(p0, dir);
        Cylinder cylinder = new Cylinder(axisRay, 1, 2);

        Point sidePoint = new Point(0, 1, 0);
        Vector exceptVectorSide = new Vector(0, 2, 0).normalize();

        Point topBaseCenterPoint = new Point(0, 0, 1);
        Vector exceptVectorCenterTopBase = new Vector(0, 0, 2).normalize();

        Point bottomBaseCenterPoint = new Point(0, 0, -1);
        Vector exceptVectorCenterBottomBase = new Vector(0, 0, -2).normalize();

        Point topBasePoint = new Point(-0.5, 0, 1);
        Vector exceptVectorTopBase = new Vector(0, 0, 1);

        Point bottomBasePoint = new Point(0.5, 0, -1);
        Vector exceptVectorBottomBase = new Vector(0, 0, -1);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: point on the sidePoint. */
        assertEquals(exceptVectorSide,
                     cylinder.getNormal(sidePoint),
                     "ERROR: getNormal() point on the side doesn't work correctly.");

        /* TC02: point the top base. */
        assertEquals(exceptVectorTopBase,
                     cylinder.getNormal(topBasePoint),
                     "ERROR: getNormal() point on the top base doesn't work correctly.");

        /* TC03: point the bottom base. */
        assertEquals(exceptVectorBottomBase,
                     cylinder.getNormal(bottomBasePoint),
                     "ERROR: getNormal() point on the top base doesn't work correctly.");

        /* =============== Boundary Values Tests ================== */

        /* TC04: point in the center the top base. */
        assertEquals(exceptVectorCenterTopBase,
                     cylinder.getNormal(topBaseCenterPoint),
                     "ERROR: getNormal() point in the center on the top base " +
                     "doesn't work correctly.");

        /* TC05: point in the center the bottom base. */
        assertEquals(exceptVectorCenterBottomBase,
                     cylinder.getNormal(bottomBaseCenterPoint),
                     "ERROR: getNormal() point in the center on the top base " +
                     "doesn't work correctly.");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    void findIntersections() {
        Ray ray = new Ray(new Point(2, 0, 0), new Vector(0, 0, 1));
        Cylinder cylinder = new Cylinder(ray, 1d, 2d);

        /* ============ Equivalence Partitions Tests ==============*/

        /* TC01 ray is outside and parallel to the cylinder's ray */

        ray = new Ray(new Point(5, 0, 0), new Vector(0, 0, 1));
        List<Point> result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");


        /*TC02 ray starts inside and parallel to the cylinder's ray*/

        ray = new Ray(new Point(2.5, 0, 1), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.5, 0, 2)), result, "Bad intersection point");

        /*TC03 ray starts outside and parallel to the cylinder's ray and crosses the cylinder*/

        ray = new Ray(new Point(2.5, 0, -1), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.5, 0, 0), new Point(2.5, 0, 2)),
                     result,
                     "Bad intersection point");

        /*TC04 ray starts from outside and crosses the cylinder*/

        ray = new Ray(new Point(-2, 0, 0.5), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5), new Point(1, 0, 0.5)),
                     result,
                     "Bad intersection points");

        /*TC05 ray starts from inside and crosses the cylinder*/
        ray = new Ray(new Point(1.5, 0, 0.5), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");

        /*TC06 ray starts from outside the cylinder and doesn't cross the cylinder*/
        ray = new Ray(new Point(5, 0, 0), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");

        /*TC07 ray starts from outside and crosses base and surface*/
        ray = new Ray(new Point(1, 0, -1), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1), new Point(2, 0, 0)),
                     result,
                     "Bad intersection points");

        /*TC08 ray starts from outside and crosses surface and base*/
        ray = new Ray(new Point(4, 0, 2), new Vector(-1, 0, -1));
        result = cylinder.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1), new Point(2, 0, 0)),
                     result,
                     "Bad intersection points");


        /* =============== Boundary Values Tests ================== */

        /*TC09 ray is on the surface of the cylinder (not bases)*/
        ray = new Ray(new Point(3, 0, 0), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");

        /*TC10 ray is on the base of the cylinder and crosses 2 times*/
        ray = new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");

        /*TC11 ray is in center of the cylinder*/
        ray = new Ray(new Point(2, 0, 0), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 2)), result, "Bad intersection points");

        /*TC12 ray is perpendicular to cylinder's ray and starts from outside the tube*/
        ray = new Ray(new Point(-2, 0, 0.5), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5), new Point(1, 0, 0.5)),
                     result,
                     "Bad intersection points");

        /*TC13 ray is perpendicular to cylinder's ray and starts from inside cylinder (not center)*/
        ray = new Ray(new Point(1.5, 0, 0.5), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");

        /*TC14 ray is perpendicular to cylinder's ray and starts from the center of cylinder*/
        ray = new Ray(new Point(2, 0, 0.5), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");

        /*TC15 ray is perpendicular to cylinder and starts from the surface of cylinder to inside*/
        ray = new Ray(new Point(1, 0, 0.5), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");

        /*TC16 ray is perpendicular to cylinder and starts from the surface of cylinder to outside*/
        ray = new Ray(new Point(3, 0, 0), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");

        /*TC17 ray starts from the surface to outside*/
        ray = new Ray(new Point(3, 0, 0), new Vector(1, 1, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");

        /*TC18 ray starts from the surface to inside*/
        ray = new Ray(new Point(3, 0, 0.5), new Vector(-1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 0.5)), result, "Bad intersection point");

        /*TC19 ray starts from the center*/
        ray = new Ray(new Point(2, 0, 0), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1)), result, "Bad intersection point");

        /*TC20 prolongation of ray crosses cylinder*/
        ray = new Ray(new Point(3, 0, 0), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");

        /*TC21 ray is on the surface starts before cylinder*/
        ray = new Ray(new Point(3, 0, -1), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");

        /*TC22 ray is on the surface starts at bottom's base*/
        ray = new Ray(new Point(3, 0, 0), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");

        /*TC23 ray is on the surface starts on the surface*/
        ray = new Ray(new Point(3, 0, 1), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");

        /*TC24 ray is on the surface starts at top's base*/
        ray = new Ray(new Point(3, 0, 2), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "Wrong number of points");
    }
}
