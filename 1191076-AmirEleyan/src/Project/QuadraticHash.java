/**
 * @autor: Amir Eleyan
 * 1191076
 * At: 27/5/2021  12:32 AM
 */
package Project;

public class QuadraticHash<T extends Comparable<T>> {

    // attribute
    private HashNode<T>[] table;
    private int size = 0;
    private int capacity;

    // constructor with initial size 101
    public QuadraticHash() {
        this(101);
        this.capacity = 101;
    }

    // constructor with specific size
    public QuadraticHash(int size) {
        this.table = new HashNode[size];
        this.capacity = size;
    }

    // get the hash for specific element
    private int hash(T data, int index) {
        return (((data.hashCode() & 0x7FFFFFFF) + index) % this.capacity);
    }

    // insert new element
    // O(n), n: capacity/2
    public void insert(T data) {

        for (int i = 0; i < this.capacity / 2; i++) {

            int index = hash(data, i * i); // get key fot this data

            if (this.table[index] == null) { // index is empty

                // rehash
                if (this.size + 1 > this.capacity / 2) this.reHash();

                this.table[index] = new HashNode<>(data);
                // check data if instance of  baby to to add his frequency to the heap
                if (data instanceof Babys) {
                    this.table[index].insertToMaxHeap(((Babys) data).getFrequency());
                    ((Babys) data).clearFrequency();
                }
                this.size++;
                break;

            } else if (this.table[index] != null && this.table[index].isExist()) { // index not empty and equal new element

                // check data if instance of  baby to to add his frequency to the heap
                if (data instanceof Babys && this.table[index].getData().equals(data)) {
                    this.table[index].insertToMaxHeap(((Babys) data).getFrequency());
                    ((Babys) data).clearFrequency();
                    break;
                }

            } else if (this.table[index] != null && !this.table[index].isExist()) {//index not empty and equal new element but deleted

                this.table[index].setData(data);
                this.table[index].setStatus(true);

                // check data if instance of  baby to to add his frequency to the heap
                if (data instanceof Babys && this.table[index].getData().equals(data)) {
                    this.table[index].insertToMaxHeap(((Babys) data).getFrequency());
                    ((Babys) data).clearFrequency();
                }

                this.size++;
                break;
            }

        }
    }

    //  remove specific element
    // O(n), n: capacity/2
    public T delete(T data) {
        for (int i = 0; i < this.capacity / 2; ++i) {

            int hash = this.hash(data, i * i); // get the index for this data
            if (this.table[hash] == null) return null;

            if (this.table[hash].getData().equals(data) && this.table[hash].isExist()) { // delete it by change his status to false
                this.table[hash].setStatus(false);
                this.size--;
                return this.table[hash].getData();
            }
        }
        return null;
    }

    // search for an element
    // O(n), n: capacity/2
    public HashNode<T> search(T data) {

        for (int i = 0; i < this.capacity / 2; ++i) {
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
        HashNode<T>[] temp = new HashNode[this.capacity * 2 + 1];
        int length = temp.length;

        for (HashNode<T> tHashNode : this.table) {
            // for collisions
            for (int j = 0; j < length; ++j) {
                if (tHashNode != null) {

                    int index = (((tHashNode.hashCode() & 0x7FFFFFFF) + j * j) % length);

                    if (temp[index] == null) {
                        temp[index] = new HashNode<>(tHashNode.getData());
                        break;
                    }

                }
            }

        }
        this.table = temp;
    }

    // clear the table
    public void clear() {
        int s = this.capacity / 2;
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
