/** Dictionary Class - Checks whether the word is in the dictionary or not
*
* @author Monishkumar Sivakumar 101115115
**/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.io.Serializable;


public class Dictionary implements Serializable{
    private static final long serialVersionUID = 1;
    private HashSet<String> dictSet;
    public Dictionary(){
        createSet();
    }

    // This function will take a String input and will read through a text file to check
    // whether the word is in the file or not. If its in the file, the function will
    // return  boolean value of true, otherwise it will return false
    public boolean check(String word){
        try {
            File dictionary = new File("Dictionary.txt");
            Scanner words = new Scanner(dictionary);
            boolean flag = false;

            boolean hasBlankTile = word.contains(Bag.BLANK_TILE);

            while (words.hasNextLine()) {
                String data = words.nextLine(); //reads the next line in the file
                String[] check = data.split(" "); //splits the sentence in the file into seperate words
                for (String s: check){
                    if (hasBlankTile) {
                        if (compareWordsWithBlankTile(word, s)) {
                            System.out.println(s + " is legal");
                            flag = true;
                        }
                    } else {
                        if (s.equals(word.toLowerCase())) { //checks whether the input word matches anything in the sentence
                            System.out.println("Word is Legal");
                            flag = true;
                        }
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

    public boolean compareWordsWithBlankTile(String wordToCheck, String dictionaryWord) {
        StringBuilder dummyDictionaryWord = new StringBuilder(dictionaryWord);
        if (wordToCheck.length() != dictionaryWord.length()) { return false; }
        for (int i = 0; i < wordToCheck.length(); i++) {
            if (wordToCheck.substring(i, i + 1).equals(Bag.BLANK_TILE)) {
                dummyDictionaryWord.replace(i, i + 1, Bag.BLANK_TILE);
            }
        }
        return dummyDictionaryWord.toString().equalsIgnoreCase(wordToCheck);
    }

    public void createSet() {
        try {
            Scanner s = new Scanner(new File("Dictionary.txt"));
            dictSet = new HashSet<>();
            while (s.hasNextLine()) {
                dictSet.add(s.nextLine());
            }
            s.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkSet(String word) {
        if (dictSet.contains(word.toLowerCase())) {
            System.out.println(word);
            return true;
        }
        return false;
    }

}
