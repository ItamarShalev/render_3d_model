package com.playking.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.playking.primitives.Point;
import com.playking.primitives.Vector;
import org.junit.jupiter.api.Test;

/**
 * Test class to test cases in point class.
 */
class PointTest {


    @Test
    public void addVectorTest() {
        Point p1 = new Point(1, 2, 3);
        assertEquals(p1.add(new Vector(-1, -2, -3)), new Point(0, 0, 0),
                     "ERROR: Point + Vector does not work correctly");
    }

    @Test
    public void subscribeTest() {
        Point p1 = new Point(1, 2, 3);
        assertEquals(new Vector(1, 1, 1), new Point(2, 3, 4).subtract(p1),
                     "ERROR: Point - Point does not work correctly");
    }
}
