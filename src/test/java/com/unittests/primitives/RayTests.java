package primitives;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.primitives.Point;
import com.primitives.Ray;
import com.primitives.Vector;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Testing Ray.
 */
public class RayTests {

    /**
     * Test method for {@link Ray#findClosestPoint(List)}.
     */
    @Test
    public void testFindClosestPoint() {
        List<Point> points;
        String errorMessage = "ERROR: Doesn't work well, it should was the (6, 2, 1) point";
        Ray ray = new Ray(new Point(5, 2, 1), new Vector(-7, -1, 8));
        /* Point a is the closest */
        Point a = new Point(6, 2, 1);
        Point b = new Point(9, 2, 3);
        Point c = new Point(10, -40, 1);

        /* ============ Equivalence Partitions Tests ============== */

        /* TC01: The closest point is the mid-point */
        points = Arrays.asList(b, a, c);
        assertEquals(a, ray.findClosestPoint(points), errorMessage);

        /* =============== Boundary Values Tests ================== */

        /* TC02: The closest point is the start point */
        points = Arrays.asList(a, c, b);
        assertEquals(a, ray.findClosestPoint(points), errorMessage);


        /* TC03: The closest point is the end point */
        points = Arrays.asList(b, c, a);
        assertEquals(a, ray.findClosestPoint(points), errorMessage);


        /* TC03: Given null list should return null */
        assertNull(ray.findClosestPoint(null), "ERROR: Doesn't work well, it should return null");
    }
}
