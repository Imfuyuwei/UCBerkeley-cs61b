package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<HeapNode> heap;
    private HashMap<T, Integer> items;

    private class HeapNode {
        private T item;
        private double priority;

        private HeapNode(T i, double p) {
            this.item = i;
            this.priority = p;
        }
    }

    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        heap.add(null);
        items = new HashMap<>();
    }

    /** Swap two items in the heap. */
    private void swap(int parent_idx, int child_idx) {
        if (parent_idx == child_idx) {
            return;
        }
        HeapNode parent = heap.get(parent_idx);
        HeapNode child = heap.get(child_idx);
        items.put(child.item, parent_idx);
        items.put(parent.item, child_idx);
        heap.set(parent_idx, child);
        heap.set(child_idx, parent);
        return;
    }

    /** Get the parent heap node of the node child.
     *  If child is already the first item, return null. */
    private HeapNode getParent(HeapNode child) {
        // If child is the smallest item in the heap, it has no parent, return null.
        int child_idx = items.get(child.item);
        if (child_idx == 1) {
            return null;
        }
        // The parent's index should be child's index / 2.
        return heap.get(child_idx / 2);
    }

    /** Get the child which has the smaller priority of the node parent.
     *  If this parent doesn't have any child, return null. */
    private HeapNode getSmallerChild (HeapNode parent) {
        int parent_idx = items.get(parent.item);

        // If 2 * parent_idx is greater than or equal to the size of heap, it has no children, return null.
        if (2 * parent_idx >= heap.size()) {
            return null;
        }

        // If 2 * parent_idx is equal to the size of heap - 1, it has only one child, return this child.
        if (2 * parent_idx == heap.size() - 1) {
            HeapNode child = heap.get((2 * parent_idx));
            return child;
        }

        // Otherwise, this parent has two children, return the one with less priority.
        HeapNode left_child = heap.get(2 * parent_idx);
        HeapNode right_child = heap.get(2 * parent_idx + 1);
        if (left_child.priority < right_child.priority) {
            return left_child;
        } else {
            return right_child;
        }
    }

    /** Continue to move an item up until it reaches where it belongs in the heap. */
    private void rise(HeapNode child) {
        HeapNode parent = getParent(child);

        // If child is already the smallest item in the heap, stop rising/
        if (parent == null) {
            return;
        }

        int child_idx = items.get(child.item);
        int parent_idx = items.get(parent.item);

        // If the child has smaller priority than its parent, swap them and continue to rise child until it reaches where it belongs.
        if (parent.priority > child.priority) {
            swap(parent_idx, child_idx);
            rise(child);
        } else {
            return;
        }
    }

    /** Continue to move an item down until it reaches where it belongs in the heap. */
    private void sink(HeapNode parent) {
        HeapNode child = getSmallerChild(parent);

        // If child is already the smallest item in the heap, stop rising/
        if (child == null) {
            return;
        }

        int child_idx = items.get(child.item);
        int parent_idx = items.get(parent.item);

        // If the child has smaller priority than its parent, swap them and continue to rise child until it reaches where it belongs.
        if (parent.priority > child.priority) {
            swap(parent_idx, child_idx);
            sink(parent);
        } else {
            return;
        }
    }


    /** Adds an item with the given priority value. Throws an
     *  IllegalArgumentException if item is already present. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("This item is already present");
        } else {
            HeapNode node = new HeapNode(item, priority);

            // Add this node to the end of the heap, put it into HashMap items and set its index as the current largest one;
            heap.add(node);
            items.put(item, heap.size() - 1);

            // Rise this node until it reaches the correct place.
            rise(node);
            return;
        }
    }

    /** Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    /** Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (heap.size() == 1) {
            throw new NoSuchElementException("Can't find this element since this PQ is empty.");
        } else {
            return heap.get(1).item;
        }
    }

    /** Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        T smallest = getSmallest();
        swap(1, heap.size() - 1);
        heap.remove(heap.size() - 1);
        items.remove(smallest);
        if (heap.size() > 1) {
            HeapNode new_head = heap.get(1);
            sink(new_head);
        }
        return smallest;

//        if (heap.size() == 1) {
//            throw new NoSuchElementException("Can't find this element since this PQ is empty.");
//        } else {
//            T smallest = heap.get(1).item;
//            int last_item_index = heap.size() - 1;
//            swap(1, last_item_index);
//            heap.remove(last_item_index);
//            items.remove(smallest);
//            if (heap.size() > 1) {
//                HeapNode new_head = heap.get(1);
//                sink(new_head);
//            }
//            return smallest;
//        }
    }

    /** Returns the number of items in the PQ. */
    @Override
    public int size() {
        return heap.size() - 1;
    }

    /** Changes the priority of the given item. Throws NoSuchElementException if the item
     *  doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("This item doesn't exist.");
        } else {
            HeapNode this_node = heap.get(items.get(item));
            if (priority < this_node.priority) {
                this_node.priority = priority;
                rise(this_node);
            } else if (priority > this_node.priority) {
                this_node.priority = priority;
                sink(this_node);
            } else {
                this_node.priority = priority;
                return;
            }
        }
    }
}
