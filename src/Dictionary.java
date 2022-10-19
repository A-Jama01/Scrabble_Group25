import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {

    public static void main(String[] args)
    {
        try {
            File dictionary = new File("Dictionary.txt");
            Scanner words = new Scanner(dictionary);
            String word = "able";
            while (words.hasNextLine()) {
                String data = words.nextLine();
                String[] check = data.split(" ");
                for (String s: check){
                    if (s.equals(word)){
                        System.out.println("Word is Legal");
                    }
                }
            }
            words.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
