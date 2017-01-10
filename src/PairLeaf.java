//PairLeaf.java
//This is the Java class file containing the class for Pair Leaf node objects.
//This file defines the Pair Leaf node class, which adds leaf node functionality to
// Pair objects for use in a 2-3 tree data structure.
//Keith Cook, 03/06/2016

//Pair Leaf class
//A leaf node class for a 2-3 tree of word pairs.
public class PairLeaf {
    //Data fields
    int size = 2;
    private Pair [] data;

    //Tree pointers
    private PairLeaf left;
    private PairLeaf middle;
    private PairLeaf right;

    //Default constructor.
    public PairLeaf() {
        data = new Pair[size];
        for(int i = 0; i < size; ++i)
        {
            data[i] = null;
        }
        left = null;
        middle = null;
        right = null;
    }

    //Constructor for leaf with one data item.
    public PairLeaf(Pair source){
        data = new Pair[size];

        data[0] = new Pair(source);
        data[1] = null;

        left = null;
        middle = null;
        right = null;
    }

    //Getter for Word Pair data
    public Pair getData(int i){
        return data[i];
    }

    //Setter for Word Pair data
    public void setData(Pair source, int i){
        if(data[i] == null) data[i] = new Pair();
        data[i] = source;
    }

    //Getter for left pointer.
    public PairLeaf getLeft() {
        return left;
    }

    //Setter for left pointer.
    public void setLeft(PairLeaf left) {
        this.left = left;
    }

    //Getter for middle pointer.
    public PairLeaf getMiddle() {
        return middle;
    }

    //Setter for middle pointer.
    public void setMiddle(PairLeaf middle) {
        this.middle = middle;
    }

    //Getter for right pointer.
    public PairLeaf getRight() {
        return right;
    }

    //Setter for right pointer.
    public void setRight(PairLeaf right) {
        this.right = right;
    }
}
