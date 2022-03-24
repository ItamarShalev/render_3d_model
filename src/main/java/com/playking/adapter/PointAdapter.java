package com.playking.adapter;

import com.playking.primitives.Point;

public class PointAdapter implements Adapter<Point> {

    public static Point parsePoint(String data) {
        return new Point(Double3Adapter.parseDouble3(data));
    }

    @Override
    public Point build() {
        return null;
    }
}
