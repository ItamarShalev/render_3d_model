package com.playking.unittests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.playking.geometries.Cylinder;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
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
        assertEquals(exceptVectorSide, cylinder.getNormal(sidePoint),
                     "ERROR: getNormal() point on the side doesn't work correctly.");

        /* TC02: point the top base. */
        assertEquals(exceptVectorTopBase, cylinder.getNormal(topBasePoint),
                     "ERROR: getNormal() point on the top base doesn't work correctly.");

        /* TC02: point the bottom base. */
        assertEquals(exceptVectorBottomBase, cylinder.getNormal(bottomBasePoint),
                     "ERROR: getNormal() point on the top base doesn't work correctly.");

        /* =============== Boundary Values Tests ================== */

        /* TC02: point in the center the top base. */
        assertEquals(exceptVectorCenterTopBase, cylinder.getNormal(topBaseCenterPoint),
                     "ERROR: getNormal() point in the center on the top base "
                     + "doesn't work correctly.");

        /* TC02: point in the center the bottom base. */
        assertEquals(exceptVectorCenterBottomBase, cylinder.getNormal(bottomBaseCenterPoint),
                     "ERROR: getNormal() point in the center on the top base "
                     + "doesn't work correctly.");
    }
}
