/**
 * @autor: Amir Eleyan
 * 1191076
 * At: 27/5/2021  3:35 AM
 */
package Project;

public class MaxHeap<T extends Comparable<T>> {


    // attribute
    private HeapNode<T>[] heap;
    private int size = 0;
    private int capacity;

    // constructor with initial size 50
    public MaxHeap() {
        this(50);
        this.capacity = 50;
    }

    // constructor with size
    public MaxHeap(int size) {
        this.heap = new HeapNode[size];
        this.capacity = size;
    }

    // get array of heapNode
    public HeapNode<T>[] getHeap() {
        return this.heap;
    }

    // get max element
    // O(1)
    public T getMax() {
        if (this.size == 0) return null;
        return this.heap[1].getData();
    }

    // delete the max element in this heap who locate in the beginning in the array
    // log n
    public T deleteMax() {

        if (this.size == 0) return null;
        HeapNode<T> max = this.heap[1];
        this.swap(1, this.size--);
        this.sink(1);
        this.heap[this.size + 1] = null;
        return max.getData();
    }

    // to put this element in the right position after add it, his child after his index
    // log n
    private void swim(int index) {

        while (1 < index && this.isLess(index / 2, index)) { // while index is not root and greater than parent,
            this.swap(index, index / 2);  // swap it with its parent
            index /= 2;
        }
    }

    // log n
    // to put this element in the right position ( to get max in the beginning of the array) after max
    private void sink(int index) {

        while (2 * index <= this.size) { // node have a child
            int j = 2 * index;
            if (j < this.size && this.isLess(j, j + 1)) j++; // which child is grater
            if (!this.isLess(index, j)) break;
            this.swap(index, j);
            index = j;
        }
    }

    // insert new element tot this heap
    // log n
    public void insert(T data) {
        if (this.heap[size + 1] == null) this.heap[++size] = new HeapNode<>(data);
        swim(size);
    }

    // search for an element in the heap and return it
    // O(n), n: actual size
    public T search(T date) {
        for (int i = 0; i < this.size + 1; ++i) {
            if (this.heap[i].getData().equals(date)) return this.heap[i].getData();
        }
        return null;
    }

    // search for an element in this heap and return his index
    public int getIndex(T date) {
        for (int i = 0; i < this.size + 1; ++i) {
            if (this.heap[i] != null && this.heap[i].getData().equals(date)) return i;
        }
        return -1;
    }

    // clear this heap
    public void clear() {
        this.heap = new HeapNode[this.capacity];
    }

    // get actual size of this heap
    public int size() {
        return this.size;
    }

    // check status of this heap
    public boolean isEmpty() {
        return this.size == 0;
    }

    //swap between two object(2 index) in the array
    private void swap(int i, int j) {
        HeapNode<T> temp = this.heap[i];
        this.heap[i] = this.heap[j];
        this.heap[j] = temp;
    }

    // print this heap
    public void print() {
        for (int i = 0; i < this.size + 1; ++i) {
            if (this.heap[i] != null)
                System.out.print(this.heap[i].getData() + " ");
        }
    }

    // get this heap sorted descending
    public HeapNode<T>[] getSorted() {
        HeapNode<T>[] tempHeapNodes = this.heap.clone();

        HeapNode<T>[] sortedMaxHeap = new HeapNode[this.size];

        for (int i = 1; i < this.size + 1; ++i) swim(i); // n log n

        int i = 0;
        while (this.size != 0) {
            sortedMaxHeap[i] = new HeapNode<>(this.deleteMax()); // n log n
            i++;
        }
        this.heap = tempHeapNodes;
        this.size = i;
        return sortedMaxHeap;
    }

    // shift array when remove element
    public void shiftArray(int index) {
        for (int i = index; i < this.size; ++i) {
            if (this.heap[i] == null) break;
            this.heap[i] = this.heap[i + 1];
        }
    }

    // compare two heapNode
    private boolean isLess(int i, int j) {
        return (this.heap[i].getData().compareTo(this.heap[j].getData()) < 0);
    }
}
