package geometries;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playking.geometries.Tube;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
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
}
