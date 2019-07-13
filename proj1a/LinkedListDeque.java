public class LinkedListDeque<T> {
    private class ItemNode {
        private ItemNode prev;
        private T val;
        private ItemNode next;

        private ItemNode(ItemNode p, T i, ItemNode n) {
            prev = p;
            val = i;
            next = n;
        }
    }

    private int size;
    private ItemNode sentinel;

    /** Creates an empty linked list deque. */
    public LinkedListDeque() {
        sentinel = new ItemNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Adds an item to the front of the deque. */
    public void addFirst(T item) {
        ItemNode first = new ItemNode(sentinel, item, sentinel.next);
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    /** Adds an item to the end of the deque. */
    public void addLast(T item) {
        ItemNode last = new ItemNode(sentinel.prev, item, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
    }

    /** Return if deque is empty. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Retuen the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        ItemNode curr = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.println(curr.val + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    /** Remove and return the item at the front of the deque. If no items, return null. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            ItemNode toRemove = sentinel.next;
            toRemove.next.prev = toRemove.prev;
            toRemove.prev.next = toRemove.next;
            size -= 1;
            return toRemove.val;
        }
    }

    /** Remove and return the item at the back of the deque. If no items, return null. */
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            ItemNode toRemove = sentinel.prev;
            toRemove.next.prev = toRemove.prev;
            toRemove.prev.next = toRemove.next;
            size -= 1;
            return toRemove.val;
        }
    }


    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     * @param index
     * @return the item at the index
     */
    public T get(int index) {
        if (index > size - 1) {
            return null;
        } else {
            ItemNode curr = sentinel.next;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            return curr.val;
        }
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * Must use recursion.
     * If no such item exists, returns null. Must not alter the deque!
     * @param index
     * @return the item at that index
     */
    public T getRecursive(int index) {

        return getRecursiveHelper(index, sentinel.next);
    }

    public T getRecursiveHelper(int index, ItemNode curr) {
        if (index > size) {
            return null;
        } else if (index == 0) {
            return curr.val;
        } else {
            return getRecursiveHelper(index - 1, curr.next);
        }
    }

    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new ItemNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

//        ItemNode curr = sentinel;
//        ItemNode other_curr = other.sentinel.next;
        for (int i = 0; i < other.size; i++) {
            addLast((T) other.get(i));
//            ItemNode copy = new ItemNode(curr, other_curr.val, sentinel);
//            curr.next = copy;
//            sentinel.prev = copy;
//            curr = copy;
//            other_curr = other_curr.next;
        }
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> Dllist = new LinkedListDeque<>();
        Dllist.addFirst(666);
        Dllist.addLast(6666);
        Dllist.addLast(66666);
        Dllist.printDeque();                        // expected (666 6666 66666)
        System.out.println("Test getIterative #1");
        System.out.println(Dllist.get(0)); // expected 666
        System.out.println(Dllist.get(1)); // expected 6666
        System.out.println(Dllist.get(5)); // expected null
        System.out.println("Test getIterative #1");
        System.out.println(Dllist.getRecursive(0)); // expected 666
        System.out.println(Dllist.getRecursive(1)); // expected 6666
        System.out.println("Test done!");

        Dllist.removeFirst();
        Dllist.printDeque();                        // expected (6666 66666)
        System.out.println("Test getIterative #2 removeFirst");
        System.out.println(Dllist.get(0)); // expected 6666
        System.out.println(Dllist.get(1)); // expected 66666
        System.out.println("Test getRecursive #2 removeFirst");
        System.out.println(Dllist.getRecursive(0)); // expected 6666
        System.out.println(Dllist.getRecursive(1)); // expected 66666

        Dllist.removeLast();
        Dllist.printDeque();                        // expected 6666
        System.out.println("Test getIterative #3 removeLast");
        System.out.println(Dllist.get(0)); // expected 6666
        System.out.println(Dllist.get(1)); // expected null
        System.out.println("Test getRecursive #3 removeFirst");
        System.out.println(Dllist.getRecursive(0)); // expected 6666
        System.out.println(Dllist.getRecursive(1)); // expected null


        Dllist.addLast(12);
        Dllist.addFirst(23);
        LinkedListDeque copy_of_Dlist = new LinkedListDeque(Dllist);
        copy_of_Dlist.printDeque();
    }

}