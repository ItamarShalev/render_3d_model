package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import java.util.List;

/**
 * Interface of intersection of light rays with objects.
 */
public abstract class Intersect {

    /**
     * Find all Intersections points from the ray.
     * @param ray MUST be not null, The ray tested at the intersection of the object
     * @return List of points that intersection with the object
     */
    public abstract List<Point> findIntersections(Ray ray);


    /**
     * GeoPoint contains the geometry and the point on the geometry.
     */
    public static class GeoPoint {
        public final Geometry geometry;
        public final Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GeoPoint)) {
                return false;
            }
            GeoPoint other = (GeoPoint)obj;
            boolean sameGeometryType = this.geometry.getClass().equals(other.geometry.getClass());
            return sameGeometryType && this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + '}';
        }
    }
}
