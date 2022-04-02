package renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import com.playking.renderer.Camera;
import org.junit.jupiter.api.Test;

/**
 * Testing Camera Class.
 * @author Dan
 */
public class CameraTests {


    /**
     * Test method for {@link Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testConstructRay() {
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1),
                                   new Vector(0, -1, 0)).setDistance(10);
        String badRayErrorMessage = "ERROR: Bad ray";

        /* ============ Equivalence Partitions Tests ============== */

        /* EP01: 4X4 Inside (1,1) */
        assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)),
                     camera.setSize(8, 8).constructRay(4, 4, 1, 1), badRayErrorMessage);

        /* =============== Boundary Values Tests ================== */

        /* BV01: 3X3 Center (1,1) */
        assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)),
                     camera.setSize(6, 6).constructRay(3, 3, 1, 1), badRayErrorMessage);

        /* BV02: 3X3 Center of Upper Side (0,1) */
        assertEquals(new Ray(Point.ZERO, new Vector(0, -2, -10)),
                     camera.setSize(6, 6).constructRay(3, 3, 1, 0), badRayErrorMessage);

        /* BV03: 3X3 Center of Left Side (1,0) */
        assertEquals(new Ray(Point.ZERO, new Vector(2, 0, -10)),
                     camera.setSize(6, 6).constructRay(3, 3, 0, 1), badRayErrorMessage);

        /* BV04: 3X3 Corner (0,0) */
        assertEquals(new Ray(Point.ZERO, new Vector(2, -2, -10)),
                     camera.setSize(6, 6).constructRay(3, 3, 0, 0), badRayErrorMessage);

        /* BV05: 4X4 Corner (0,0) */
        assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)),
                     camera.setSize(8, 8).constructRay(4, 4, 0, 0), badRayErrorMessage);

        /* BV06: 4X4 Side (0,1) */
        assertEquals(new Ray(Point.ZERO, new Vector(1, -3, -10)),
                     camera.setSize(8, 8).constructRay(4, 4, 1, 0), badRayErrorMessage);
    }
}
