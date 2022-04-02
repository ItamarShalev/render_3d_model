package com.playking.unittests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.playking.geometries.Geometries;
import com.playking.geometries.Plane;
import com.playking.geometries.Sphere;
import com.playking.geometries.Triangle;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GeometriesTest {

    @Test
    void testFindIntersections() {

        List<Point> result;

        /* TC01 variables */
        Sphere sphereTC01 = new Sphere(new Point(2, 0, 0), 1);
        Plane planeTC01 = new Plane(new Point(-5, 0, 0), new Point(0, 0, 4), new Point(0, -4, 0));
        Triangle triangleTC01 = new Triangle(new Point(6, 0, 0), new Point(4, 0, 0),
                                             new Point(2.38107, 5.79587, 0));
        Geometries geometriesTC01 = new Geometries(sphereTC01, planeTC01, triangleTC01);
        Ray rayTC01 = new Ray(new Point(0, 4, 0),
                              new Vector(2.732115775904636, -4.492163336027315, 0.470937088518219));

        /* TC02 variables */
        Geometries geometriesTC02 = new Geometries();
        Ray rayTC02 = new Ray(new Point(0, 4, 0),
                              new Vector(2.732115775904636, -4.492163336027315, 0.470937088518219));

        /* TC03 variables */
        Triangle triangleTC03 = new Triangle(new Point(3.394228564552314, -3.933133206886659, 0),
                                             new Point(5.490624249175983, -2.801760059740879, 0),
                                             new Point(2.37669835688477, -0.470644059061225,
                                                       0.797867481221467));
        Geometries geometriesTC03 = new Geometries(sphereTC01, planeTC01, triangleTC03);
        Ray rayTC03 = new Ray(new Point(0, 4, 0),
                              new Vector(2.732115775904636, -4.492163336027315, 0.470937088518219));

        /* TC04 variables */
        Sphere sphereTC04 = new Sphere(new Point(1, 0, 0), 1);
        Plane planeTC04 = new Plane(new Point(0, 0, 4), new Point(0, 0, 0), new Point(-3, 0, 0));
        Triangle triangleTC04 = new Triangle(new Point(0, -1, 0), new Point(0, -2, 0),
                                             new Point(0, -2, 2));
        Geometries geometriesTC04 = new Geometries(sphereTC04, planeTC04, triangleTC04);
        Ray rayTC04 = new Ray(new Point(4, 0, 0), new Vector(1, 0, 0));

        /* TC05 variables */
        Ray rayTC05 = new Ray(new Point(4, 0, 0), new Vector(-40, -1, -1));


        /* ============ Equivalence Partitions Tests ============== */
        /* TC01: Not all shapes are intersected with the ray */
        result = geometriesTC01.findIntersections(rayTC01);
        assertEquals(3, result.size(), "ERROR: Wrong number of points");

        /* =============== Boundary Values Tests ================== */

        /* TC2: Collection is empty */
        assertNull(geometriesTC02.findIntersections(rayTC02),
                   "ERROR: Collection of shapes is empty");

        /* TC3: All shapes intersected with the ray */
        result = geometriesTC03.findIntersections(rayTC03);

        assertEquals(4, result.size(), "ERROR: Wrong number of points");

        /* TC4: There is no shape is intersected with Ray */
        assertNull(geometriesTC04.findIntersections(rayTC04),
                   "ERROR: There is no shape is intersected with Ray");

        // TC5: There is only 1 shape is intersected
        result = geometriesTC04.findIntersections(rayTC05);
        assertEquals(2, result.size(), "ERROR: Wrong number of points");
    }
}
