package bearmaps;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

public class KDTreeTest {
    private static Random rd = new Random(100);

    private Point generateRandomPoint() {
        double x = rd.nextDouble();
        double y = rd.nextDouble();
        return new Point(x, y);
    }

    private List<Point> generateNpoints(int n) {
        List<Point> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point p = generateRandomPoint();
            res.add(p);
        }
        return res;
    }

    private void test_Npoints_Qqueries(int N, int Q) {
        List<Point> points = generateNpoints(N);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = generateNpoints(Q);

        for (Point p : queries) {
            Point actual = nps.nearest(p.getX(), p.getY());
            Point expected = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    private void test_Npoints_Qqueries_runtime(int N, int Q) {
        List<Point> points = generateNpoints(N);
        List<Point> queries = generateNpoints(Q);

        Stopwatch nps_sw = new Stopwatch();
        NaivePointSet nps = new NaivePointSet(points);
        for (Point p : queries) {
            Point actual = nps.nearest(p.getX(), p.getY());
        }
        System.out.println("For " + N + " points and " + Q +
                " queries, NaivePointSet's runtime is: " + nps_sw.elapsedTime() + " seconds.");

        Stopwatch kd_sw = new Stopwatch();
        KDTree kd = new KDTree(points);
        for (Point p : queries) {
            Point actual = kd.nearest(p.getX(), p.getY());
        }
        System.out.println("For " + N + " points and " + Q +
                " queries, KDTree's runtime is: " + kd_sw.elapsedTime() + " seconds.");
    }

    @Test
    public void testNearest() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        Point p8 = new Point(2.01, 2.01);


        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7, p8));
        Point expected = kd.nearest(1, 2.01);
        assertEquals(expected, p8);
    }


    @Test
    public void test_100000points_1000queries_runtime() {
        test_Npoints_Qqueries_runtime(100000, 1000);
    }

    @Test
    public void test_10000points_500queries() {
        int num_of_points = 10000;
        int num_of_queries = 500;
        test_Npoints_Qqueries(num_of_points, num_of_queries);
    }

    @Test
    public void test_1000points_10queries() {
        int num_of_points = 1000;
        int num_of_queries = 100;
        test_Npoints_Qqueries(num_of_points, num_of_queries);
    }

    @Test
    public void testNearest_regular_case() {
        List<Point> points = new ArrayList<>();
        List<Point> queries = new ArrayList<>();
        for (double i = 0; i < 1000; i++) {
            Point p_1 = new Point(i, i * i);
            Point p_2 = new Point(i, Math.abs(i));
            points.add(p_1);
            queries.add(p_2);
        }


        KDTree kd = new KDTree(points);
        NaivePointSet nps = new NaivePointSet(points);

        for (Point p : queries) {
            Point expected  = kd.nearest(p.getX(), p.getY());
            Point actual = nps.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }
}

