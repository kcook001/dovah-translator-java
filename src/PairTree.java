//PairTree.java
//This is the Java class file containing the data structure and utility methods used in Assignment #5.
//The two classes defined in this file are PairTree, which is a linear-linked list of arrays of word pairs data
// structure class, and Translator, which extends PairTree and adds the client-accessible utility methods for
// translating words between languages.
//Keith Cook, 03/06/2016

//Imports
import java.io.*;
import java.util.Scanner;

//Pair List class
//LLL of arrays data structure of word pairs.
public class PairTree {
    //Fields
    private PairLeaf root; //Head reference for the data structure.
    private int entryCount;          //Number of entries (word pairs) in the data structure.
    private String language;       //The non-English language being translated into.
    private boolean elevate = false;

    //Default constructor/
    public PairTree(){
        this.root = null;
        this.language = null;
        entryCount = 0;
    }

    //Constructor with filename argument (for loading the data structure).
    public PairTree(String filename){
        this.root = null;
        this.language = null;
        entryCount = 0;
        this.load(filename);
    }

    //Getter for language string.
    public String getLanguage() {
        return language;
    }

    //Returns true if the data structure contains data, false if it does not.
    public boolean exists() {
        if(root != null) return true;
        return false;
    }

    //Loads in language data to the data structure from an external file.
    protected void load(String filename){
        //Temp variables and scanner for reading file.
        String tempS1;
        String tempS2;
        Pair tempPair;
        Scanner infile = null;

        //Throws an error if file is not found.
        try{
            //Open the file.
            infile = new Scanner(new BufferedReader(new FileReader(filename)));

            entryCount = 0;

            //Read in first line and set language.
            infile.useDelimiter("\\r\\n");
            tempS1 = infile.next();
            this.language = tempS1;

            //Read in the rest of the file
            while(infile.hasNext()) {
                //Read into temp1
                infile.skip("\\r\\n");
                infile.useDelimiter(",");
                tempS1 = infile.next();

                //Read into temp2
                infile.skip(",");
                infile.useDelimiter("\\r\\n");
                tempS2 = infile.next();

                //Create a new node from the word pair
                tempPair = new Pair(tempS1, tempS2);

                //Add the new node to the data structure and increments the count.
                this.add(tempPair);
                ++entryCount;
            }
        } catch(FileNotFoundException ex) {  //Error handling.
            language = "None";
            System.out.println("Error: File not found.");
        }finally {
            //Close the stream when we're done with it.
            if (infile != null) infile.close();
        }
    }

    //Adds a node to the data structure (wrapper for recursive function).
    protected void add(Pair toAdd){
        //Exception cases go here
        if(root == null) {
            root = new PairLeaf(toAdd);
            //root.setData(toAdd, 0);
        } else {
            root = add(toAdd, root);
        }

        elevate = false;
    }

    //Adds a node to the data structure (recursively).
    private PairLeaf add(Pair toAdd, PairLeaf current){
        //Temp variables
        PairLeaf tempLeaf;
        PairLeaf toElevate;
        PairLeaf fromBelow;

        //Base case: leaf has been reached.
        if(current.getLeft() == null/* && current.getMiddle() == null && current.getRight() == null*/){
            //If there's only one data element in the leaf...
            if(current.getData(1) == null) {
                //Compare the value in the leaf and plug the new value in in the appropriate spot.
                if(toAdd.getWord(0).compareToIgnoreCase(current.getData(0).getWord(0)) <= 0){
                    current.setData(current.getData(0),1);
                    current.setData(toAdd,0);
                } else {
                    current.setData(toAdd, 1);
                }
                return current; //Nothing special to return, the add is complete.
            } else {  //If the node is already has two elements in it...
                //..split the node, raise up the middle value, return reference to it.
                //Current node lesser data is the middle value
                elevate = true;
                if(toAdd.getWord(0).compareToIgnoreCase(current.getData(0).getWord(0)) <= 0){
                    toElevate = new PairLeaf(current.getData(0));

                    tempLeaf = new PairLeaf(toAdd);
                    toElevate.setLeft(tempLeaf);

                    tempLeaf = new PairLeaf(current.getData(1));
                    toElevate.setMiddle(tempLeaf);
                //New item is the middle item.
                } else if(toAdd.getWord(0).compareToIgnoreCase(current.getData(1).getWord(0)) <= 0) {
                    toElevate = new PairLeaf(toAdd);

                    tempLeaf = new PairLeaf(current.getData(0));
                    toElevate.setLeft(tempLeaf);

                    tempLeaf = new PairLeaf(current.getData(1));
                    toElevate.setMiddle(tempLeaf);
                //Current node greater value is the middle item.
                } else {
                    toElevate = new PairLeaf(current.getData(1));

                    tempLeaf = new PairLeaf(current.getData(0));
                    toElevate.setLeft(tempLeaf);

                    tempLeaf = new PairLeaf(toAdd);
                    toElevate.setMiddle(tempLeaf);
                }
                //Send the new subtree back up to the last level for connecting up.
                return toElevate;
            }
        }

        //If not at a leaf, traverse according to the data's value.
        if(toAdd.getWord(0).compareToIgnoreCase(current.getData(0).getWord(0)) <= 0){
            fromBelow = add(toAdd, current.getLeft());

            //After returning, merge or split and elevate if needed.
            if(elevate && current.getData(1) == null) {
                current.setData(current.getData(0), 1);
                current.setData(fromBelow.getData(0),0);
                current.setLeft(fromBelow.getLeft());
                current.setRight(current.getMiddle());
                current.setMiddle(fromBelow.getMiddle());
                elevate = false;
            } else if(elevate) {
                toElevate = new PairLeaf(current.getData(0));

                tempLeaf = fromBelow;
                toElevate.setLeft(tempLeaf);

                tempLeaf = new PairLeaf(current.getData(1));
                tempLeaf.setLeft(current.getMiddle());
                tempLeaf.setMiddle(current.getRight());
                toElevate.setMiddle(tempLeaf);
                return toElevate;
            } else {
                //If no need to split, connect up as usual.
                current.setLeft(fromBelow);
            }
        } else if(current.getData(1) == null || toAdd.getWord(0).compareToIgnoreCase(current.getData(1).getWord(0)) <= 0){
            fromBelow = add(toAdd, current.getMiddle());

            //After returning, merge or split and elevate if needed.
            if(elevate && current.getData(1) == null) {
                current.setData(fromBelow.getData(0),1);
                current.setMiddle(fromBelow.getLeft());
                current.setRight(fromBelow.getMiddle());
                elevate = false;
            } else if(elevate) {
                toElevate = new PairLeaf(fromBelow.getData(0));

                tempLeaf = new PairLeaf(current.getData(0));
                tempLeaf.setLeft(current.getLeft());
                tempLeaf.setMiddle(fromBelow.getLeft());
                toElevate.setLeft(tempLeaf);

                tempLeaf = new PairLeaf(current.getData(1));
                tempLeaf.setLeft(fromBelow.getMiddle());
                tempLeaf.setMiddle(current.getRight());
                toElevate.setMiddle(tempLeaf);

                return toElevate;
            } else {
                //If no need to split, connect up as usual.
                current.setMiddle(fromBelow);
            }
        } else {
            fromBelow = add(toAdd, current.getRight());

            //After returning, merge or split and elevate if needed.
            if(elevate) {
                toElevate = new PairLeaf(current.getData(1));

                tempLeaf = new PairLeaf(current.getData(0));
                tempLeaf.setLeft(current.getLeft());
                tempLeaf.setMiddle(current.getMiddle());
                toElevate.setLeft(tempLeaf);

                tempLeaf = fromBelow;
                toElevate.setMiddle(tempLeaf);

                return toElevate;
            } else {
            //If no need to split, connect up as usual.
            current.setRight(fromBelow);
            }
        }

        return current;
    }

    //Wrapper for recursive retrieve function.
    public Pair retrieve(String toFind){
        if(root == null) return null;

        return retrieve(toFind, root);
    }

    //Finds and returns a word pair where the English word matches the supplied string.
    private Pair retrieve(String toFind, PairLeaf current) {
        if (current == null) return null;

        Pair found = null;

        if (current.getData(0).getWord(0).equals(toFind)) {
            found = current.getData(0);
        }

        if(current.getData(1) != null) {
            if (current.getData(1).getWord(0).equals(toFind)) {
                found = current.getData(1);
            }
        }

        if (found == null) {
            if(toFind.compareToIgnoreCase(current.getData(0).getWord(0)) <= 0) {
                found = retrieve(toFind, current.getLeft());
            } else if(current.getData(1) == null || toFind.compareToIgnoreCase(current.getData(1).getWord(0)) <= 0){
                    found = retrieve(toFind, current.getMiddle());
            } else {
                    found = retrieve(toFind, current.getRight());
                }
            }
        return found;
    }

    //Displays all the word pairs in the data structure (wrapper method).
    public void displayAll() {
        if(root == null){
            System.out.println("The list of word pairs is empty.  Please load from an external file.");
            return;
        }
        System.out.println("English to " + language + " Dictionary");
        displayAll(root);

        System.out.println(entryCount + " entries in the dictionary.");
    }

    //Displays all the word pairs in the data structure (recursive method).
    private void displayAll(PairLeaf current) {

        if(current == null) return;

        displayAll(current.getLeft());
        if(current.getData(0) != null) {
            current.getData(0).displayFormatted(language);
            System.out.println();
        }
        displayAll(current.getMiddle());
        if(current.getData(1) != null) {
            current.getData(1).displayFormatted(language);
            System.out.println();
        }
        displayAll(current.getRight());
    }

    //Removes all leaves in the tree (Java style!)
    protected void removeAll(){
        this.root = null;
        this.entryCount = 0;
        this.language = null;
    }
}

//Translator class
//Adds client-accessible utility functions to the data structure.
class Translator extends PairTree{
    //Default Constructor
    public Translator(){
        super();
    }

    //Constructor with string argument, kickstarts parent class constructor with a filename to load with.
    public Translator(String filename){
        super(filename);
    }

    public String translate(String toTrans){
        String result = null;

        Pair temp = retrieve(toTrans);
        if(temp != null){
            result = temp.getWord(1);
        }

        return result;
    }

    //Adds a special menu for debugging / testing purposes.
    public void debugMenu(){
        char choice;
        String filename = new String();
        Scanner scan = new Scanner(System.in);

        do {
            //Display options
            System.out.println("Debug / Testing Menu");
            System.out.println("What would you like to do?");
            System.out.println("1. Display all dictionary entries. (Warning: There might be a lot!)");
            System.out.println("2. Change to a different language. (Requires a formatted text file)");
            System.out.println("0. Exit the debug menu.");
            System.out.println("Please enter the number of your choice.");

            choice = scan.findInLine(".").charAt(0);
            scan.nextLine();

            if(choice == '1')   //Display all entries.
            {
                System.out.println("Displaying all entries:");
                this.displayAll();
            }

            if(choice == '2'){  //Switch to a different language text file.
                System.out.println("Please enter the name of the file you wish to use.");
                filename = scan.next();
                scan.nextLine();

                this.removeAll();
                this.load(filename);
            }
        } while(choice == '1' || choice == '2');

        System.out.println("Returning to "+ this.getLanguage() + " translator...");
    }

    //Overloaded load function, kickstarts parent version.
    protected void load(String filename){
        super.load(filename);
    }

    //Overloaded remove all function, kickstarts parent version.
    protected void removeAll() {
        super.removeAll();
    }
}
