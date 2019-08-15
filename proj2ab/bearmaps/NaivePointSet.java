package bearmaps;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class NaivePointSet implements PointSet {
    private HashSet<Point> pt_set;

    public NaivePointSet(List<Point> points) {
        pt_set = new HashSet<>();
        pt_set.addAll(points);
    }

    @Override
    public Point nearest(double x, double y) {
        Point this_point = new Point(x, y);
        double shortest_distance = Double.MAX_VALUE;
        Iterator it = pt_set.iterator();
        Point nearest_point = (Point) it.next();
        for (Point pt : pt_set) {
            double distance = Point.distance(pt, this_point);
            if (distance < shortest_distance) {
                shortest_distance = distance;
                nearest_point = pt;
            }
        }
        return nearest_point;
    }
}