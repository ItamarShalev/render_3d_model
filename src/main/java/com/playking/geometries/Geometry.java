package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Vector;

public interface Geometry {

    Vector getNormal(Point point);
}
