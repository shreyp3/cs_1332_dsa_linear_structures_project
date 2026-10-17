package refactor;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EndlessLinkedList<T> extends StaticEndlessLinkedList<T> {

    private Node<T> tail;

    @Override
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        Node<T> newData = new Node<T>(data);
        newData.setNext(head);
        head = newData;
        if (size == 0) {
            tail = head;
        }
        size++;
    }

    @Override
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            addFirst(data);
        } else {
            Node<T> newData = new Node<T>(data);
            tail.setNext(newData);
            tail = newData;
            size++;
        }
    }

    @Override
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        if (size == 1) {
            tail = null;
        }
        Node<T> deletionData = head;
        head = head.getNext();
        size--;
        return deletionData.getData();
    }

    @Override
    public T removeLast() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        if (size == 1) {
            return removeFirst();
        } else {
            Node<T> curr = head;
            for (int i = 0; i < size - 2; i++) {
                curr = curr.getNext();
            }
            Node<T> deletionData = tail;
            tail = curr;
            tail.setNext(null);
            size--;
            return deletionData.getData();
        }
    }

    @Override
    public int removeValue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        Node<T> cur = head;
        for (int i = 0; i < size; i++) {
            if (cur.getData().equals((data))) {
                removeAtIndex(i);
                return i;
            }
            cur = cur.getNext();
        }
        throw new NoSuchElementException("Data is not in the list");
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.getNext();
        }
        return cur.getData();
    }

    @Override
    public void reverse() {
        if (size > 1) {
            tail = head;
            Node<T> cur = head;
            Node<T> next;
            Node<T> prev = null;

            for (int i = 0; i < size; i++) {
                next  = cur.getNext();
                cur.setNext(prev);
                prev = cur;
                cur = next;
            }
            head = prev;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<T>(head);
    }
    private static class LinkedListIterator<T> implements Iterator<T> {
        private Node<T> curr;
        private final Node<T> start;

        /**
         * Instantiates a LinkedListIterator Object.
         *
         * @param start the beginning node of the list
         * @implSpec {@code O(1)} runtime
         */
        public LinkedListIterator(Node<T> start) {
            this.start = start;
            this.curr = start;
        }
        @Override
        public boolean hasNext() {
            return start != null;
        }
        @Override
        public T next() {
            if (start == null) {
                throw new NoSuchElementException("List is empty");
            }
            T currData = curr.getData();
            curr = curr.getNext();
            if (curr == null) {
                curr = start;
            }
            return currData;
        }
    }
}
