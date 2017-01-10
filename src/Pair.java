//Pair.java
//This is the Java class file containing the class for Word Pair objects.
//This file defines the Pair class, which represents an English word and a
// non-English word with the same meaning.
//Keith Cook, 03/06/2016

//Pair class
//Represents a pair of words with the same meaning, one English and one non-English, to be translated between.
public class Pair {
    final int size = 2;   //Size of the array of Words (if it wasn't 2 it wouldn't be a pair, would it?)
    private Word [] pair; //An array of Words.

    //Default constructor
    public Pair() {
        pair = new Word[size];  //Create the pair.
        pair[0] = new Eword();  //One English word,
        pair[1] = new Dword();  // and one non-English word.
    }

    //Constructor with String arguments.
    public Pair(String e, String d) {
        pair = new Word[size];  //Create the pair.
        pair[0] = new Eword(e); //One English word,
        pair[1] = new Dword(d); // and one non-English word.
    }

    //Copy constructor (just in case)
    public Pair(Pair source) {
        pair = new Word[size];
        this.pair[0] = source.pair[0];
        this.pair[1] = source.pair[1];
    }

    //Getter for specified word (0 is English, 1 is non-English).  Unexpected argument returns null.
    public String getWord(int i){
        if(i == 0 || i ==1) return pair[i].getWord();
        return null;
    }

    //Simple display function.
    public void display(){
        pair[0].display();
        pair[1].display();
    }

    //Fancier display function.
    public void displayFormatted(String language){
        System.out.print("English: ");
        pair[0].display();
        System.out.print(language + ": ");
        pair[1].display();
    }
}
