public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int first;
    private int last;

    public ArrayDeque () {
        items = (T[]) new Object[8];
        size = 0;
        first = 0;
        last = 0;
    }

    public ArrayDeque (ArrayDeque other) {
        int capacity = (int) Math.pow(2, Math.ceil(Math.log(other.size) / Math.log(2)));
        if (capacity < 8) {
            capacity = 8;
        }
        items = (T[]) new Object[capacity];
        for (int i = 0; i < other.size; i++) {
            items[i] = (T) other.get(i);
        }
        size = other.size;
        first = 0;
        last = size - 1;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (first <= last) {
            System.arraycopy(items, first, a, 0, last - first + 1);
        } else {
            System.arraycopy(items, first, a, 0, items.length - first);
            System.arraycopy(items, 0, a, items.length - first, last + 1);
        }
        items = a;
        first = 0;
        last = size - 1;
    }

    public void addFirst(T item) {
        if (size == 0) {
            items[first] = item;
            size += 1;
            return;
        }
        if (size == items.length) {
            resize(2 * items.length);
        }
        first = ((first - 1) % items.length + items.length) % items.length;
        items[first] = item;
        size += 1;
    }

    public void addLast(T item) {
        if (size == 0) {
            items[last] = item;
            size += 1;
            return;
        }
        if (size == items.length) {
            resize(2 * size);
        }

        last = (last + 1) % items.length;
        items[last] = item;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.println(items[(first + i) % items.length] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else if (size * 2 == items.length) {
            resize(items.length / 2);
        }
        T returnItem = items[first];
        first = (first + 1) % items.length;
        size -= 1;
        return returnItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else if (size * 2 == items.length) {
            resize(items.length / 2);
        }
        T returnItem = items[last];
        last = ((last - 1) % items.length + items.length) % items.length;
        size -= 1;
        return returnItem;
    }

    public T get(int index) {
        if (index > size - 1) {
            return null;
        } else {
            return items[(first + index) % items.length];
        }
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> Alist = new ArrayDeque<>();
        Alist.addFirst(666);
        Alist.addLast(6666);
        Alist.addLast(66666);
        Alist.printDeque();                        // expected (666 6666 66666)
        System.out.println("Test get #1");
        System.out.println(Alist.get(0)); // expected 666
        System.out.println(Alist.get(1)); // expected 6666
        System.out.println(Alist.get(5)); // expected null
        System.out.println("Test done!");
        System.out.println();

        System.out.println(Alist.removeFirst());   // expected 666
        Alist.printDeque();                        // expected (6666 66666)
        System.out.println("Test get #2 removeFirst");
        System.out.println(Alist.get(0)); // expected 6666
        System.out.println(Alist.get(1)); // expected 66666
        System.out.println();

        Alist.removeLast();
        Alist.printDeque();                        // expected 6666
        System.out.println("Test get #3 removeLast");
        System.out.println(Alist.get(0)); // expected 6666
        System.out.println(Alist.get(1)); // expected null
        System.out.println();


        Alist.addLast(12);
        Alist.addFirst(23);
        //System.out.println(Alist.size);   // expected 3
        //Alist.printDeque();

        ArrayDeque<Integer> Alist_2 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            Alist_2.addFirst(i);
        }
        ArrayDeque copy_of_Alist_2 = new ArrayDeque(Alist_2);
        copy_of_Alist_2.printDeque();
//        Alist_2.printDeque();   // expected 99,98,97,...,0
//        while (Alist_2.size > 2) {
//            System.out.println(Alist_2.removeLast());
//        }
//        System.out.println(Alist_2.removeFirst());  // expected 99
//        System.out.println(Alist_2.removeLast());   // expected 98
//        System.out.println(Alist_2.size);
//        ArrayDeque copy_of_Alist = new ArrayDeque(Alist);
//        copy_of_Alist.printDeque();
    }
}