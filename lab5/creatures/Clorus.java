package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * An implementation of a a fierce blue-colored predator named clorus.
 *
 * @author Yuwei Fu
 */
public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }


    /**
     * Should return a color with red = 34, blue = 231, and green = 0.
     */
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    /**
     * Attack another creature and gain that creature's energy.
     */
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Cloruses should lose 0.03 units of energy when moving. No negative energy is allowed
     */
    public void move() {
        energy -= 0.03;
        if (energy < 0) {
            energy = 0;
        }
    }


    /**
     * Cloruses gain 0.01 energy when staying. No maximum energy limit.
     */
    public void stay() {
        energy += 0.01;
    }

    /**
     * Cloruses and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Clorus.
     */
    public Clorus replicate() {
        double energy_to_offspring = this.energy * 0.5;
        this.energy -= energy_to_offspring;
        Clorus offspring = new Clorus(energy_to_offspring);
        return offspring;
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If there are no empty squares, the Clorus will STAY (even if there are Plips nearby
     * they could attack since plip squares do not count as empty squares).
     * 2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one, it will REPLICATE
     * to a random empty square.
     * 4. Otherwise, the Clorus will MOVE to a random empty square.
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipsNeighbors = new ArrayDeque<>();

        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.add(d);
            } else if (neighbors.get(d).name().equals("plip")) {
                plipsNeighbors.add(d);
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (plipsNeighbors.size() > 0) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plipsNeighbors));
        }

        // Rule 3
        if (this.energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }


        // Rule 4
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
    }
}