package bearmaps.hw4;


import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private int numOfStatesExplored;
    private double timeSpent;

    private ArrayHeapMinPQ<Vertex> pq;
    private HashMap<Vertex, WeightedEdge> edgeTo;
    private HashMap<Vertex, Double> distTo;

    private HashSet<Vertex> visited;

    private void relax(WeightedEdge e, AStarGraph<Vertex> input, Vertex end) {
        Vertex p = (Vertex) e.from();
        Vertex q = (Vertex) e.to();
        if (!visited.contains(q)) {
            double w = e.weight();
            if (distTo.get(p) + w < distTo.getOrDefault(q, Double.MAX_VALUE)) {
                distTo.put(q, distTo.get(p) + w);
                edgeTo.put(q, e);
                if (pq.contains(q)) {
                    pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                } else {
                    pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                }
            }
        }

    }

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        pq = new ArrayHeapMinPQ<>();
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        visited = new HashSet<>();
        solution = new LinkedList<>();

        edgeTo.put(start, null);
        distTo.put(start, 0.0);

        pq.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end));

        while (pq.size() != 0 && !pq.getSmallest().equals(end) && sw.elapsedTime() < timeout) {
            Vertex p = pq.removeSmallest();
            visited.add(p);
            numOfStatesExplored += 1;
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(p);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                if (sw.elapsedTime() >= timeout) {
                    break;
                }
                relax(e, input, end);
            }
        }

        if (pq.getSmallest().equals(end)) {
            Vertex last = end;
            solution.add(end);
            while (edgeTo.get(last) != null) {
                last = (Vertex) edgeTo.get(last).from();
                solution.add(0, last);
            }

            outcome = SolverOutcome.SOLVED;
            solutionWeight = distTo.get(end);
            timeSpent = sw.elapsedTime();
        } else if (pq.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
            solutionWeight = 0;
            timeSpent = sw.elapsedTime();
        } else {
            outcome = SolverOutcome.TIMEOUT;
            solutionWeight = 0;
            timeSpent = sw.elapsedTime();
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored(){
        return numOfStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }

}