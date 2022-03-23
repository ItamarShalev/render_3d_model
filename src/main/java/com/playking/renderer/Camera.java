package com.playking.renderer;

import static com.playking.primitives.Util.alignZero;
import static com.playking.primitives.Util.isZero;

import com.playking.geometries.Intersect;
import com.playking.primitives.Axis;
import com.playking.primitives.Color;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

public class Camera {
    private static final double ERROR_VALUE_DOUBLE = -1d;
    private static final int ERROR_VALUE_INT = -1;
    private Vector vectorTo;
    private Vector vectorUp;
    private Vector vectorRight;
    private Point p0;
    private int distance;
    private double width;
    private double height;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

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
        distance = ERROR_VALUE_INT;
        width = ERROR_VALUE_DOUBLE;
        height = ERROR_VALUE_DOUBLE;
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


    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }


    /**
     * Helper to check if some resource is missing.
     * @throws MissingResourceException if some resource is missing
     */
    private void checkAndThrowIfMissingResources() throws MissingResourceException {
        String errorMessage = "ERROR: Missing resource name: ";
        String key = "";

        if (rayTracer == null) {
            throw new MissingResourceException(errorMessage + "rayTracer",
                                               RayTracerBase.class.getName(), key);
        }
        if (imageWriter == null) {
            throw new MissingResourceException(errorMessage + "imageWriter",
                                               ImageWriter.class.getName(), key);
        }
        if (p0 == null) {
            throw new MissingResourceException(errorMessage + "p0", Point.class.getName(), key);
        }
        if (isZero(height - ERROR_VALUE_DOUBLE)) {
            throw new MissingResourceException(errorMessage + "height", double.class.getName(),
                                               key);
        }
        if (isZero(width - ERROR_VALUE_DOUBLE)) {
            throw new MissingResourceException(errorMessage + "width", double.class.getName(), key);
        }
        if (distance == ERROR_VALUE_INT) {
            throw new MissingResourceException(errorMessage + "distance", int.class.getName(), key);
        }
    }

    /**
     * Helper for {@link #rotation(double, Axis)}.
     * @param vector the vector to rotate
     * @param radians an angle in radians
     * @return the result vector after the rotation
     */
    private Vector rotationAxisZ(Vector vector, double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double axisX = vector.getX() * cos - vector.getY() * sin;
        double axisY = vector.getX() * sin + vector.getY() * cos;
        return new Vector(axisX, axisY, vector.getZ());
    }

    /**
     * Helper for {@link #rotation(double, Axis)}.
     * @param vector the vector to rotate
     * @param radians an angle in radians
     * @return the result vector after the rotation
     */
    private Vector rotationAxisY(Vector vector, double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double axisX = vector.getX() * cos + vector.getZ() * sin;
        double axisZ = vector.getZ() * cos - vector.getX() * sin;
        return new Vector(axisX, vector.getY(), axisZ);
    }

    /**
     * Helper for {@link #rotation(double, Axis)}.
     * @param vector the vector to rotate
     * @param radians an angle in radians
     * @return the result vector after the rotation
     */
    private Vector rotationAxisX(Vector vector, double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double axisY = vector.getY() * cos - vector.getZ() * sin;
        double axisZ = vector.getY() * sin + vector.getZ() * cos;
        return new Vector(vector.getX(), axisY, axisZ);
    }

    /**
     * Rotating the camera by degree in radians.
     * @param radians the degrees in radians
     * @param axis to which axis to rotate
     * @return the camera itself
     */
    public Camera rotation(double radians, Axis axis) {
        switch (axis) {
            case X:
                vectorTo = rotationAxisX(vectorTo, radians);
                vectorUp = rotationAxisX(vectorUp, radians);
                break;
            case Y:
                vectorTo = rotationAxisY(vectorTo, radians);
                vectorUp = rotationAxisY(vectorUp, radians);
                break;
            case Z:
                vectorTo = rotationAxisZ(vectorTo, radians);
                vectorUp = rotationAxisZ(vectorUp, radians);
                break;
        }
        vectorRight = vectorTo.crossProduct(vectorUp);
        return this;
    }

    /**
     * Shift the camera to the direction of the vector with the distance.
     * @param vector direction with distance
     * @return the camera itself
     */
    public Camera shift(Vector vector) {
        p0 = p0.add(vector);
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

    /**
     * Find all the intersection points with the plan view and geometry.
     * @param nX row count pixels
     * @param nY column count pixels
     * @param intersect the intersect-able object with the rays from the view plane
     * @return list of all the points that intersect, if there is no intersect point return null
     */
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

    public void renderImage() throws MissingResourceException {
        checkAndThrowIfMissingResources();
        Ray ray;
        Color color;
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                ray = constructRay(nX, nY, i, j);
                color = rayTracer.traceRay(ray);
                imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * Print grid with line as color param.
     * @param interval how many unit size to color (in height and width)
     * @param color the color to color the grid
     * @throws MissingResourceException if some resource is missing
     */
    public void printGrid(int interval, Color color) throws MissingResourceException {
        checkAndThrowIfMissingResources();
        boolean isOnTheLine = true;
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                isOnTheLine = i % interval == 0 || j % interval == 0;
                if (isOnTheLine) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        imageWriter.writeToImage();
    }

    /**
     * Actual write the image.
     * @throws MissingResourceException if some resource is missing
     */
    public void writeToImage() throws MissingResourceException {
        checkAndThrowIfMissingResources();
        imageWriter.writeToImage();
    }
}
