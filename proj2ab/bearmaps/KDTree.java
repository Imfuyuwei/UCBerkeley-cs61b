package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private static final boolean horizontal = true;
    private static final boolean vertical = false;
    private Node root;

    private class Node {
        private Point p;
        private Node leftChild;       // Indicate left when orientation is horizontal, down when orientation is vertical.
        private Node rightChild;      // Indicate right when orientation is horizontal, up when orientation is vertical.
        private boolean orientation;  // Indicate whether the children will be divided horizontally or vertically.

        private Node (Point pt, boolean o) {
            this.p = pt;
            this.orientation = o;
        }
    }

    /** Insert a new node whose point is p into a KDTree whose root is node n, then return this root. */
    private Node add(Point p, Node n) {
        if (n == null) {
            n = new Node(p, horizontal);
            return n;
        }

        // If point p already exists in this KDTree, do nothing.
        if (n.p.equals(p)) {
            return n;
        }

        if (comparePoints(p, n.p, n.orientation) < 0) {
            if (n.leftChild == null) {
                // The childNode's orientation is always the different from the parentNode's orientation.
                n.leftChild = new Node(p, !n.orientation);
            } else {
                n.leftChild = add(p, n.leftChild);
            }
        } else {
            if (n.rightChild == null) {
                n.rightChild = new Node(p, !n.orientation);
            } else {
                n.rightChild = add(p, n.rightChild);
            }
        }
        return n;
    }

    /** Compare two points based on the orientation. If horizontal, compare their x coordinates.
     *  Otherwise, compare their y coordinates. */
    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == horizontal) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }

    private double simplified_distance_to_badSide(Point goal, Node boundary_node) {
        if (boundary_node.orientation == horizontal) {
            Point hypo_pt = new Point(boundary_node.p.getX(), goal.getY());
            return Point.distance(goal, hypo_pt);
        } else {
            Point hypo_pt = new Point(goal.getX(), boundary_node.p.getY());
            return Point.distance(goal, hypo_pt);
        }
    }

    private Node nearest_node(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }

        Node good_side;
        Node bad_side;
        
        if (comparePoints(goal, n.p, n.orientation) < 0) {
            good_side = n.leftChild;
        } else {
            good_side = n.rightChild;
        }

        best = nearest_node(good_side, goal, best);
        if (comparePoints(goal, n.p, n.orientation) < 0) {
            bad_side = n.rightChild;
        } else {
            bad_side = n.leftChild;
        }
        if (bad_side == null || simplified_distance_to_badSide(goal, n) <= Point.distance(goal, best.p)) {
            best = nearest_node(bad_side, goal, best);
        }
        return best;
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node best = nearest_node(root, goal, root);
        return best.p;
    }
}



