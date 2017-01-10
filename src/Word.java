//Word.java
//This is the Java class file containing the classes relating to language words.
//The three classes defined in this file are the Word class, an abstract base class representing a language word,
// the English Word class, representing a word in English, and the Dovahzul Word class, representing a word in
// a non-English language (the program defaults to Dovahzul).
//Keith Cook, 03/06/2016

//Word class
//Abstract base class representing a language word.
public abstract class Word{
    private String word; //Word string.

    //Default constructor.
    public Word() {
        word = null;
    }

    //Getter for the word data.
    public String getWord() {
        return word;
    }

    //Setter for the word data.
    public void setWord(String word) {
        this.word = word;
    }

    //Displays the word data to the screen.
    public void display(){
        System.out.println(word);
    }
}

//English Word class
//Represent an English-language word.
class Eword extends Word{
    //Default constructor
    public Eword() {
        super();
    }

    //Constructor with string argument.
    public Eword(String e){
        this.setWord(e);
    }

    //Overriding display function for dynamic binding.
    public void display(){
        System.out.println(this.getWord());
    }
}

//Dovahzul Word class
//Represents a non-English-language word.
class Dword extends Word{
    //Default constructor.
    public Dword() {
        super();
    }

    //Constructor with string argument.
    public Dword(String e){
        this.setWord(e);
    }

    //Overriding display function for dynamic binding.
    public void display(){
        System.out.println(this.getWord());
    }
}
