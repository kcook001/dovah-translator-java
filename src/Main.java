//Main.java
//The is the client class file for the Translator Program.  This program reads in a supplied,
// specially-formatted text file of word cognate pairs from English and another language and uses this data
// to translate common words or phrases from English to the non-English language.  By default the program
// translates to Dovahzul, the language spoken by the dragons of Skyrim, but a properly-formatted text file
// for any language may be substituted.
//Keith Cook, 03/06/2016

// A QUICK NOTE:
//If you aren't familiar with the Elder Scrolls series of video games, the country of Skyrim is basically a
// medieval fantasy sort of setting.  As per the assignment I have included several common modern travel phrases, but,
// in general, keep this setting in mind when thinking of words to test.  You can also use the debug menu I've included to see
// the full list of translatable words.
//
//Some words/phrases to try for starters:
// Where is the bathroom?
// Do you speak English?
// My name is
// Please
// Thank you
// Please call the city guard
//     ...and so on.

//Imports
import java.util.Scanner;
import java.io.*;

public class Main {

    public static void main(String[] ignore) throws FileNotFoundException {

        //Variables
        char choice;
        Translator dictionary = new Translator("dovahzul.txt");
        String toTranslate;
        Word result = new Dword();
        Scanner scan = new Scanner(System.in);

        //If dictionary cannot be loaded, show error message and exit.
        if(!dictionary.exists()){
            System.out.println("The dictionary could not be loaded correctly.  The program will now terminate.");
            return;
        }

        //Display the name of the program.
        System.out.println("English to " + dictionary.getLanguage() + " Translator");

        //Main program loop.
        do {
            System.out.println("Please enter the word or phrase you'd like to translate.");
            System.out.println("(or you can type \"debug\" for testing options)");
            System.out.print("English: ");
            scan.useDelimiter("\\n");
            toTranslate = scan.next();
            scan.nextLine();

            //Access debug menu if user chooses.
            if(toTranslate.equals("debug")) {
                dictionary.debugMenu();
            } else { //otherwise, attempt the translation.
                //Input formatting
                toTranslate = toTranslate.toLowerCase();
                toTranslate = toTranslate.substring(0, 1).toUpperCase() + toTranslate.substring(1);
                toTranslate = toTranslate.replace("?", "");

                //Attempt translation.
                result.setWord(dictionary.translate(toTranslate));

                //Display result.
                if (result.getWord() == null) {
                    System.out.println("No translation found for: " + toTranslate);
                } else {
                    System.out.print(dictionary.getLanguage() + ": ");
                    result.display();
                    //System.out.println(result);
                }
            }
            result.setWord(null);
            System.out.println("Would you like to translate another word or phrase? (y/n)");
            choice = scan.findInLine(".").charAt(0);
            scan.nextLine();

            if(choice != 'y' && choice != 'Y' && choice != 'n' && choice != 'N') {
                System.out.println("I'll take that as a 'no', then.");
            }

        }while(choice == 'y' || choice == 'Y');
        //Repeat as desired.

        //Nulls out the dictionary (aka, setting the cans by the street for the garbage collector)
        dictionary.removeAll();
    }
}
