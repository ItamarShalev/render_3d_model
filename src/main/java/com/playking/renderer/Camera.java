package com.playking.renderer;

import static com.playking.primitives.Util.alignZero;
import static com.playking.primitives.Util.isZero;

import com.playking.geometries.Intersect;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.LinkedList;
import java.util.List;

public class Camera {
    private final Point p0;
    private final Vector vectorTo;
    private final Vector vectorUp;
    private final Vector vectorRight;
    private int distance;
    private double width;
    private double height;

    /**
     * Constructor to create new Camera.
     * @param p0 the center point
     * @param vectorTo MUST be orthogonal to vectorUp
     * @param vectorUp MUST be orthogonal to vectorTo
     * @throws IllegalArgumentException if vectorTo isn't orthogonal to vectorUp
     */
    public Camera(Point p0, Vector vectorTo, Vector vectorUp) throws IllegalArgumentException {
        this.p0 = p0;
        this.vectorTo = vectorTo.normalize();
        this.vectorUp = vectorUp.normalize();
        if (!isZero(vectorTo.dotProduct(vectorUp))) {
            throw new IllegalArgumentException("ERROR: vectorTo and vectorUp must be orthogonal");
        }
        this.vectorRight = vectorTo.crossProduct(vectorUp).normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getVectorTo() {
        return vectorTo;
    }

    public Vector getVectorUp() {
        return vectorUp;
    }

    public Vector getVectorRight() {
        return vectorRight;
    }

    public int getDistance() {
        return distance;
    }

    /**
     * Set the distance from the camera to the view plane.
     * @param distance the distance from the camera to the view plane, MUST be not negative or
     *     zero
     * @return the camera itself
     */
    public Camera setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    /**
     * Set the width and the height of the view plane.
     * @param width the width of the view plane, MUST be not negative or zero
     * @param height the height of the view plane, MUST be not negative or zero
     * @return the camera itself
     */
    public Camera setSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Find a ray from p0 to the center of the pixel from the given resolution.
     * @param nX the number of the rows
     * @param nY the number of the columns
     * @param j column
     * @param i row
     * @return ray from p0 the center to the center of the pixel in row i column j
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Vector dir;
        Point pointCenter, pointCenterPixel;
        double ratioY, ratioX, yI, xJ;

        pointCenter = p0.add(vectorTo.scale(distance));
        ratioY = alignZero(height / nY);
        ratioX = alignZero(width / nX);

        pointCenterPixel = pointCenter;
        yI = alignZero(-1 * (i - (nY - 1) / 2d) * ratioY);
        xJ = alignZero((j - (nX - 1) / 2d) * ratioX);
        if (!isZero(xJ)) {
            pointCenterPixel = pointCenterPixel.add(vectorRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pointCenterPixel = pointCenterPixel.add(vectorUp.scale(yI));
        }
        dir = pointCenterPixel.subtract(p0);

        return new Ray(p0, dir);
    }

    public List<Point> findIntersections(int nX, int nY, Intersect intersect) {
        Ray ray;
        List<Point> result = new LinkedList<>();
        List<Point> intersectionPoints;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ray = constructRay(nX, nY, i, j);
                intersectionPoints = intersect.findIntersections(ray);
                if (intersectionPoints != null) {
                    result.addAll(intersectionPoints);
                }
            }
        }
        return result.isEmpty() ? null : result;
    }
}