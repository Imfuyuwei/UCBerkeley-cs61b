package bearmaps;

import org.junit.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdRandom;

public class ArrayHeapMinPQTest {
    @Test
    public void testAdd() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        assertEquals(0, pq.size());
        pq.add("C", 3);
        assertEquals("C", pq.getSmallest());
        pq.add("A", 1);
        assertEquals("A", pq.getSmallest());
        pq.add("B", 2);
        assertEquals("A", pq.getSmallest());
        assertEquals(3, pq.size());
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetSmallest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.getSmallest();
        pq.add("B", 2);
        assertEquals("B", pq.getSmallest());
        pq.add("C", 3);
        assertEquals("B", pq.getSmallest());
        pq.add("A", 1);
        assertEquals("A", pq.getSmallest());
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("A", 1);
        pq.add("C", 3);
        pq.add("B", 2);
        assertEquals("A", pq.removeSmallest());
        assertEquals(2, pq.size());
        assertEquals("B", pq.removeSmallest());
        assertEquals(1, pq.size());
        assertEquals("C", pq.removeSmallest());
        assertEquals(0, pq.size());
        pq.removeSmallest();
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveSmallestException() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.removeSmallest();
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("A", 1);
        pq.add("C", 3);
        pq.add("B", 2);
        assertEquals("A", pq.getSmallest());

        pq.changePriority("A", 5);
        assertEquals("B", pq.getSmallest());

        pq.changePriority("C", 1);
        assertEquals("C", pq.getSmallest());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElement() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("A", 1);
        pq.add("C", 3);
        pq.add("B", 2);
        pq.changePriority("D", 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("A", 1);
        pq.add("C", 3);
        pq.add("A", 2);
    }

    @Test
    public void testRunTimeOfChangePriority() {
        ArrayHeapMinPQ<Integer> pq_1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i += 1) {
            pq_1.add(i, i);
        }
        Stopwatch sw_1 = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            int item = StdRandom.uniform(100000);
            double priority = StdRandom.uniform(0, 100000);
            pq_1.changePriority(item, priority);
        }
        System.out.println("Runtime Of pq_1's changePriority method is: " + sw_1.elapsedTime() +  " seconds.");

        NaiveMinPQ<Integer> pq_2 = new NaiveMinPQ<>();
        for (int i = 0; i < 100000; i += 1) {
            pq_2.add(i, i);
        }
        Stopwatch sw_2 = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            int item = StdRandom.uniform(100000);
            double priority = StdRandom.uniform(0, 100000);
            pq_2.changePriority(item, priority);
        }
        System.out.println("Runtime Of pq_2's changePriority method is: " + sw_1.elapsedTime() +  " seconds.");
    }

    @Test
    public void testRunTimeOfContains() {
        ArrayHeapMinPQ<Integer> pq_1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i += 1) {
            pq_1.add(i, i);
        }
        Stopwatch sw_1 = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            int item = StdRandom.uniform(100000);
            boolean isContained = pq_1.contains(item);
        }
        System.out.println("Runtime Of pq_1's contains method is: " + sw_1.elapsedTime() +  " seconds.");

        NaiveMinPQ<Integer> pq_2 = new NaiveMinPQ<>();
        for (int i = 0; i < 100000; i += 1) {
            pq_2.add(i, i);
        }
        Stopwatch sw_2 = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            int item = StdRandom.uniform(100000);
            boolean isContained = pq_2.contains(item);
        }
        System.out.println("Runtime Of pq_2's contains method is: " + sw_2.elapsedTime() +  " seconds.");
    }

    @Test
    public void testRunTimeOfRemoveSmallest() {
        ArrayHeapMinPQ<Integer> pq_1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000; i += 1) {
            pq_1.add(i, i);
        }
        Stopwatch sw_1 = new Stopwatch();
        while (pq_1.size() > 0) {
            pq_1.removeSmallest();
        }
        System.out.println("Runtime Of pq_1's removeSmallest method is: " + sw_1.elapsedTime() +  " seconds.");

        NaiveMinPQ<Integer> pq_2 = new NaiveMinPQ<>();
        for (int i = 0; i < 1000; i += 1) {
            pq_2.add(i, i);
        }
        Stopwatch sw_2 = new Stopwatch();
        while (pq_2.size() > 0){
            pq_2.removeSmallest();
        }
        System.out.println("Runtime Of pq_2's removeSmallest method is: " + sw_2.elapsedTime() +  " seconds.");
    }
}

