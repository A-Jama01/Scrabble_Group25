import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {

    public static boolean check(String word){
        try {
            File dictionary = new File("Dictionary.txt");
            Scanner words = new Scanner(dictionary);
            boolean flag = false;
            while (words.hasNextLine()) {
                String data = words.nextLine();
                String[] check = data.split(" ");
                for (String s: check){
                    if (s.equals(word)){
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
