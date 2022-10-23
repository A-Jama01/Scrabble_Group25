//Monishkumar Sivakumar - 1011115115

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Dictionary {

    public Dictionary(){}

    // This class will take a String input and will read through a text file to check
    // whether the word is in the file or not. If its in the file, the class will
    // return  boolean value of true, otherwise it will return false
    public boolean check(String word){
        try {
            File dictionary = new File("Dictionary.txt");
            Scanner words = new Scanner(dictionary);
            boolean flag = false;
            while (words.hasNextLine()) {
                String data = words.nextLine(); //reads the next line in the file
                String[] check = data.split(" "); //splits the sentence in the file into seperate words
                for (String s: check){
                    if (s.equals(word.toLowerCase())){ //checks whether the input word matches anything in the sentence
                        System.out.println("Word is Legal");
                        flag = true;
                    }
                }
            }
            words.close();
            return flag;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

}
