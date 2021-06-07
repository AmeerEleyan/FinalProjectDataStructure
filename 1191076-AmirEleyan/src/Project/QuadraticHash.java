/**
 * @autor: Amir Eleyan
 * 1191076
 * At: 27/5/2021  12:32 AM
 */
package Project;

import java.util.Hashtable;

public class QuadraticHash<T extends Comparable<T>> {

    // attribute
    private HashNode<T>[] table;
    private int size = 0;

    // constructor with initial size 101
    public QuadraticHash() {
        this(101);
    }

    // constructor with specific size
    public QuadraticHash(int size) {
        this.table = new HashNode[getPrime(size * 2)];
    }

    // get the hash for specific element
    private int hash(T data, int index) {
        return ((Math.abs(data.hashCode()) + index) % this.table.length);
    }


    // insert new element
    // O(n), n: capacity/2
    public void insert(T data) {
        if ((this.size + 1) > this.table.length / 2) { // rehash
            this.reHash();
        }

        int hash = data.hashCode() % this.table.length;
        int count = 0;
        int index = hash;
        do {
            if (this.table[index] == null) { // index is empty

                this.table[index] = new HashNode<>(data);
                // check data if instance of  baby to to add his frequency to the heap
                if (data instanceof Babys) {
                    this.table[index].insertToMaxHeap(((Babys) data).getFrequency());
                    this.size++;
                    break;
                }

            } else if (this.table[index] != null && !this.table[index].isExist()) {//index not empty and equal new element but deleted

                this.table[index].setData(data);
                this.table[index].setStatus(true);
                // check data if instance of  baby to to add his frequency to the heap
                if (data instanceof Babys && this.table[index].getData().equals(data)) {
                    this.table[index].insertToMaxHeap(((Babys) data).getFrequency());
                }
                this.size++;
                break;

            } else if (this.table[index] != null && this.table[index].isExist()) { // index not empty and equal new element

                // check data if instance of  baby to to add his frequency to the heap
                if (data instanceof Babys && this.table[index].getData().equals(data)) {
                    this.table[index].insertToMaxHeap(((Babys) data).getFrequency());
                    break;
                }

            }
            count++;
            index = hash + count * count;
            index %= this.table.length;

        } while (true);

    }


    //  remove specific element
    // O(n), n: capacity/2
    public HashNode<T> delete(T data) {
        for (int i = 0; i < this.table.length; ++i) {

            int hash = this.hash(data, i * i); // get the index for this data
            if (this.table[hash] == null) return null;

            if (this.table[hash].getData().equals(data) && this.table[hash].isExist()) { // delete it by change his status to false
                this.table[hash].setStatus(false);
                this.size--;
                return this.table[hash];
            }
        }
        return null;
    }

    // search for an element
    // O(n), n: capacity/2
    public HashNode<T> search(T data) {

        for (int i = 0; i < this.table.length; ++i) {
            int hash = this.hash(data, i * i);
            if (this.table[hash] == null) return null;// not found element

            if (this.table[hash].getData().equals(data) && this.table[hash].isExist()) { // found element
                return this.table[hash];
            }
        }
        return null;
    }

    // resize the table
    private void reHash() {
        int capacity = getPrime(this.table.length * 2);
        HashNode<T>[] temp = new HashNode[capacity];

        for (HashNode<T> tHashNode : this.table) {

            if (tHashNode != null) {
                int index = tHashNode.getData().hashCode() % capacity;
                int i = 0;
                int num = index;
                while (true) {
                    if (temp[num] == null) {
                        temp[num] = new HashNode<>();
                        temp[num].setData(tHashNode.getData());
                        temp[num].setFrequencyMaxHeap(tHashNode.getFrequencyMaxHeap());
                        break;
                    } else if (!temp[num].isExist()) {
                        temp[num] = new HashNode<>();
                        temp[num].setData(tHashNode.getData());
                        temp[num].setFrequencyMaxHeap(tHashNode.getFrequencyMaxHeap());
                        break;
                    }
                    i++;
                    num = index + i * i;
                    num %= capacity;
                }
            }
        }
        this.table = temp;
    }


    // get first prime number after specific number
    private static int getPrime(int size) {
        int temp = size;
        while (true) {
            boolean result = true;
            for (int i = 2; i <= temp / 2; ++i) {
                if (temp % i == 0) {
                    result = false;
                    break;
                }
            }
            if (result) return temp;
            else temp++;
        }
    }

    // clear the table
    public void clear() {
        int s = this.table.length;
        this.table = new HashNode[s];
    }

    // size of exist elements
    public int size() {
        return this.size;
    }

    // check the size of the table is empty or not
    public boolean isEmpty() {
        return this.size == 0;
    }

    // print the table
    public void print() {
        for (HashNode<T> tHashNode : this.table) {
            if (tHashNode != null) {
                System.out.println(tHashNode);
            }
        }
    }

    // return the table
    public HashNode<T>[] getTable() {
        return this.table;
    }
}
