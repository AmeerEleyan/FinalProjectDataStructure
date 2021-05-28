/**
 * @autor: Amir Eleyan
 * 1191076
 * At: 26/5/2021  9:00 PM
 */
package Project;

public class HeapNode<T extends Comparable<T>> {

    // attribute
    private T data;

    // constructor with no arguments
    public HeapNode() {

    }

    // constructor with specific arguments
    public HeapNode(T data) {
        this.data = data;
    }

    // get data for this node
    public T getData() {
        return this.data;
    }

    // set a new data for this node
    public void setData(T data) {
        this.data = data;
    }


    // return data as string
    @Override
    public String toString() {
        return this.data.toString();
    }


}