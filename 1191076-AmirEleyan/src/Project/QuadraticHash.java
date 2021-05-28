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

    // constructor with no arguments
    public QuadraticHash() {
        this(101);
        this.capacity = 101;
    }

    // constructor with specific arguments
    public QuadraticHash(int size) {
        this.table = new HashNode[size];
        this.capacity = size;
    }

    // get the hash for specific element
    private int hash(T data, int index) {
        return (((data.hashCode() & 0x7FFFFFFF) + index) % this.capacity);
    }

    // insert new element
    public void insert(T data) {

        for (int i = 0; i < this.capacity; i++) {

            int index = hash(data, i * i); // get key fot this data

            if (this.table[index] == null) { // index is empty

                // rehash
                if (this.size + 1 > this.capacity / 2) this.reHash();

                this.table[index] = new HashNode<>(data);
                if (data instanceof Babys) this.table[index].insertToMaxHeap(((Babys) data).getFrequency());
                this.size++;
                break;

            } else if (this.table[index] != null && this.table[index].isExist()) { // index not empty and equal new element

                if (data instanceof Babys && this.table[index].getData().equals(data))
                    this.table[index].insertToMaxHeap(((Babys) data).getFrequency());
                break;

            } else if (this.table[index] != null && !this.table[index].isExist()) {//index not empty and equal new element but deleted

                this.table[index].setData(data);
                this.table[index].setStatus(true);
                if (data instanceof Babys && this.table[index].getData().equals(data))
                    this.table[index].insertToMaxHeap(((Babys) data).getFrequency());
                this.size++;
                break;
            }

        }
    }

    //  remove specific element
    public T delete(T data) {
        for (int i = 0; i < this.capacity; ++i) {
            int hash = this.hash(data, i * i);
            if (this.table[hash] == null) return null;

            if (this.table[hash].getData().equals(data) && this.table[hash].isExist()) {
                this.table[hash].setStatus(false);
                this.size--;
                return this.table[hash].getData();
            }
        }
        return null;
    }

    // search for an element
    public HashNode<T> search(T data) {

        for (int i = 0; i < this.capacity; ++i) {
            int hash = this.hash(data, i * i);
            if (this.table[hash] == null) return null;

            if (this.table[hash].getData().equals(data) && this.table[hash].isExist()) {
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
