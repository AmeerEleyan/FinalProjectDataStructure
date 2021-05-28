/**
 * @autor: Amir Eleyan
 * 1191076
 * At: 26/5/2021  9:00 PM
 */
package Project;

public class HashNode<T extends Comparable<T>> {

    // attribute
    private T data;
    private boolean status;
    private MaxHeap<Frequency> frequencyMaxHeap = new MaxHeap<>();

    // constructor with no arguments
    public HashNode() {
        this.status = true;
    }

    // constructor with specific arguments
    public HashNode(T data) {
        this.data = data;
        this.status = true;
    }

    public MaxHeap<Frequency> getFrequencyMaxHeap() {
        return this.frequencyMaxHeap;
    }

    // insert new element to the max heap
    public void insertToMaxHeap(Frequency date) {
        this.frequencyMaxHeap.insert(date);
    }

    // get data for this node
    public T getData() {
        return this.data;
    }

    // set a new data for this node
    public void setData(T data) {
        this.data = data;
    }

    // get status of the node. deleted or not
    public boolean isExist() {
        return this.status;
    }

    // set new status of this node
    public void setStatus(boolean status) {
        this.status = status;
    }

    // return data as string
    @Override
    public String toString() {
        return this.data.toString();
    }
}